package com.relichunt;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.function.Supplier;

/**
 * NeoForge port foundation. Only the basic registry entries below are ported.
 * Do not distribute as a feature-complete replacement for the Fabric build.
 */
@Mod(RelicHunt.MOD_ID)
public class RelicHunt {
    public static final String MOD_ID = "relic_hunt";
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final Supplier<Block> RUBY_ORE = BLOCKS.register("ruby_ore",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final Supplier<Block> POLISHING_STONE = BLOCKS.register("polishing_stone",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final Supplier<Item> WANDERER_FEATHER = ITEMS.register("wanderer_feather", () -> new WandererFeatherItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHTNING_CRYSTAL = ITEMS.register("lightning_crystal", () -> new LightningCrystalItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TIDE_CRYSTAL = ITEMS.register("tide_crystal", () -> new TideCrystalItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGMA_HEART = ITEMS.register("magma_heart", () -> new ElementalRelicItem(ElementalRelicItem.Power.MAGMA, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ABYSS_EYE = ITEMS.register("abyss_eye", () -> new ElementalRelicItem(ElementalRelicItem.Power.ABYSS, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(Tiers.DIAMOND, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 3, -2.4F))));
    public static final Supplier<Item> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(Tiers.DIAMOND, new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F))));
    public static final Supplier<Item> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(Tiers.DIAMOND, new Item.Properties().attributes(AxeItem.createAttributes(Tiers.DIAMOND, 5.0F, -3.0F))));
    public static final Supplier<Item> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(Tiers.DIAMOND, new Item.Properties().attributes(ShovelItem.createAttributes(Tiers.DIAMOND, 1.5F, -3.0F))));
    public static final Supplier<Item> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(Tiers.DIAMOND, new Item.Properties().attributes(HoeItem.createAttributes(Tiers.DIAMOND, -3.0F, 0.0F))));
    public static final Supplier<Item> WOODEN_MACE = ITEMS.registerSimpleItem("wooden_mace");
    public static final Supplier<Item> DIAMOND_MACE = ITEMS.registerSimpleItem("diamond_mace");
    public static final Supplier<Item> RUBY_MACE = ITEMS.registerSimpleItem("ruby_mace");
    public static final Supplier<Item> NETHERITE_MACE = ITEMS.registerSimpleItem("netherite_mace");
    public static final Supplier<Item> RAW_RUBY = ITEMS.registerSimpleItem("raw_ruby");
    public static final Supplier<Item> RUBY_SHARD = ITEMS.register("ruby_shard", () -> new RubyShardItem(new Item.Properties()));
    public static final Supplier<Item> RUBY = ITEMS.registerSimpleItem("ruby");
    public static final Supplier<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore",
        () -> new BlockItem(RUBY_ORE.get(), new Item.Properties()));
    public static final Supplier<Item> POLISHING_STONE_ITEM = ITEMS.register("polishing_stone",
        () -> new BlockItem(POLISHING_STONE.get(), new Item.Properties()));

    public RelicHunt(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(this::addCreativeTabs);
    }
    private void addCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(WANDERER_FEATHER.get());
            event.accept(LIGHTNING_CRYSTAL.get());
            event.accept(TIDE_CRYSTAL.get());
            event.accept(MAGMA_HEART.get());
            event.accept(ABYSS_EYE.get());
            event.accept(RUBY_SWORD.get());
            event.accept(RUBY_PICKAXE.get());
            event.accept(RUBY_AXE.get());
            event.accept(RUBY_SHOVEL.get());
            event.accept(RUBY_HOE.get());
            event.accept(WOODEN_MACE.get());
            event.accept(DIAMOND_MACE.get());
            event.accept(RUBY_MACE.get());
            event.accept(NETHERITE_MACE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(RAW_RUBY.get());
            event.accept(RUBY_SHARD.get());
            event.accept(RUBY.get());
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(RUBY_ORE_ITEM.get());
            event.accept(POLISHING_STONE_ITEM.get());
        }
    }
}
