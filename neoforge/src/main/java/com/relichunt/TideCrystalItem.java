package com.relichunt;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TideCrystalItem extends Item {
    public TideCrystalItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);
        if (!level.isClientSide) {
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(6.0),
                    entity -> entity != player && entity.isAlive() && entity.distanceToSqr(player) <= 36.0)) {
                Vec3 direction = target.position().subtract(player.position());
                Vec3 push = direction.lengthSqr() < 0.01 ? player.getLookAngle() : direction.normalize();
                target.push(push.x * 1.5, 0.45, push.z * 1.5);
                target.hurtMarked = true;
            }
            if (player.isInWaterOrBubble()) {
                player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200, 0));
            }
            player.getCooldowns().addCooldown(this, 300);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
