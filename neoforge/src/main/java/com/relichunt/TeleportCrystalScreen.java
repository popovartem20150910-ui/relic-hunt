package com.relichunt;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Vanilla-style first-pass navigation screen. No teleport is performed by these buttons yet. */
public class TeleportCrystalScreen extends Screen {
    private static final String[] SECTIONS = {
        "Дом", "Шахта", "Деревни", "Мои точки", "Друзья", "SOS"
    };
    private String selected = "";

    public TeleportCrystalScreen() {
        super(Component.literal("Кристалл телепортации"));
    }

    @Override
    protected void init() {
        int buttonWidth = 200;
        int left = (this.width - buttonWidth) / 2;
        int top = Math.max(35, (this.height - 7 * 24) / 2);
        for (int i = 0; i < SECTIONS.length; i++) {
            final String section = SECTIONS[i];
            this.addRenderableWidget(Button.builder(Component.literal(section),
                button -> this.selected = section)
                .bounds(left, top + i * 24, buttonWidth, 20).build());
        }
        this.addRenderableWidget(Button.builder(Component.literal("Назад"),
            button -> this.onClose())
            .bounds(left, top + 6 * 24, buttonWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
        if (!this.selected.isEmpty()) {
            graphics.drawCenteredString(this.font,
                Component.literal(this.selected + ": функция в разработке"),
                this.width / 2, this.height - 20, 0xAAAAAA);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
