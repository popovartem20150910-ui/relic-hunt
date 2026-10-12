package com.relichunt;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    private static final double RADIUS = 6.0;

    public TideCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(stack);
        }

        if (level instanceof ServerLevel serverLevel) {
            // Visible concentric rings of water splashes around the player.
            for (double radius = 0.75; radius <= RADIUS; radius += 0.75) {
                int points = Math.max(20, (int) (radius * 16));
                for (int i = 0; i < points; i++) {
                    double angle = (Math.PI * 2.0 * i) / points;
                    double x = player.getX() + Math.cos(angle) * radius;
                    double z = player.getZ() + Math.sin(angle) * radius;
                    double y = player.getY() + 0.25;
                    serverLevel.sendParticles(ParticleTypes.SPLASH, x, y, z, 1, 0.0, 0.08, 0.0, 0.02);
                }
            }
            serverLevel.sendParticles(ParticleTypes.BUBBLE_POP,
                    player.getX(), player.getY() + 0.9, player.getZ(),
                    35, 0.65, 0.5, 0.65, 0.12);

            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_SPLASH, SoundSource.PLAYERS, 1.3F, 0.85F);

            for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(RADIUS),
                    entity -> entity != player && entity.isAlive()
                            && entity.distanceToSqr(player) <= RADIUS * RADIUS)) {
                Vec3 direction = target.position().subtract(player.position());
                Vec3 push = direction.lengthSqr() < 0.01
                        ? player.getLookAngle()
                        : direction.normalize();
                Vec3 horizontal = new Vec3(push.x, 0.0, push.z).normalize();
                if (horizontal.lengthSqr() < 0.01) {
                    horizontal = new Vec3(1.0, 0.0, 0.0);
                }
                target.push(horizontal.x * 1.8, 0.5, horizontal.z * 1.8);
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
