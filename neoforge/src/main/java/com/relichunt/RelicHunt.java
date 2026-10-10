package com.relichunt;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
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
    }
}
