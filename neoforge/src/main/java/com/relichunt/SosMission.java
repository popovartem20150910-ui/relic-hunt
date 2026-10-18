package com.relichunt;

import java.util.Objects;

/**
 * Server-side SOS mission state. Persistence and teleport execution are handled
 * by the server integration layer; this class contains no client-only state.
 */
public final class SosMission {
    public record ReturnPoint(String dimension, double x, double y, double z,
                              float yaw, float pitch) {
        public ReturnPoint {
            Objects.requireNonNull(dimension, "dimension");
            if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z))
                throw new IllegalArgumentException("Invalid return coordinates");
        }
    }

    public enum Status { ACTIVE, RETURN_COUNTDOWN, FINISHED }

    public static final int RETURN_DELAY_TICKS = 100; // 5 seconds at 20 TPS
    private final ReturnPoint origin;
    private Status status = Status.ACTIVE;
    private int ticksRemaining = 0;

    public SosMission(ReturnPoint origin) {
        this.origin = Objects.requireNonNull(origin, "origin");
    }

    public ReturnPoint origin() { return origin; }
    public Status status() { return status; }
    public int ticksRemaining() { return ticksRemaining; }

    /** Called after the player presses the Return button. */
    public void startReturn() {
        if (status == Status.FINISHED) throw new IllegalStateException("Mission finished");
        status = Status.RETURN_COUNTDOWN;
        ticksRemaining = RETURN_DELAY_TICKS;
    }

    /**
     * Only voluntary movement/jumping or actual damage cancels the countdown.
     * External knockback without damage and camera rotation do not call this.
     */
    public void cancelReturn() {
        if (status == Status.RETURN_COUNTDOWN) {
            status = Status.ACTIVE;
            ticksRemaining = 0;
        }
    }

    /** Returns true exactly when the server should perform a safe return teleport. */
    public boolean tick() {
        if (status != Status.RETURN_COUNTDOWN) return false;
        if (--ticksRemaining > 0) return false;
        status = Status.FINISHED;
        ticksRemaining = 0;
        return true;
    }
}
