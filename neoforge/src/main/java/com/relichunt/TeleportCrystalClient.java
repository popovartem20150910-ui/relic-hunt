package com.relichunt;

import net.minecraft.client.Minecraft;

/** Client-only entry point for the teleport crystal screen. */
public final class TeleportCrystalClient {
    private TeleportCrystalClient() {}

    public static void openMenu() {
        Minecraft.getInstance().setScreen(new TeleportCrystalScreen());
    }
}
