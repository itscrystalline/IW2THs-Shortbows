package com.thaddev.coolideas.content.entities.projectiles;

import com.google.common.collect.Sets;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DiamondHeadedArrow extends AbstractArrow {
    private static final int EXPOSED_POTION_DECAY_TIME = 600;
    private static final int NO_EFFECT_COLOR = -1;
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(DiamondHeadedArrow.class, EntityDataSerializers.INT);
    private static final byte EVENT_POTION_PUFF = 0;
    private Potion potion = Potions.EMPTY;
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    private boolean fixedColor;
    private boolean shotByShortbow;

    private boolean isHoming;
    LivingEntity target;

    public DiamondHeadedArrow(EntityType<? extends AbstractArrow> type, Level world) {
        super(type, world);
        setBaseDamage(4D);
        setPierceLevel((byte) 5);
        //CoolIdeasMod.instance.printMessage(ColorUtils.from("(%$green)Init Velocity: " + getDeltaMovement()));
    }

    public DiamondHeadedArrow(LivingEntity shooter, Level world) {
        super(EntityTypeInit.DIAMOND_HEADED_ARROW.get(), shooter, world);
        setBaseDamage(4D);
        setPierceLevel((byte) 5);
        //CoolIdeasMod.instance.printMessage(ColorUtils.from("(%$green)Init Velocity: " + getDeltaMovement()));
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(ItemInit.DIAMOND_HEADED_ARROW.get());
        } else {
            ItemStack itemstack = new ItemStack(ItemInit.TIPPED_DIAMOND_HEADED_ARROW.get());
            PotionUtils.setPotion(itemstack, this.potion);
            PotionUtils.setCustomEffects(itemstack, this.effects);
            if (this.fixedColor) {
                itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
            }

            return itemstack;
        }
    }

    public void setEffectsFromItem(ItemStack stack) {
        if (stack.is(ItemInit.TIPPED_DIAMOND_HEADED_ARROW.get())) {
            this.potion = PotionUtils.getPotion(stack);
            Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(stack);
            if (!collection.isEmpty()) {
                for (MobEffectInstance mobeffectinstance : collection) {
                    this.effects.add(new MobEffectInstance(mobeffectinstance));
                }
            }

            int i = getCustomColor(stack);
            if (i == -1) {
                this.updateColor();
            } else {
                this.setFixedColor(i);
            }
        } else if (stack.is(ItemInit.DIAMOND_HEADED_ARROW.get())) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }

    }

    public boolean getShotByShortbow() {
        return this.shotByShortbow;
    }

    public void setShotByShortbow(boolean shotByShortbow) {
        this.shotByShortbow = shotByShortbow;
    }

    public boolean isHoming() {
        return isHoming;
    }

    public void setHoming(boolean homing) {
        isHoming = homing;
    }

    public static int getCustomColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.contains("CustomPotionColor", 99) ? compoundtag.getInt("CustomPotionColor") : -1;
    }

    private void updateColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.entityData.set(ID_EFFECT_COLOR, -1);
        } else {
            this.entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
        }

    }

    public void addEffect(MobEffectInstance effectInstance) {
        this.effects.add(effectInstance);
        this.getEntityData().set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, -1);
    }

    public void tick() {
        super.tick();
        if (inGroundTime > 20 && this.shotByShortbow) {
            this.discard();
        }
        if (this.isHoming() && (!this.inGround || this.inGroundTime <= 10)) {
            this.setTarget();
            if (target != null && target.isAlive()) {
                float i = 5f;
                double diffY = target instanceof EnderDragon dragon ? dragon.getSubEntities()[1 /* Gets the Neck part */].getY() - this.getY() : target.getEyeY() - this.getY();
                Vec3 vec3d = new Vec3(target.getX() - this.getX(), diffY, target.getZ() - this.getZ());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015D * (double) i, this.getZ());
                if (this.level.isClientSide) {
                    this.yOld = this.getY();
                }

                double d = 0.1D * (double) i;
                this.setDeltaMovement(this.getDeltaMovement()
                    .multiply(0.75D, 0.75D, 0.75D)
                    .add(vec3d.normalize().multiply(d, d, d)));
                this.hasImpulse = true;
            }
        }
        if (this.level.isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte) 0);
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }

    }

    private void makeParticle(int color) {
        int i = this.getColor();
        if (i != -1 && color > 0) {
            double d0 = (double) (i >> 16 & 255) / 255.0D;
            double d1 = (double) (i >> 8 & 255) / 255.0D;
            double d2 = (double) (i & 255) / 255.0D;

            for (int j = 0; j < color; ++j) {
                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }

        }
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    private void setFixedColor(int color) {
        this.fixedColor = true;
        this.entityData.set(ID_EFFECT_COLOR, color);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.potion != Potions.EMPTY) {
            tag.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }

        if (this.fixedColor) {
            tag.putInt("Color", this.getColor());
        }

        if (this.shotByShortbow) {
            tag.putBoolean("ShotByShortbow", this.getShotByShortbow());
        }

        if (this.isHoming) {
            tag.putBoolean("IsHoming", this.isHoming());
        }

        if (!this.effects.isEmpty()) {
            ListTag listtag = new ListTag();

            for (MobEffectInstance mobeffectinstance : this.effects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            tag.put("CustomPotionEffects", listtag);
        }

    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotion(tag);
        }

        for (MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(tag)) {
            this.addEffect(mobeffectinstance);
        }

        if (tag.contains("Color", 99)) {
            this.setFixedColor(tag.getInt("Color"));
        } else {
            this.updateColor();
        }

        if (tag.contains("IsHoming", 99)) {
            this.setHoming(tag.getBoolean("IsHoming"));
        } else {
            this.setHoming(false);
        }

        if (tag.contains("ShotByShortbow", 99)) {
            this.setShotByShortbow(tag.getBoolean("ShotByShortbow"));
        } else {
            this.setShotByShortbow(false);
        }

    }

    protected void doPostHurtEffects(@NotNull LivingEntity entity1) {
        super.doPostHurtEffects(entity1);
        Entity entity = this.getEffectSource();

        for (MobEffectInstance mobeffectinstance : this.potion.getEffects()) {
            entity1.addEffect(new MobEffectInstance(mobeffectinstance.getEffect(), Math.max(mobeffectinstance.getDuration() / 8, 1), mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()), entity);
        }

        if (!this.effects.isEmpty()) {
            for (MobEffectInstance mobeffectinstance1 : this.effects) {
                entity1.addEffect(mobeffectinstance1, entity);
            }
        }

    }

    public void handleEntityEvent(byte b) {
        if (b == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double) (i >> 16 & 255) / 255.0D;
                double d1 = (double) (i >> 8 & 255) / 255.0D;
                double d2 = (double) (i & 255) / 255.0D;

                for (int j = 0; j < 20; ++j) {
                    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(b);
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (result.getEntity().getUUID().equals(this.target.getUUID())) {
            this.discard();
        }
        super.onHitEntity(result);
    }

    //HOMING BEHAVIOR
    private void setTarget() {
        if (target == null || target.isDeadOrDying() || (target instanceof EnderDragon dragon && dragon.getPhaseManager().getCurrentPhase().getPhase() == EnderDragonPhase.DYING)) {
            AABB aabb = (new AABB(new BlockPos(this.position()))).inflate(20).expandTowards(0.0D, level.getHeight(), 0.0D);
            List<LivingEntity> nearbyEntities = this.level.getEntitiesOfClass(LivingEntity.class, aabb);

            if (nearbyEntities.size() == 0) {
                target = null;
                return;
            }

            List<LivingEntity> targetList = new ArrayList<>();

            for (LivingEntity entity : nearbyEntities) {
                if (entity.hasLineOfSight(this) && !entity.isDeadOrDying()) {
                    if (entity instanceof Enemy) {
                        if (!(entity instanceof EnderMan)) {
                            if (!(entity instanceof WitherBoss) && !(entity instanceof EnderDragon)) {
                                targetList.add(entity);
                            } else if (entity instanceof WitherBoss && !((WitherBoss) entity).isPowered()) {
                                targetList.add(entity);
                            } else if (entity instanceof EnderDragon) {
                                EnderDragonPhase<? extends DragonPhaseInstance> phase = ((EnderDragon) entity).getPhaseManager().getCurrentPhase().getPhase();
                                //CoolIdeasMod.instance.printMessage(ColorUtils.from("(%$red)Dragon Phase: " + phase));
                                if (phase != EnderDragonPhase.SITTING_ATTACKING &&
                                    phase != EnderDragonPhase.SITTING_SCANNING &&
                                    phase != EnderDragonPhase.SITTING_FLAMING &&
                                    phase != EnderDragonPhase.DYING) {
                                    targetList.add(entity);
                                }
                            }

                        }
                    } else if (entity instanceof Player player) {
                        if (!player.getUUID().equals(Objects.requireNonNull(this.getOwner()).getUUID()) && !player.isCreative() && !player.isSpectator()) {
                            targetList.add(entity);
                        }
                    }

                }

            }


            if (targetList.size() == 0) {
                target = null;
                return;
            }

            target = targetList.get(random.nextInt(targetList.size()));
        }
    }
}
