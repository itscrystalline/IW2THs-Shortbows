package com.thaddev.iw2thshortbows.content.items.weapons;

import com.thaddev.iw2thshortbows.content.entities.projectiles.DiamondHeadedArrow;
import com.thaddev.iw2thshortbows.content.entities.projectiles.ShortBowArrow;
import com.thaddev.iw2thshortbows.mechanics.damagesources.RubberBandHitDamage;
import com.thaddev.iw2thshortbows.mechanics.inits.EnchantmentInit;
import com.thaddev.iw2thshortbows.mechanics.inits.ItemInit;
import com.thaddev.iw2thshortbows.util.Utils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.thaddev.iw2thshortbows.util.Utils.component;

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

    public ShortBowBase(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 0;
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity player) {
            ItemStack itemstack = player.getArrowType(player.getStackInHand(hand));
            boolean inherit = EnchantmentHelper.getLevel(EnchantmentInit.INHERIT, player.getStackInHand(hand)) > 0;
            float precisionChance = EnchantmentHelper.getLevel(EnchantmentInit.PRECISION, player.getStackInHand(hand)) == 1 ? (2f / 3f) :
                (EnchantmentHelper.getLevel(EnchantmentInit.PRECISION, player.getStackInHand(hand)) == 2 ? 0.5f : 1f);
            double damage = player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            double baseDamage = player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            double strengthBoost = (inherit ? damage - baseDamage : 0);

            boolean flag = player.getAbilities().creativeMode || (EnchantmentHelper.getLevel(Enchantments.INFINITY, player.getStackInHand(hand)) > 0 && itemstack.getCount() > 0);
            if (!itemstack.isEmpty() || flag) {
                float rng = (float) Math.random();
                if (rng <= (getRubberbandHitChance() * precisionChance) + ((strengthBoost / 2f) * 0.1f)) {
                    player.damage(new RubberBandHitDamage(), (float) (getRubberbandHitDamage() + strengthBoost));
                    if (strengthBoost > 0) {
                        PotionUtil.getPotion(itemstack).getEffects().forEach(effect -> {
                            if (effect.getEffectType().isInstant()) {
                                effect.getEffectType().applyInstantEffect(null, player, player, (effect.getAmplifier() + 1) > 1 ?
                                    (int) Math.round(Math.floor(effect.getAmplifier() / 2f)) :
                                    effect.getAmplifier(), 1);
                            } else {
                                player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() / 20, effect.getAmplifier()));
                            }
                        });
                    }
                }
                onStoppedUsing(player.getStackInHand(hand), world, player);
                return TypedActionResult.success(player.getStackInHand(hand));
            } else {
                return TypedActionResult.fail(player.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user) {
        boolean bl2;
        float f;
        if (user instanceof PlayerEntity player) {
            float chance = getCritChance();
            float strengthLowDown = (((float) stack.getDamage() / stack.getMaxDamage()) / 4f * 3f);
            boolean bl = player.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = player.getArrowType(stack);
            if (itemStack.isEmpty() && !bl) {
                return;
            }
            if (itemStack.isEmpty()) {
                itemStack = new ItemStack(Items.ARROW);
            }
            boolean bl3 = bl2 = bl && itemStack.isOf(Items.ARROW);
            boolean crit = Math.random() < chance;
            float strength = 1f - strengthLowDown;
            int multiShotArrows = (2 * (EnchantmentHelper.getLevel(Enchantments.MULTISHOT, stack) + 1)) - 1;
            if (!world.isClient) {
                ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);

                if (multiShotArrows == 1) {
                    summonAndShoot(itemStack, stack, world, player, strength, arrowItem, crit, bl3, 0);
                } else {
                    for (int j = -((multiShotArrows - 1) / 2); j <= ((multiShotArrows - 1) / 2); j++) {
                        summonAndShoot(itemStack, stack, world, player, strength, arrowItem, crit, bl3, j);
                    }
                }
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + strength * 0.5f);
            if (!bl2 && !player.getAbilities().creativeMode) {
                itemStack.decrement(1);
                if (itemStack.isEmpty()) {
                    player.getInventory().removeOne(itemStack);
                }
            }
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

    private void summonAndShoot(ItemStack itemStack, ItemStack stack, World world, PlayerEntity player, float strength, ArrowItem arrowItem, boolean crit, boolean flag1, int j) {
        PersistentProjectileEntity persistentProjectileEntity;

        if (itemStack.isOf(Items.ARROW) || itemStack.isOf(Items.TIPPED_ARROW)) {
            persistentProjectileEntity = createShortBowArrow(world, itemStack, player);
        } else {
            persistentProjectileEntity = arrowItem.createArrow(world, itemStack, player);
        }

        if (persistentProjectileEntity instanceof DiamondHeadedArrow diamondHeadedArrow) {
            diamondHeadedArrow.setShotByShortbow(true);
            if (doesHomeArrow(stack)) {
                diamondHeadedArrow.setHoming(true);
            }
        }

        int l, k;
        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() * 0.75D);
        persistentProjectileEntity.setVelocity(player, player.getPitch(), player.getYaw() + (j * 8), 0.0f, strength * 3.0f, 1.0f);
        if (crit) {
            persistentProjectileEntity.setCritical(true);
        }
        if ((l = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
            persistentProjectileEntity.setDamage(getPowerBaseDamage(persistentProjectileEntity.getDamage(), l));
        }
        if ((k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
            persistentProjectileEntity.setPunch(k);
        }
        if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
            persistentProjectileEntity.setOnFireFor(getTicksOnFire());
        }
        stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        if (flag1 || player.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW) || itemStack.isOf(ItemInit.DIAMOND_HEADED_ARROW) || itemStack.isOf(ItemInit.TIPPED_DIAMOND_HEADED_ARROW))) {
            persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        }
        world.spawnEntity(persistentProjectileEntity);
    }

    private ShortBowArrow createShortBowArrow(World world, ItemStack stack, LivingEntity player) {
        ShortBowArrow arrow = new ShortBowArrow(world, player);
        arrow.initFromStack(stack);
        return arrow;
    }

    public static void setDoesHomeArrow(ItemStack stack, boolean doesHomeArrow) {
        if (stack.getItem() instanceof ShortBowBase shortbow) {
            if (shortbow.canHomeArrows()) {
                NbtCompound compoundtag = stack.getOrCreateNbt();
                compoundtag.putBoolean("DoesHomeArrow", doesHomeArrow);
            }
        }
    }

    public static boolean doesHomeArrow(ItemStack stack) {
        NbtCompound compoundtag = stack.getNbt();
        return compoundtag != null && compoundtag.getBoolean("DoesHomeArrow");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (doesHomeArrow(stack)) {
            tooltip.add(component(Utils.fromNoTag("(%$green)Homing")));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
