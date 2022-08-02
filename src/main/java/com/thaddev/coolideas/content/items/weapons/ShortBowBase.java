package com.thaddev.coolideas.content.items.weapons;

import com.thaddev.coolideas.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.coolideas.content.entities.projectiles.ShortBowArrow;
import com.thaddev.coolideas.mechanics.damagesources.RubberBandHitDamage;
import com.thaddev.coolideas.mechanics.inits.EnchantmentInit;
import com.thaddev.coolideas.util.ColorUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

import static com.thaddev.coolideas.util.ColorUtils.component;

public class ShortBowBase extends BowItem {

    public float getCritChance() {
        return 0f;
    }

    public boolean canHomeArrows() {
        return false;
    }

    public float getRubberbandHitChance() {
        return 0f;
    }

    public float getRubberbandHitDamage() {
        return 0f;
    }

    public int getTicksOnFire() {
        return 0;
    }

    public double getPowerBaseDamage(double baseDamage, int powerLevel) {
        return baseDamage;
    }

    public ShortBowBase(Properties properties) {
        super(properties);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_40680_) {
        return 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer) {
            ItemStack itemstack = player.getProjectile(player.getItemInHand(hand));
            boolean inherit = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.INHERIT.get(), player.getItemInHand(hand)) > 0;
            float precisionChance = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.PRECISION.get(), player.getItemInHand(hand)) == 1 ? (2f / 3f) :
                (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.PRECISION.get(), player.getItemInHand(hand)) == 2 ? 0.5f : 1f);
            AttributeInstance attributeinstance = player.getAttribute(Attributes.ATTACK_DAMAGE);
            double strengthBoost = (attributeinstance != null && inherit ? attributeinstance.getValue() - attributeinstance.getBaseValue() : 0);

            boolean flag = player.getAbilities().instabuild || (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, player.getItemInHand(hand)) > 0 && itemstack.getCount() > 0);
            if (!itemstack.isEmpty() || flag) {
                float rng = (float) Math.random();
                if (rng <= (getRubberbandHitChance() * precisionChance) + ((strengthBoost / 2f) * 0.1f)) {
                    player.hurt(new RubberBandHitDamage(), (float) (getRubberbandHitDamage() + strengthBoost));
                    if (strengthBoost > 0) {
                        PotionUtils.getPotion(itemstack).getEffects().forEach(effect -> {
                            if (effect.getEffect().isInstantenous()){
                                effect.getEffect().applyInstantenousEffect(null, player, player, (effect.getAmplifier() + 1) > 1 ?
                                        (int) Math.round(Math.floor(effect.getAmplifier() / 2f)) :
                                        effect.getAmplifier(), 1);
                            } else {
                                player.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() / 20, effect.getAmplifier()));
                            }
                        });
                    }
                }
                releaseUsing(player.getItemInHand(hand), world, player);
                return InteractionResultHolder.success(player.getItemInHand(hand));
            } else {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    private void releaseUsing(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof Player player) {
            boolean inherit = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.INHERIT.get(), stack) > 0;
            boolean precision = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.PRECISION.get(), stack) > 0;
            AttributeInstance attributeinstance = player.getAttribute(Attributes.ATTACK_DAMAGE);
            double strengthBoost = (attributeinstance != null ? attributeinstance.getValue() - attributeinstance.getBaseValue() : 0);
            float strengthLowDown = (((float) stack.getDamageValue() / stack.getMaxDamage()) / 4f * 3f);
            float chance = getCritChance();

            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack) > 0) {
                chance += 0.05f * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            }
            ItemStack itemstack = player.getProjectile(stack);
            boolean flag = player.getAbilities().instabuild || (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && itemstack.getCount() > 0);

            int i = this.getUseDuration(stack);
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float strength = 1f - strengthLowDown;
                boolean crit = Math.random() < chance;
                boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, player));
                int multiShotArrows = (2 * (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack) + 1)) - 1;
                if (!world.isClientSide) {
                    ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);

                    if (multiShotArrows == 1) {
                        summonAndShoot(itemstack, stack, world, player, strength, arrowitem, crit, flag1, 0);
                    } else {
                        for (int j = -((multiShotArrows - 1) / 2); j <= ((multiShotArrows - 1) / 2); j++) {
                            summonAndShoot(itemstack, stack, world, player, strength, arrowitem, crit, flag1, j);
                        }
                    }
                }

                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + strength * 0.5F);
                if (!flag1 && !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.getInventory().removeItem(itemstack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private ShortBowArrow createShortBowArrow(Level world, ItemStack stack, LivingEntity player) {
        ShortBowArrow arrow = new ShortBowArrow(world, player);
        arrow.setEffectsFromItem(stack);
        return arrow;
    }

    private void summonAndShoot(ItemStack itemstack, ItemStack stack, Level world, Player player, float strength, ArrowItem arrowitem, boolean crit, boolean flag1, int j) {
        AbstractArrow shortBowArrow;

        if (itemstack.is(Items.ARROW) || itemstack.is(Items.TIPPED_ARROW)) {
            //CoolIdeasMod.instance.printMessage("Shooting Regular Arrow!");
            shortBowArrow = createShortBowArrow(world, itemstack, player);
        } else {
            //CoolIdeasMod.instance.printMessage("Shooting Custom Arrow!");
            shortBowArrow = arrowitem.createArrow(world, itemstack, player);
        }

        if (shortBowArrow instanceof DiamondHeadedArrow diamondHeadedArrow) {
            diamondHeadedArrow.setShotByShortbow(true);
            if (doesHomeArrow(stack)) {
                diamondHeadedArrow.setHoming(true);
            }
        }

        shortBowArrow.setBaseDamage(shortBowArrow.getBaseDamage() * 0.75D);
        shortBowArrow.shootFromRotation(player, player.getXRot(), player.getYRot() + (j * 8), 0.0F, strength * 3.0F, 1.0F);
        if (crit) {
            shortBowArrow.setCritArrow(true);
        }

        int l = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (l > 0) {
            shortBowArrow.setBaseDamage(getPowerBaseDamage(shortBowArrow.getBaseDamage(), l));
        }

        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
        if (k > 0) {
            shortBowArrow.setKnockback(k);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
            shortBowArrow.setSecondsOnFire(getTicksOnFire());
        }

        if (j == 0) {
            stack.hurtAndBreak(1, player, (player1) -> {
                player1.broadcastBreakEvent(player.getUsedItemHand());
            });
            if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                shortBowArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
        } else {
            shortBowArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        world.addFreshEntity(shortBowArrow);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return super.getAllSupportedProjectiles();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == EnchantmentInit.INHERIT.get() || enchantment == EnchantmentInit.PRECISION.get() || enchantment.category == EnchantmentCategory.BOW;
    }

    public static void setDoesHomeArrow(ItemStack stack, boolean doesHomeArrow) {
        if (stack.getItem() instanceof ShortBowBase shortbow) {
            if (shortbow.canHomeArrows()){
                CompoundTag compoundtag = stack.getOrCreateTag();
                compoundtag.putBoolean("DoesHomeArrow", doesHomeArrow);
            }
        }
    }

    public static boolean doesHomeArrow(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.getBoolean("DoesHomeArrow");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        if (doesHomeArrow(stack)) {
            tooltip.add(component(ColorUtils.fromNoTag("(%$green)Homing")));
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }
}
