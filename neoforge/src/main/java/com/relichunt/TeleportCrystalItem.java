package com.relichunt;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Opens the teleport menu; actual teleportation is handled server-side later. */
public class TeleportCrystalItem extends Item {
    public TeleportCrystalItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            TeleportCrystalClient.openMenu();
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
