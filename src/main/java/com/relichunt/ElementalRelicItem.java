package com.relichunt;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ElementalRelicItem extends Item {
    public enum Power { MAGMA, DASH, ABYSS }
    private final Power power;

    public ElementalRelicItem(Power power, Settings settings) {
        super(settings);
        this.power = power;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.pass(stack);
        if (world instanceof ServerWorld serverWorld) {
            switch (power) {
                case MAGMA -> {
                    for (LivingEntity target : serverWorld.getEntitiesByClass(LivingEntity.class,
                            new Box(player.getBlockPos()).expand(6.0),
                            entity -> entity != player && entity.isAlive())) {
                        target.setOnFireFor(5);
                    }
                    player.getItemCooldownManager().set(this, 300);
                }
                case DASH -> {
                    Vec3d forward = player.getRotationVec(1.0F).normalize();
                    player.addVelocity(forward.x * 2.0, forward.y * 0.6 + 0.35, forward.z * 2.0);
                    player.velocityModified = true;
                    player.getItemCooldownManager().set(this, 60);
                }
                case ABYSS -> {
                    for (LivingEntity target : serverWorld.getEntitiesByClass(LivingEntity.class,
                            new Box(player.getBlockPos()).expand(16.0),
                            entity -> entity != player && entity.isAlive())) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0));
                    }
                    player.getItemCooldownManager().set(this, 600);
                }
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}
