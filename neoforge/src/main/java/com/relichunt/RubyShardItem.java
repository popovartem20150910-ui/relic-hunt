package com.relichunt;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class RubyShardItem extends Item {
    public RubyShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().getBlockState(context.getClickedPos()).is(RelicHunt.POLISHING_STONE.get())) {
            return InteractionResult.PASS;
        }
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (!context.getLevel().isClientSide) {
            ItemStack shard = context.getItemInHand();
            if (!player.getAbilities().instabuild) shard.shrink(1);
            ItemStack ruby = new ItemStack(RelicHunt.RUBY.get());
            if (!player.getInventory().add(ruby)) player.drop(ruby, false);
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }
}
