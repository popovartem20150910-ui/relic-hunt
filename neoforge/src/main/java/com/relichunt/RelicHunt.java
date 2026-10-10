package com.relichunt;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
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
    public static final Supplier<Item> LIGHTNING_CRYSTAL = ITEMS.registerSimpleItem("lightning_crystal");
    public static final Supplier<Item> MAGMA_HEART = ITEMS.registerSimpleItem("magma_heart");
    public static final Supplier<Item> ABYSS_EYE = ITEMS.registerSimpleItem("abyss_eye");
    public static final Supplier<Item> RUBY_SWORD = ITEMS.registerSimpleItem("ruby_sword");
    public static final Supplier<Item> RUBY_PICKAXE = ITEMS.registerSimpleItem("ruby_pickaxe");
    public static final Supplier<Item> RUBY_AXE = ITEMS.registerSimpleItem("ruby_axe");
    public static final Supplier<Item> RUBY_SHOVEL = ITEMS.registerSimpleItem("ruby_shovel");
    public static final Supplier<Item> RUBY_HOE = ITEMS.registerSimpleItem("ruby_hoe");
    public static final Supplier<Item> WOODEN_MACE = ITEMS.registerSimpleItem("wooden_mace");
    public static final Supplier<Item> DIAMOND_MACE = ITEMS.registerSimpleItem("diamond_mace");
    public static final Supplier<Item> RUBY_MACE = ITEMS.registerSimpleItem("ruby_mace");
    public static final Supplier<Item> NETHERITE_MACE = ITEMS.registerSimpleItem("netherite_mace");
    public static final Supplier<Item> RAW_RUBY = ITEMS.registerSimpleItem("raw_ruby");
    public static final Supplier<Item> RUBY_SHARD = ITEMS.registerSimpleItem("ruby_shard");
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
