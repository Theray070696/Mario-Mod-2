package io.github.Theray070696.mariodeath.block;

import cpw.mods.fml.common.registry.GameRegistry;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray on 8/27/2015.
 */
public class ModBlocks
{
    public static final BlockMario blockMarioMaker = new BlockMarioMaker();
    
    public static final BlockMario blockNoteBlock = new BlockNoteBlock();
    public static final BlockMario blockEmptyQuestionMark = new BlockQuestionMarkEmpty();
    public static final BlockMario blockQuestionMark = new BlockQuestionMark();
    public static final BlockMario blockEmptyQuestionMarkSMB = new BlockQuestionMarkEmptySMB();
    public static final BlockMario blockQuestionMarkSMB = new BlockQuestionMarkSMB();

    public static final BlockMario blockEmptyInvisibleBlock = new BlockInvisibleBlockEmpty();
    public static final BlockMario blockInvisibleBlock = new BlockInvisibleBlock();
    public static final BlockMario blockEmptyInvisibleBlockSMB = new BlockInvisibleBlockEmptySMB();
    public static final BlockMario blockInvisibleBlockSMB = new BlockInvisibleBlockSMB();
    
    public static final BlockMario blockEmptyQuestionMarkUndergroundSMB = new BlockQuestionMarkEmptyUndergroundSMB();
    public static final BlockMario blockQuestionMarkUndergroundSMB = new BlockQuestionMarkUndergroundSMB();
    
    public static final Block blockGround = new BlockMario().setBlockName("marioBlockGround");
    public static final Block blockMarioBrick = new BlockMario().setBlockName("marioBlockBrick");
    public static final Block blockGroundUnderground = new BlockMario().setBlockName("marioBlockGroundUnderground");
    public static final Block blockMarioBrickUnderground = new BlockMario().setBlockName("marioBlockBrickUnderground");
    public static final Block blockGroundUnderwater = new BlockMario().setBlockName("marioBlockGroundUnderwater");

    public static void initBlocks()
    {
        LogHelper.info("Loading Blocks");

        GameRegistry.registerBlock(blockMarioMaker, "marioBlockMarioMaker");

        GameRegistry.registerBlock(blockGround, "marioBlockGround");
        GameRegistry.registerBlock(blockMarioBrick, "marioBlockBrick");
        GameRegistry.registerBlock(blockNoteBlock, "marioBlockNoteBlock");
        GameRegistry.registerBlock(blockEmptyQuestionMark, "marioBlockEmptyQuestionMark");
        GameRegistry.registerBlock(blockQuestionMark, "marioBlockQuestionMark");
        GameRegistry.registerBlock(blockEmptyQuestionMarkSMB, "marioBlockEmptyQuestionMarkSMB");
        GameRegistry.registerBlock(blockQuestionMarkSMB, "marioBlockQuestionMarkSMB");

        GameRegistry.registerBlock(blockEmptyInvisibleBlock, "marioBlockInvisibleBlockEmpty");
        GameRegistry.registerBlock(blockInvisibleBlock, "marioBlockInvisibleBlock");
        GameRegistry.registerBlock(blockEmptyInvisibleBlockSMB, "marioBlockInvisibleBlockEmptySMB");
        GameRegistry.registerBlock(blockInvisibleBlockSMB, "marioBlockInvisibleBlockSMB");

        GameRegistry.registerBlock(blockGroundUnderground, "marioBlockGroundUnderground");
        GameRegistry.registerBlock(blockMarioBrickUnderground, "marioBlockBrickUnderground");
        GameRegistry.registerBlock(blockEmptyQuestionMarkUndergroundSMB, "marioBlockEmptyQuestionMarkUndergroundSMB");
        GameRegistry.registerBlock(blockQuestionMarkUndergroundSMB, "marioBlockQuestionMarkUndergroundSMB");
    
        GameRegistry.registerBlock(blockGroundUnderwater, "marioBlockGroundUnderwater");
    
        GameRegistry.registerTileEntity(TileQuestionMark.class, "tileMarioQuestionMark");

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMark);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlock);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkUndergroundSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB);

        LogHelper.info("Block Loading Complete");
    }
}