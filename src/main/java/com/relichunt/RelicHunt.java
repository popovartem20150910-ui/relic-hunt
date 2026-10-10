package com.relichunt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.MaceItem;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.Item;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RelicHunt implements ModInitializer {
    public static final String MOD_ID = "relic_hunt";
    public static final Item LIGHTNING_CRYSTAL = new LightningCrystalItem(new Item.Settings().maxCount(1));
    public static final Item MAGMA_HEART = new ElementalRelicItem(ElementalRelicItem.Power.MAGMA, new Item.Settings().maxCount(1));
    public static final Item WANDERER_FEATHER = new ElementalRelicItem(ElementalRelicItem.Power.DASH, new Item.Settings().maxCount(1));
    public static final Block POLISHING_STONE = new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block RUBY_ORE = new Block(AbstractBlock.Settings.copy(Blocks.IRON_ORE));
    public static final Item RAW_RUBY = new Item(new Item.Settings());
    public static final Item RUBY_SHARD = new Item(new Item.Settings());
    public static final Item RUBY = new Item(new Item.Settings());
    public static final Item RUBY_SWORD = new SwordItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 4, -2.4f)));
    public static final Item RUBY_PICKAXE = new PickaxeItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 2, -2.8f)));
    public static final Item RUBY_AXE = new AxeItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 7, -3.0f)));
    public static final Item RUBY_SHOVEL = new ShovelItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.DIAMOND, 2, -3.0f)));
    public static final Item RUBY_HOE = new HoeItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.DIAMOND, -2, -1.0f)));
    public static final Item WOODEN_MACE = new MaceItem(new Item.Settings().maxDamage(59).attributeModifiers(MaceItem.createAttributeModifiers()));
    public static final Item DIAMOND_MACE = new MaceItem(new Item.Settings().maxDamage(1561).attributeModifiers(MaceItem.createAttributeModifiers()));
    public static final Item RUBY_MACE = new MaceItem(new Item.Settings().maxDamage(2031).attributeModifiers(MaceItem.createAttributeModifiers()));
    public static final Block GOLD_DOOR = new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));
    public static final Block DIAMOND_DOOR = new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));
    public static final Block NETHERITE_DOOR = new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));
    public static final Item ABYSS_EYE = new ElementalRelicItem(ElementalRelicItem.Power.ABYSS, new Item.Settings().maxCount(1));

    @Override
    public void onInitialize() {
        register("lightning_crystal", LIGHTNING_CRYSTAL);
        register("magma_heart", MAGMA_HEART);
        register("wanderer_feather", WANDERER_FEATHER);
        register("abyss_eye", ABYSS_EYE);
        register("ruby_sword", RUBY_SWORD);
        register("ruby_pickaxe", RUBY_PICKAXE);
        register("ruby_axe", RUBY_AXE);
        register("ruby_shovel", RUBY_SHOVEL);
        register("ruby_hoe", RUBY_HOE);
        register("wooden_mace", WOODEN_MACE);
        register("diamond_mace", DIAMOND_MACE);
        register("ruby_mace", RUBY_MACE);
        registerDoor("gold_door", GOLD_DOOR);
        registerDoor("diamond_door", DIAMOND_DOOR);
        registerDoor("netherite_door", NETHERITE_DOOR);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "raw_ruby"), RAW_RUBY);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "ruby_shard"), RUBY_SHARD);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "ruby"), RUBY);
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "ruby_ore"), RUBY_ORE);
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "polishing_stone"), POLISHING_STONE);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "polishing_stone"), new BlockItem(POLISHING_STONE, new Item.Settings()));
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "ruby_ore"), new BlockItem(RUBY_ORE, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> { entries.add(RUBY_ORE); entries.add(POLISHING_STONE); });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> { entries.add(RAW_RUBY); entries.add(RUBY_SHARD); entries.add(RUBY); });
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (!world.getBlockState(hit.getBlockPos()).isOf(POLISHING_STONE)) return ActionResult.PASS;
            ItemStack held = player.getStackInHand(hand);
            if (!held.isOf(RUBY_SHARD)) return ActionResult.PASS;
            if (!world.isClient) {
                if (!player.getAbilities().creativeMode) held.decrement(1);
                ItemStack result = new ItemStack(RUBY);
                if (!player.getInventory().insertStack(result)) player.dropItem(result, false);
            }
            return ActionResult.SUCCESS;
        });
    }
    private static void registerDoor(String id, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, id), block);
        Item item = Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(item));
    }
    private static void register(String id, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(item));
    }
}
