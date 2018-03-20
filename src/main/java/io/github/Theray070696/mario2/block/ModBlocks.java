package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.block.tile.TilePipe;
import io.github.Theray070696.mario2.block.tile.TileQuestionMark;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray070696 on 8/27/2015.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModBlocks
{
    public static final Block marioBlockMarioMaker = null;
    public static final Block marioBlockNoteBlock = null;
    public static final Block marioBlockEmptyQuestionMark = null;
    public static final Block marioBlockQuestionMark = null;
    public static final Block marioBlockEmptyQuestionMarkSMB = null;
    public static final Block marioBlockQuestionMarkSMB = null;
    public static final Block marioBlockEmptyQuestionMarkSMB3 = null;
    public static final Block marioBlockQuestionMarkSMB3 = null;
    public static final Block marioBlockInvisibleBlockEmpty = null;
    public static final Block marioBlockInvisibleBlock = null;
    public static final Block marioBlockInvisibleBlockEmptySMB = null;
    public static final Block marioBlockInvisibleBlockSMB = null;
    public static final Block marioBlockInvisibleBlockEmptySMB3 = null;
    public static final Block marioBlockInvisibleBlockSMB3 = null;
    public static final Block marioBlockEmptyQuestionMarkUndergroundSMB = null;
    public static final Block marioBlockQuestionMarkUndergroundSMB = null;
    public static final Block marioBlockEmptyQuestionMarkCastleSMB = null;
    public static final Block marioBlockQuestionMarkCastleSMB = null;
    public static final Block marioBlockGround = null;
    public static final Block marioBlockBrick = null;
    public static final Block marioBlockDecoration = null;
    public static final Block marioBlockGroundUnderground = null;
    public static final Block marioBlockBrickUnderground = null;
    public static final Block marioBlockUndergroundDecoration = null;
    public static final Block marioBlockGroundUnderwater = null;
    public static final Block marioBlockGroundSnow = null;
    public static final Block marioBlockCastleWall = null;
    public static final Block marioBlockCastleBrick = null;
    public static final Block marioBlockLeaves = null;
    public static final Block marioBlockLog = null;
    public static final Block marioBlockPlanks = null;
    public static final Block marioBlockSapling = null;
    public static final Block marioBlockBeanstalk = null;
    public static final Block marioBlockGroundSMW = null;
    public static final Block marioBlockGroundUndergroundSMW = null;
    public static final Block marioBlockCoalSMW = null;
    public static final Block marioBlockIronSMW = null;
    public static final Block marioBlockGoldSMW = null;
    public static final Block marioBlockDiamondSMW = null;
    public static final Block marioBlockLapisSMW = null;
    public static final Block marioBlockPipeBase = null;
    public static final Block marioBlockPipe = null;

    private static final Block[] BLOCKS =
            {
                    new BlockMarioMaker(),
                    new BlockMario(Material.ROCK, true, true).setUnlocalizedName("marioBlockGround").setHardness(4.0F),
                    new BlockMario().setUnlocalizedName("marioBlockBrick").setHardness(2.0F),
                    new BlockMario(Material.WOOD).setUnlocalizedName("marioBlockDecoration").setHardness(5.0F),
                    new BlockNoteBlock(),
                    new BlockMarioLeaves(),
                    new BlockMarioLog(),
                    new BlockMario(Material.WOOD).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("marioBlockPlanks"),
                    new BlockMarioSapling(),
                    new BlockMario(Material.GROUND, true, true).setUnlocalizedName("marioBlockGroundSMW").setHardness(4.0F),
                    new BlockMario(Material.ROCK, true, true).setUnlocalizedName("marioBlockGroundUndergroundSMW").setHardness(4.0F),
                    new BlockMarioOre().setUnlocalizedName("marioBlockCoalSMW").setHardness(3.0F).setResistance(5.0F),
                    new BlockMarioOre().setUnlocalizedName("marioBlockIronSMW").setHardness(3.0F).setResistance(5.0F),
                    new BlockMarioOre().setUnlocalizedName("marioBlockGoldSMW").setHardness(3.0F).setResistance(5.0F),
                    new BlockMarioOre().setUnlocalizedName("marioBlockDiamondSMW").setHardness(3.0F).setResistance(5.0F),
                    new BlockMarioOre().setUnlocalizedName("marioBlockLapisSMW").setHardness(3.0F).setResistance(5.0F),
                    new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMark", EnumBlockType.SMW),
                    new BlockQuestionMark("marioBlockQuestionMark", EnumBlockType.SMW),
                    new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB", EnumBlockType.SMB),
                    new BlockQuestionMark("marioBlockQuestionMarkSMB", EnumBlockType.SMB),
                    new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkSMB3", EnumBlockType.SMB3),
                    new BlockQuestionMark("marioBlockQuestionMarkSMB3", EnumBlockType.SMB3),
                    new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmpty", EnumBlockType.SMW_INVISIBLE),
                    new BlockInvisibleBlock("marioBlockInvisibleBlock", EnumBlockType.SMW_INVISIBLE),
                    new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB", EnumBlockType.SMB_INVISIBLE),
                    new BlockInvisibleBlock("marioBlockInvisibleBlockSMB", EnumBlockType.SMB_INVISIBLE),
                    new BlockQuestionMarkEmpty("marioBlockInvisibleBlockEmptySMB3", EnumBlockType.SMB3_INVISIBLE),
                    new BlockInvisibleBlock("marioBlockInvisibleBlockSMB3", EnumBlockType.SMB3_INVISIBLE),
                    new BlockMario(Material.ROCK, true, true).setUnlocalizedName("marioBlockGroundUnderground").setHardness(4.0F),
                    new BlockMario().setUnlocalizedName("marioBlockBrickUnderground").setHardness(2.0F),
                    new BlockMario(Material.WOOD).setUnlocalizedName("marioBlockUndergroundDecoration").setHardness(5.0F),
                    new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkUndergroundSMB", EnumBlockType.SMB_UNDERGROUND),
                    new BlockQuestionMark("marioBlockQuestionMarkUndergroundSMB", EnumBlockType.SMB_UNDERGROUND),
                    new BlockMario().setUnlocalizedName("marioBlockCastleWall").setHardness(6.0F),
                    new BlockMario().setUnlocalizedName("marioBlockCastleBrick").setHardness(3.0F),
                    new BlockQuestionMarkEmpty("marioBlockEmptyQuestionMarkCastleSMB", EnumBlockType.SMB_CASTLE),
                    new BlockQuestionMark("marioBlockQuestionMarkCastleSMB", EnumBlockType.SMB_CASTLE),
                    new BlockMario(Material.CORAL).setUnlocalizedName("marioBlockGroundUnderwater").setHardness(4.0F),
                    new BlockMarioSnow(),
                    new BlockBeanstalk(),
                    new BlockPipeBase(),
                    new BlockPipe()
            };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BLOCKS);
        MarioMod2.INSTANCE.logger.info("Block Registration Complete");
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        for(Block block : BLOCKS)
        {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }

        MarioMod2.INSTANCE.logger.info("ItemBlock Registration Complete");
    }

    @SubscribeEvent
    public static void loadBlockModels(ModelRegistryEvent event)
    {
        for(Block block : BLOCKS)
        {
            RayCore.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, ModInfo.MOD_ID, block.getRegistryName().getResourcePath());
        }
    }

    public static void initBlocks()
    {
        MarioMod2.INSTANCE.logger.info("Loading Extra Block Data");

        GameRegistry.registerTileEntity(TilePipe.class, "tileMarioPipe");
        GameRegistry.registerTileEntity(TileQuestionMark.class, "tileMarioQuestionMark");

        OreDictionary.registerOre("logWood", marioBlockLog);
        OreDictionary.registerOre("plankWood", marioBlockPlanks);
        OreDictionary.registerOre("treeLeaves", marioBlockLeaves);
        OreDictionary.registerOre("treeSapling", marioBlockSapling);

        OreDictionary.registerOre("oreCoal", marioBlockCoalSMW);
        OreDictionary.registerOre("oreIron", marioBlockIronSMW);
        OreDictionary.registerOre("oreGold", marioBlockGoldSMW);
        OreDictionary.registerOre("oreDiamond", marioBlockDiamondSMW);
        OreDictionary.registerOre("oreLapis", marioBlockLapisSMW);

        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockEmptyQuestionMark);
        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockInvisibleBlockEmpty);

        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockEmptyQuestionMarkSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockEmptyQuestionMarkUndergroundSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockEmptyQuestionMarkCastleSMB);
        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockInvisibleBlockEmptySMB);

        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockEmptyQuestionMarkSMB3);
        OreDictionary.registerOre("emptyQuestionMarkBlock", marioBlockInvisibleBlockEmptySMB3);

        MarioMod2.INSTANCE.logger.info("Extra Block Data Loading Complete");
    }
}