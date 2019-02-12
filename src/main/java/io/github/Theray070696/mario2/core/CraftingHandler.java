package io.github.Theray070696.mario2.core;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.crafting.*;
import io.github.Theray070696.mario2.item.ModItems;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class CraftingHandler
{
    public static void initCraftingRecipes()
    {
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation("mario_maker"), new ItemStack(ModBlocks.marioBlockMarioMaker),
                "bgb", "gbg", "bgb", 'b', "emptyQuestionMarkBlock", 'g', "itemCoin").setRegistryName(new ResourceLocation(ModInfo.MOD_ID,
                "mario_maker")));

        ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation("mario_planks"), new ItemStack(ModBlocks.marioBlockPlanks, 4),
                new ItemStack(ModBlocks.marioBlockLog)).setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "mario_planks")));
    }

    public static void initMarioMakerRecipes()
    {
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(Items.GOLD_NUGGET), "itemCoin"));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModItems.itemCoin, 2), "ingotGold"));

        boolean foundSeeds = false;

        if(OreDictionary.doesOreNameExist("seed"))
        {
            foundSeeds = true;
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', "seed"));
        }

        if(OreDictionary.doesOreNameExist("seeds"))
        {
            foundSeeds = true;
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', "seeds"));
        }

        if(!foundSeeds)
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', new ItemStack
                    (Items.WHEAT_SEEDS)));
        }

        // Functional items/blocks
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.itemPipeLink), 5, " p", "s ", 'p', new ItemStack(ModBlocks.marioBlockPipe), 's',
                "stickWood"));
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModBlocks.marioBlockPipe, 2), 10, "i i", "iei", "iii", 'i', "ingotIron", 'e', "enderpearl"));

        // Power-ups
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.itemFireFlower), 50, "rrr", "lyl", " l ", 'r', Blocks.RED_FLOWER, 'l', "treeLeaves",
                'y', Blocks.YELLOW_FLOWER, 'b', Items.LAVA_BUCKET));
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.itemMushroom), 50, "mmm", "mim", "mmm", 'm', Blocks.RED_MUSHROOM, 'i', "ingotIron"));
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.item1Up), 100, "mmm", "msm", "mmm", 'm', Blocks.RED_MUSHROOM, 's', "slimeball"));
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.itemStarMan), 100, "g g", " d ", "g g", 'g', "ingotGold", 'd', "gemDiamond"));
        addRecipe(new ShapedCoinRecipe(new ItemStack(ModItems.itemCape), 150, "f f", "fff", "fff", 'f', "feather"));

        // Decorative blocks
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockGround, 8), "sss", "sss", "sss", 's', "stone"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBrick, 16), "bb", "bb", 'b', new ItemStack(Blocks.BRICK_BLOCK)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockDecoration, 4), "www", "www", "www", 'w', "plankWood"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockNoteBlock, 8), " n ", "ncn", " n ", 'n', new ItemStack(Blocks
                .NOTEBLOCK), 'c', "itemCoin"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundUnderwater, 8), "ggg", "gwg", "ggg", 'g', new ItemStack
                (ModBlocks.marioBlockGround), 'w', Items.WATER_BUCKET));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundSnow), new ItemStack(ModBlocks.marioBlockGround), new
                ItemStack(Items.SNOWBALL)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockCastleWall, 4), "bb", "bb", 'b', new ItemStack(ModBlocks
                .marioBlockCastleBrick)));

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b',
                new ItemStack(ModBlocks.marioBlockBrick)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmptySMB, 4), "bgb", "gbg", "bgb", 'g', "ingotGold",
                'b', "blockGlass"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkUndergroundSMB, 4), " g ", "gbg", " g ", 'g',
                "ingotGold", 'b', new ItemStack(ModBlocks.marioBlockBrickUnderground)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkCastleSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold",
                'b', new ItemStack(ModBlocks.marioBlockCastleBrick)));

        // Block converting
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundSMW), new ItemStack(ModBlocks.marioBlockGround), new
                ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGround), new ItemStack(ModBlocks.marioBlockGroundSMW), new
                ItemStack(ModItems.itemCoin)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundUndergroundSMW), new ItemStack(ModBlocks
                .marioBlockGroundUnderground), new ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundUnderground), new ItemStack(ModBlocks
                .marioBlockGroundUndergroundSMW), new ItemStack(ModItems.itemCoin)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMark), new ItemStack(ModItems.itemCoin)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModItems.itemCoin)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmptySMB), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmpty), new ItemStack(ModItems.itemCoin)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmptySMB), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmptySMB3), new ItemStack(ModItems.itemCoin)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMarkSMB), new ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmpty), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmptySMB), new ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmpty), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmptySMB3), new ItemStack(ModItems.itemCoin, 1, 1)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMarkSMB), new ItemStack(ModItems.itemCoin, 1, 2)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks
                .marioBlockEmptyQuestionMark), new ItemStack(ModItems.itemCoin, 1, 2)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmptySMB3), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmpty), new ItemStack(ModItems.itemCoin, 1, 2)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockInvisibleBlockEmptySMB3), new ItemStack(ModBlocks
                .marioBlockInvisibleBlockEmptySMB), new ItemStack(ModItems.itemCoin, 1, 2)));
    }

    public static void initSmeltingRecipes()
    {
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockLog), new ItemStack(Items.COAL, 1, 1), 0.15f);

        //Decorative blocks
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockGround), new ItemStack(ModBlocks.marioBlockGroundUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockBrick), new ItemStack(ModBlocks.marioBlockBrickUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockDecoration), new ItemStack(ModBlocks.marioBlockUndergroundDecoration), 0.3f);

        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockBrickUnderground), new ItemStack(ModBlocks.marioBlockCastleBrick), 0.3f);
    }

    private static void addRecipe(IMarioRecipe recipe)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(recipe);
    }
}
