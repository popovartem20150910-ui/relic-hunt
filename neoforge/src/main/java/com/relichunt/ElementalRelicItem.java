package com.relichunt;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ElementalRelicItem extends Item {
    public enum Power { MAGMA, ABYSS }
    private final Power power;

    public ElementalRelicItem(Power power, Properties properties) {
        super(properties);
        this.power = power;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);
        if (!level.isClientSide) {
            double radius = power == Power.MAGMA ? 6.0 : 16.0;
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(radius),
                    entity -> entity != player && entity.isAlive())) {
                if (power == Power.MAGMA) target.igniteForSeconds(5);
                else target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
            }
            player.getCooldowns().addCooldown(this, power == Power.MAGMA ? 300 : 600);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
