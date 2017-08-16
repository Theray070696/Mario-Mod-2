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
    public static Block blockDecoration;
    public static Block blockGroundUnderground;
    public static Block blockMarioBrickUnderground;
    public static Block blockUndergroundDecoration;
    public static Block blockGroundUnderwater;
    public static Block blockGroundSnow;

    public static Block blockGroundSMW;
    
    public static BlockMario blockPipeBase;
    public static BlockPipe blockPipe;

    public static void initBlocks()
    {
        LogHelper.info("Loading Blocks");

        blockMarioMaker = RayBlockRegistry.register(new BlockMarioMaker());

        blockGround = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGround"));
        blockMarioBrick = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrick"));
        blockDecoration = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockDecoration"));
        blockNoteBlock = RayBlockRegistry.register(new BlockNoteBlock());

        blockGroundSMW = RayBlockRegistry.register(new BlockMarioConnectedTexture(Material.GRASS).setUnlocalizedName("marioBlockGroundSMW"));

        blockEmptyQuestionMark = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMark", EnumBlockType.SMW));
        blockQuestionMark = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMark", EnumBlockType.SMW));
        blockEmptyQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB", EnumBlockType.SMB));
        blockQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkSMB", EnumBlockType.SMB));
        blockEmptyQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB3", EnumBlockType.SMB3));
        blockQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkSMB3", EnumBlockType.SMB3));

        blockEmptyInvisibleBlock = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmpty", EnumBlockType.SMW_INVISIBLE));
        blockInvisibleBlock = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlock", EnumBlockType.SMW_INVISIBLE));
        blockEmptyInvisibleBlockSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB", EnumBlockType.SMB_INVISIBLE));
        blockInvisibleBlockSMB = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlockSMB", EnumBlockType.SMB_INVISIBLE));
        blockEmptyInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB3", EnumBlockType.SMB3_INVISIBLE));
        blockInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlockSMB3", EnumBlockType.SMB3_INVISIBLE));

        blockGroundUnderground = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGroundUnderground"));
        blockMarioBrickUnderground = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrickUnderground"));
        blockUndergroundDecoration = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockUndergroundDecoration"));
        blockEmptyQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkUndergroundSMB", EnumBlockType.SMB_UNDERGROUND));
        blockQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkUndergroundSMB", EnumBlockType.SMB_UNDERGROUND));

        blockGroundUnderwater = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGroundUnderwater"));

        blockGroundSnow = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockGroundSnow"));

        blockPipeBase = RayBlockRegistry.register(new BlockPipeBase());
        blockPipe = RayBlockRegistry.register(new BlockPipe());

        GameRegistry.registerTileEntity(TilePipe.class, "tileMarioPipe");

        GameRegistry.registerTileEntity(TileQuestionMark.class, "tileMarioQuestionMark");

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMark);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlock);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkUndergroundSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB3);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB3);

        LogHelper.info("Block Loading Complete");
    }
}