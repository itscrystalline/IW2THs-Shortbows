package com.thaddev.coolideas.content.entities.projectiles;

import com.google.common.collect.Sets;
import com.thaddev.coolideas.mechanics.inits.EntityTypeInit;
import com.thaddev.coolideas.mechanics.inits.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DiamondHeadedArrow extends PersistentProjectileEntity {
    private static final int MAX_POTION_DURATION_TICKS = 600;
    private static final int NO_POTION_COLOR = -1;
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(DiamondHeadedArrow.class, TrackedDataHandlerRegistry.INTEGER);
    private static final byte PARTICLE_EFFECT_STATUS = 0;
    private Potion potion = Potions.EMPTY;
    private final Set<StatusEffectInstance> effects = Sets.newHashSet();
    private boolean colorSet;
    private boolean shotByShortbow;

    private boolean isHoming;
    LivingEntity target;

    public DiamondHeadedArrow(EntityType<? extends DiamondHeadedArrow> entityType, World world) {
        super(entityType, world);
        setDamage(4D);
        setPierceLevel((byte) 5);
    }

    public DiamondHeadedArrow(World world, LivingEntity owner) {
        super(EntityTypeInit.DIAMOND_HEADED_ARROW, owner, world);
        setDamage(4D);
        setPierceLevel((byte) 5);
    }

    public void initFromStack(ItemStack stack) {
        if (stack.isOf(ItemInit.TIPPED_DIAMOND_HEADED_ARROW)) {
            int i;
            this.potion = PotionUtil.getPotion(stack);
            List<StatusEffectInstance> collection = PotionUtil.getCustomPotionEffects(stack);
            if (!collection.isEmpty()) {
                for (StatusEffectInstance statusEffectInstance : collection) {
                    this.effects.add(new StatusEffectInstance(statusEffectInstance));
                }
            }
            if ((i = DiamondHeadedArrow.getCustomPotionColor(stack)) == -1) {
                this.initColor();
            } else {
                this.setColor(i);
            }
        } else if (stack.isOf(ItemInit.DIAMOND_HEADED_ARROW)) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.dataTracker.set(COLOR, -1);
        }
    }

    @Override
    public ItemStack asItemStack() {
        if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(Items.ARROW);
        }
        ItemStack itemStack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtil.setPotion(itemStack, this.potion);
        PotionUtil.setCustomPotionEffects(itemStack, this.effects);
        if (this.colorSet) {
            itemStack.getOrCreateNbt().putInt("CustomPotionColor", this.getColor());
        }
        return itemStack;
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

    public static int getCustomPotionColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null && nbtCompound.contains("CustomPotionColor", NbtElement.NUMBER_TYPE)) {
            return nbtCompound.getInt("CustomPotionColor");
        }
        return -1;
    }

    private void initColor() {
        this.colorSet = false;
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.dataTracker.set(COLOR, -1);
        } else {
            this.dataTracker.set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
        }
    }

    public void addEffect(StatusEffectInstance effect) {
        this.effects.add(effect);
        this.getDataTracker().set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (inGroundTime > 20 && this.shotByShortbow) {
            this.discard();
        }
        if (this.isHoming() && (!this.inGround || this.inGroundTime <= 10)) {
            this.setTarget();
            if (target != null && target.isAlive()) {
                float i = 5f;
                double diffY = target instanceof EnderDragonEntity dragon ? dragon.getBodyParts()[1 /* Gets the Neck part */].getY() - this.getY() : target.getEyeY() - this.getY();
                Vec3d vec3d = new Vec3d(target.getX() - this.getX(), diffY, target.getZ() - this.getZ());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015D * (double) i, this.getZ());
                if (this.world.isClient) {
                    this.lastRenderY = this.getY();
                }

                double d = 0.1D * (double) i;
                this.setVelocity(this.getVelocity()
                    .multiply(0.75D, 0.75D, 0.75D)
                    .add(vec3d.normalize().multiply(d, d, d)));
                //this.hasImpulse = true;
            }
        }
        if (this.world.isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles(1);
                }
            } else {
                this.spawnParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.world.sendEntityStatus(this, (byte) 0);
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.dataTracker.set(COLOR, -1);
        }
    }

    private void spawnParticles(int amount) {
        int i = this.getColor();
        if (i == -1 || amount <= 0) {
            return;
        }
        double d = (double) (i >> 16 & 0xFF) / 255.0;
        double e = (double) (i >> 8 & 0xFF) / 255.0;
        double f = (double) (i >> 0 & 0xFF) / 255.0;
        for (int j = 0; j < amount; ++j) {
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
        }
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    private void setColor(int color) {
        this.colorSet = true;
        this.dataTracker.set(COLOR, color);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.potion != Potions.EMPTY) {
            nbt.putString("Potion", Registry.POTION.getId(this.potion).toString());
        }
        if (this.colorSet) {
            nbt.putInt("Color", this.getColor());
        }
        if (!this.effects.isEmpty()) {
            NbtList nbtList = new NbtList();
            for (StatusEffectInstance statusEffectInstance : this.effects) {
                nbtList.add(statusEffectInstance.writeNbt(new NbtCompound()));
            }
            nbt.put("CustomPotionEffects", nbtList);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Potion", NbtElement.STRING_TYPE)) {
            this.potion = PotionUtil.getPotion(nbt);
        }
        for (StatusEffectInstance statusEffectInstance : PotionUtil.getCustomPotionEffects(nbt)) {
            this.addEffect(statusEffectInstance);
        }
        if (nbt.contains("Color", NbtElement.NUMBER_TYPE)) {
            this.setColor(nbt.getInt("Color"));
        } else {
            this.initColor();
        }
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        Entity entity = this.getEffectCause();
        for (StatusEffectInstance statusEffectInstance : this.potion.getEffects()) {
            target.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), Math.max(statusEffectInstance.getDuration() / 8, 1), statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles()), entity);
        }
        if (!this.effects.isEmpty()) {
            for (StatusEffectInstance statusEffectInstance : this.effects) {
                target.addStatusEffect(statusEffectInstance, entity);
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d = (double) (i >> 16 & 0xFF) / 255.0;
                double e = (double) (i >> 8 & 0xFF) / 255.0;
                double f = (double) (i & 0xFF) / 255.0;
                for (int j = 0; j < 20; ++j) {
                    this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
                }
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.target != null && entityHitResult.getEntity().getUuid().equals(this.target.getUuid())) {
            this.discard();
        }
    }

    //HOMING BEHAVIOR
    private void setTarget() {
        if (target == null || target.isDead() || (target instanceof EnderDragonEntity dragon && dragon.getPhaseManager().getCurrent().getType() == PhaseType.DYING)) {
            Box box = (new Box(new BlockPos(this.getPos()))).expand(20).stretch(0.0D, world.getHeight(), 0.0D);
            List<LivingEntity> nearbyEntities = this.world.getEntitiesByClass(LivingEntity.class, box, (entity) -> !entity.isSpectator() && entity.isAlive());

            if (nearbyEntities.size() == 0) {
                target = null;
                return;
            }

            List<LivingEntity> targetList = new ArrayList<>();

            for (LivingEntity entity : nearbyEntities) {
                if (entity.canSee(this) && !entity.isDead()) {
                    if (entity instanceof Monster) {
                        if (!(entity instanceof EndermanEntity)) {
                            if (!(entity instanceof WitherEntity) && !(entity instanceof EnderDragonEntity)) {
                                targetList.add(entity);
                            } else if (entity instanceof WitherEntity && !((WitherEntity) entity).shouldRenderOverlay()) {
                                targetList.add(entity);
                            } else if (entity instanceof EnderDragonEntity) {
                                PhaseType<? extends Phase> phase = ((EnderDragonEntity) entity).getPhaseManager().getCurrent().getType();
                                //CoolIdeasMod.instance.printMessage(ColorUtils.from("(%$red)Dragon Phase: " + phase));
                                if (phase != PhaseType.SITTING_ATTACKING &&
                                    phase != PhaseType.SITTING_SCANNING &&
                                    phase != PhaseType.SITTING_FLAMING &&
                                    phase != PhaseType.DYING) {
                                    targetList.add(entity);
                                }
                            }

                        }
                    } else if (entity instanceof PlayerEntity player) {
                        if (!player.getUuid().equals(Objects.requireNonNull(this.getOwner()).getUuid()) && !player.isCreative() && !player.isSpectator()) {
                            if (this.getOwner() instanceof PlayerEntity owner) {
                                if (owner.shouldDamagePlayer(player)) {
                                    targetList.add(entity);
                                }
                            } else {
                                targetList.add(entity);
                            }
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
