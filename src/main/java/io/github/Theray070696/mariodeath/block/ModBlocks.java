package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.block.tile.TilePipe;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.raycore.api.block.RayBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class ModBlocks
{
    public static BlockMario blockMarioMaker;
    
    public static BlockMario blockNoteBlock;
    public static BlockMario blockEmptyQuestionMark;
    public static BlockMario blockQuestionMark;
    public static BlockMario blockEmptyQuestionMarkSMB;
    public static BlockMario blockQuestionMarkSMB;
    public static BlockMario blockEmptyQuestionMarkSMB3;
    public static BlockMario blockQuestionMarkSMB3;

    public static BlockMario blockEmptyInvisibleBlock;
    public static BlockMario blockInvisibleBlock;
    public static BlockMario blockEmptyInvisibleBlockSMB;
    public static BlockMario blockInvisibleBlockSMB;
    public static BlockMario blockEmptyInvisibleBlockSMB3;
    public static BlockMario blockInvisibleBlockSMB3;
    
    public static BlockMario blockEmptyQuestionMarkUndergroundSMB;
    public static BlockMario blockQuestionMarkUndergroundSMB;
    
    public static Block blockGround;
    public static Block blockMarioBrick;
    public static Block blockGroundUnderground;
    public static Block blockMarioBrickUnderground;
    public static Block blockGroundUnderwater;

    public static Block blockGroundSMW;
    
    public static BlockMario blockPipeBase;
    public static BlockPipe blockPipe;

    public static void initBlocks()
    {
        LogHelper.info("Loading Blocks");

        blockMarioMaker = RayBlockRegistry.register(new BlockMarioMaker());

        blockGround = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGround"));
        blockMarioBrick = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrick"));
        blockNoteBlock = RayBlockRegistry.register(new BlockNoteBlock());

        blockGroundSMW = RayBlockRegistry.register(new BlockMarioConnectedTexture(Material.GRASS).setUnlocalizedName("marioBlockGroundSMW"));

        blockEmptyQuestionMark = RayBlockRegistry.register(new BlockQuestionMarkEmpty());
        blockQuestionMark = RayBlockRegistry.register(new BlockQuestionMark());
        blockEmptyQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMarkEmptySMB());
        blockQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMarkSMB());
        blockEmptyQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMarkEmptySMB3());
        blockQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMarkSMB3());

        blockEmptyInvisibleBlock = RayBlockRegistry.register(new BlockInvisibleBlockEmpty());
        blockInvisibleBlock = RayBlockRegistry.register(new BlockInvisibleBlock());
        blockEmptyInvisibleBlockSMB = RayBlockRegistry.register(new BlockInvisibleBlockEmptySMB());
        blockInvisibleBlockSMB = RayBlockRegistry.register(new BlockInvisibleBlockSMB());
        blockEmptyInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockInvisibleBlockEmptySMB3());
        blockInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockInvisibleBlockSMB3());

        blockGroundUnderground = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGroundUnderground"));
        blockMarioBrickUnderground = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrickUnderground"));
        blockEmptyQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMarkEmptyUndergroundSMB());
        blockQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMarkUndergroundSMB());

        blockGroundUnderwater = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGroundUnderwater"));

        blockPipeBase = RayBlockRegistry.register(new BlockPipeBase());
        blockPipe = RayBlockRegistry.register(new BlockPipe());

        GameRegistry.registerTileEntity(TilePipe.class, "tileMarioPipe");

        GameRegistry.registerTileEntity(TileQuestionMark.class, "tileMarioQuestionMark");

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMark);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlock);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB3);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkUndergroundSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB3);

        LogHelper.info("Block Loading Complete");
    }
}