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
    public static final Item MAGMA_HEART = new ElementalRelicItem(ElementalRelicItem.Power.MAGMA, new Item.Settings().maxCount(1));
    public static final Item WANDERER_FEATHER = new ElementalRelicItem(ElementalRelicItem.Power.DASH, new Item.Settings().maxCount(1));
    public static final Item ABYSS_EYE = new ElementalRelicItem(ElementalRelicItem.Power.ABYSS, new Item.Settings().maxCount(1));

    @Override
    public void onInitialize() {
        register("lightning_crystal", LIGHTNING_CRYSTAL);
        register("magma_heart", MAGMA_HEART);
        register("wanderer_feather", WANDERER_FEATHER);
        register("abyss_eye", ABYSS_EYE);
    }
    private static void register(String id, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(item));
    }
}
