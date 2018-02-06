package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.block.tile.TilePipe;
import io.github.Theray070696.mario2.block.tile.TileQuestionMark;
import io.github.Theray070696.mario2.util.LogHelper;
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

    public static BlockMario blockEmptyQuestionMarkCastleSMB;
    public static BlockMario blockQuestionMarkCastleSMB;

    public static Block blockGround;
    public static Block blockMarioBrick;
    public static Block blockDecoration;
    public static Block blockGroundUnderground;
    public static Block blockMarioBrickUnderground;
    public static Block blockUndergroundDecoration;
    public static Block blockGroundUnderwater;
    public static Block blockGroundSnow;
    public static Block blockCastleWall;
    public static Block blockMarioCastleBrick;
    public static Block blockMarioLeaves;
    public static Block blockMarioLog;
    public static Block blockMarioPlanks;
    public static BlockMarioSapling blockMarioSapling;

    public static Block blockBeanstalk;

    public static Block blockGroundSMW;
    public static Block blockGroundUndergroundSMW;

    public static Block blockCoalOreSMW;
    public static Block blockIronOreSMW;
    public static Block blockGoldOreSMW;
    public static Block blockDiamondOreSMW;
    public static Block blockLapisOreSMW;

    public static BlockMario blockPipeBase;
    public static BlockPipe blockPipe;

    public static void initBlocks()
    {
        LogHelper.info("Loading Blocks");

        Block temp;

        blockMarioMaker = RayBlockRegistry.register(new BlockMarioMaker());

        blockGround = RayBlockRegistry.register(new BlockMario(Material.ROCK, true, true).setUnlocalizedName("marioBlockGround").setHardness(1.5F));
        blockMarioBrick = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrick").setHardness(2.0F));
        blockDecoration = RayBlockRegistry.register(new BlockMario(Material.WOOD).setUnlocalizedName("marioBlockDecoration").setHardness(5.0F));
        blockNoteBlock = RayBlockRegistry.register(new BlockNoteBlock());

        blockMarioLeaves = RayBlockRegistry.register(new BlockMarioLeaves());
        blockMarioLog = RayBlockRegistry.register(new BlockMarioLog());
        blockMarioPlanks = RayBlockRegistry.register(new BlockMario(Material.WOOD).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName
                ("marioBlockPlanks"));
        blockMarioSapling = RayBlockRegistry.register(new BlockMarioSapling());

        blockGroundSMW = RayBlockRegistry.register(new BlockMario(Material.GROUND, true, true).setUnlocalizedName("marioBlockGroundSMW").setHardness
                (0.5F));
        blockGroundUndergroundSMW = RayBlockRegistry.register(new BlockMario(Material.ROCK, true, true).setUnlocalizedName
                ("marioBlockGroundUndergroundSMW").setHardness(1.5F));

        blockCoalOreSMW = RayBlockRegistry.register(new BlockMarioOre().setUnlocalizedName("marioBlockCoalSMW").setHardness(3.0F).setResistance
                (5.0F));
        blockIronOreSMW = RayBlockRegistry.register(new BlockMarioOre().setUnlocalizedName("marioBlockIronSMW").setHardness(3.0F).setResistance
                (5.0F));
        blockGoldOreSMW = RayBlockRegistry.register(new BlockMarioOre().setUnlocalizedName("marioBlockGoldSMW").setHardness(3.0F).setResistance
                (5.0F));
        blockDiamondOreSMW = RayBlockRegistry.register(new BlockMarioOre().setUnlocalizedName("marioBlockDiamondSMW").setHardness(3.0F)
                .setResistance(5.0F));
        blockLapisOreSMW = RayBlockRegistry.register(new BlockMarioOre().setUnlocalizedName("marioBlockLapisSMW").setHardness(3.0F).setResistance
                (5.0F));

        blockEmptyQuestionMark = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMark", EnumBlockType.SMW));
        blockQuestionMark = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMark", EnumBlockType.SMW));
        blockEmptyQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB", EnumBlockType.SMB));
        blockQuestionMarkSMB = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkSMB", EnumBlockType.SMB));
        blockEmptyQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB3", EnumBlockType.SMB3));
        blockQuestionMarkSMB3 = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkSMB3", EnumBlockType.SMB3));

        blockEmptyInvisibleBlock = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmpty", EnumBlockType
                .SMW_INVISIBLE));
        blockInvisibleBlock = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlock", EnumBlockType.SMW_INVISIBLE));
        blockEmptyInvisibleBlockSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB", EnumBlockType
                .SMB_INVISIBLE));
        blockInvisibleBlockSMB = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlockSMB", EnumBlockType.SMB_INVISIBLE));
        blockEmptyInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB3", EnumBlockType
                .SMB3_INVISIBLE));
        blockInvisibleBlockSMB3 = RayBlockRegistry.register(new BlockInvisibleBlock("marioBlockInvisibleBlockSMB3", EnumBlockType.SMB3_INVISIBLE));

        blockGroundUnderground = RayBlockRegistry.register(new BlockMario(Material.ROCK, true, true).setUnlocalizedName
                ("marioBlockGroundUnderground").setHardness(1.5F));
        blockMarioBrickUnderground = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockBrickUnderground").setHardness(2.0F));
        blockUndergroundDecoration = RayBlockRegistry.register(new BlockMario(Material.WOOD).setUnlocalizedName("marioBlockUndergroundDecoration")
                .setHardness(2.0F));
        blockEmptyQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkUndergroundSMB",
                EnumBlockType.SMB_UNDERGROUND));
        blockQuestionMarkUndergroundSMB = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkUndergroundSMB", EnumBlockType
                .SMB_UNDERGROUND));

        blockCastleWall = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockCastleWall").setHardness(2.0F));
        blockMarioCastleBrick = RayBlockRegistry.register(new BlockMario().setUnlocalizedName("marioBlockCastleBrick").setHardness(2.0F));
        blockEmptyQuestionMarkCastleSMB = RayBlockRegistry.register(new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkCastleSMB",
                EnumBlockType.SMB_CASTLE));
        blockQuestionMarkCastleSMB = RayBlockRegistry.register(new BlockQuestionMark("marioBlockQuestionMarkCastleSMB", EnumBlockType.SMB_CASTLE));

        blockGroundUnderwater = RayBlockRegistry.register(new BlockMario(Material.CORAL).setUnlocalizedName("marioBlockGroundUnderwater")
                .setHardness(1.5F));

        temp = new BlockMario().setUnlocalizedName("marioBlockGroundSnow").setHardness(1.5F);
        temp.slipperiness = 0.9f;
        blockGroundSnow = RayBlockRegistry.register(temp);

        blockBeanstalk = RayBlockRegistry.register(new BlockBeanstalk());

        blockPipeBase = RayBlockRegistry.register(new BlockPipeBase());
        blockPipe = RayBlockRegistry.register(new BlockPipe());

        GameRegistry.registerTileEntity(TilePipe.class, "tileMarioPipe");

        GameRegistry.registerTileEntity(TileQuestionMark.class, "tileMarioQuestionMark");

        OreDictionary.registerOre("logWood", blockMarioLog);
        OreDictionary.registerOre("plankWood", blockMarioPlanks);
        OreDictionary.registerOre("treeLeaves", blockMarioLeaves);
        OreDictionary.registerOre("treeSapling", blockMarioSapling);

        OreDictionary.registerOre("oreCoal", blockCoalOreSMW);
        OreDictionary.registerOre("oreIron", blockIronOreSMW);
        OreDictionary.registerOre("oreGold", blockGoldOreSMW);
        OreDictionary.registerOre("oreDiamond", blockDiamondOreSMW);
        OreDictionary.registerOre("oreLapis", blockLapisOreSMW);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMark);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlock);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkUndergroundSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkCastleSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB);

        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyQuestionMarkSMB3);
        OreDictionary.registerOre("emptyQuestionMarkBlock", blockEmptyInvisibleBlockSMB3);

        LogHelper.info("Block Loading Complete");
    }
}