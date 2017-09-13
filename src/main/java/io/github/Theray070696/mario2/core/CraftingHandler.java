package io.github.Theray070696.mario2.core;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.crafting.IMarioRecipe;
import io.github.Theray070696.mario2.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mario2.crafting.ShapedOreRecipeMario;
import io.github.Theray070696.mario2.crafting.ShapelessOreRecipeMario;
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
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation("mario_maker"), new ItemStack(ModBlocks.marioBlockMarioMaker), "bgb",
                "gbg", "bgb", 'b', "emptyQuestionMarkBlock", 'g', "itemCoin").setRegistryName(new ResourceLocation(ModInfo.MOD_ID,
                "mario_maker")));

        ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation("mario_planks"), new ItemStack(ModBlocks.marioBlockMarioPlanks, 4),
                new ItemStack(ModBlocks.marioBlockMarioLog)).setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "mario_planks")));
    }

    public static void initMarioMakerRecipes()
    {
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(Items.GOLD_NUGGET), "itemCoin"));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModItems.itemCoin, 2), "ingotGold"));

        if(OreDictionary.doesOreNameExist("seed"))
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', "seed"));
        } else if(OreDictionary.doesOreNameExist("seeds"))
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', "seeds"));
        } else
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', new ItemStack(Items
                    .WHEAT_SEEDS)));
        }

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockGround, 8), "sss", "sss", "sss", 's', "stone"));
        addShaped(new ItemStack(ModBlocks.marioBlockMarioBrick, 16), "bb", "bb", 'b', new ItemStack(Blocks.BRICK_BLOCK));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockDecoration, 4), "www", "www", "www", 'w', "plankWood"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockNoteBlock, 8), " n ", "ncn", " n ", 'n', new ItemStack(Blocks.NOTEBLOCK),
                'c', "itemCoin"));
        addShaped(new ItemStack(ModBlocks.marioBlockGroundUnderwater, 8), "ggg", "gwg", "ggg", 'g', new ItemStack(ModBlocks.marioBlockGround), 'w', Items
                .WATER_BUCKET);
        addShapeless(new ItemStack(ModBlocks.marioBlockGroundSnow), new ItemStack(ModBlocks.marioBlockGround), new ItemStack(Items.SNOWBALL));
        addShaped(new ItemStack(ModBlocks.marioBlockCastleWall, 4), "bb", "bb", 'b', new ItemStack(ModBlocks.marioBlockMarioCastleBrick));

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new
                ItemStack(ModBlocks.marioBlockMarioBrick)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB, 4), "bgb", "gbg", "bgb", 'g', "ingotGold", 'b',
                "blockGlass"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkUndergroundSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold",
                'b', new ItemStack(ModBlocks.marioBlockMarioBrickUnderground)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkCastleSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b',
                new ItemStack(ModBlocks.marioBlockMarioCastleBrick)));

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModItems.itemPipeLink), " p", "s ", 'p', new ItemStack(ModBlocks.marioBlockPipe), 's',
                "stickWood"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.marioBlockPipe, 2), "i i", "iei", "iii", 'i', "ingotIron", 'e', "enderpearl"));

        // Block converting
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundSMW), new ItemStack(ModBlocks.marioBlockGround), new ItemStack(ModItems
                .itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGround), new ItemStack(ModBlocks.marioBlockGroundSMW), new ItemStack(ModItems
                .itemCoin)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundUndergroundSMW), new ItemStack(ModBlocks.marioBlockGroundUnderground),
                new ItemStack(ModItems.itemCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.marioBlockGroundUnderground), new ItemStack(ModBlocks.marioBlockGroundUndergroundSMW),
                new ItemStack(ModItems.itemCoin)));

        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModItems
                .itemCoin));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack
                (ModItems.itemCoin));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlock), new ItemStack
                (ModItems.itemCoin));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB3), new ItemStack
                (ModItems.itemCoin));

        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack(ModItems
                .itemCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModItems
                .itemCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlock), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB), new ItemStack
                (ModItems.itemCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlock), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB3), new ItemStack
                (ModItems.itemCoin, 1, 1));

        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB), new ItemStack
                (ModItems.itemCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.marioBlockEmptyQuestionMark), new ItemStack(ModItems
                .itemCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB3), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlock), new ItemStack
                (ModItems.itemCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB3), new ItemStack(ModBlocks.marioBlockEmptyInvisibleBlockSMB), new ItemStack
                (ModItems.itemCoin, 1, 2));
    }

    public static void initSmeltingRecipes()
    {
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockGround), new ItemStack(ModBlocks.marioBlockGroundUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockMarioBrick), new ItemStack(ModBlocks.marioBlockMarioBrickUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockDecoration), new ItemStack(ModBlocks.marioBlockUndergroundDecoration), 0.3f);

        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockMarioBrickUnderground), new ItemStack(ModBlocks.marioBlockMarioCastleBrick), 0.3f);

        GameRegistry.addSmelting(new ItemStack(ModBlocks.marioBlockMarioLog), new ItemStack(Items.COAL, 1, 1), 0.15f);
    }

    private static void addShaped(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(output, inputs);
    }

    private static void addShapeless(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addShapelessRecipe(output, inputs);
    }

    private static void addRecipe(IMarioRecipe recipe)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(recipe);
    }
}
