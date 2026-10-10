package com.relichunt;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WandererFeatherItem extends Item {
    public WandererFeatherItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);
        if (!level.isClientSide) {
            Vec3 direction = player.getLookAngle().normalize();
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.add(direction.x * 2.0, direction.y * 0.6 + 0.35, direction.z * 2.0));
            player.hurtMarked = true;
            player.getCooldowns().addCooldown(this, 60);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
