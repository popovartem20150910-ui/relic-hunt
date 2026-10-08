package com.relichunt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RelicHunt implements ModInitializer {
    public static final String MOD_ID = "relic_hunt";
    public static final Item LIGHTNING_CRYSTAL = new LightningCrystalItem(new Item.Settings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "lightning_crystal"), LIGHTNING_CRYSTAL);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(LIGHTNING_CRYSTAL));
    }
}
