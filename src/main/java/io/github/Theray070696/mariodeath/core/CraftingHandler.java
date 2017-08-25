package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.crafting.IMarioRecipe;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mariodeath.crafting.ShapedOreRecipeMario;
import io.github.Theray070696.mariodeath.crafting.ShapelessOreRecipeMario;
import io.github.Theray070696.mariodeath.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class CraftingHandler
{
    public static void initCraftingRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMarioMaker), "bgb", "gbg", "bgb", 'b', "emptyQuestionMarkBlock",
                'g', "itemMarioCoin"));

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockMarioPlanks, 4), new ItemStack(ModBlocks.blockMarioLog));
    }

    public static void initMarioMakerRecipes()
    {
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(Items.GOLD_NUGGET), "itemMarioCoin"));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModItems.itemMarioCoin, 2), "ingotGold"));

        if(OreDictionary.doesOreNameExist("seed"))
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', "seed"));
        } else
        {
            addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockBeanstalk), "r", "r", "s", 'r', "sugarcane", 's', new ItemStack(Items
                    .WHEAT_SEEDS)));
        }

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockGround, 8), "sss", "sss", "sss", 's', "stone"));
        addShaped(new ItemStack(ModBlocks.blockMarioBrick, 16), "bb", "bb", 'b', new ItemStack(Blocks.BRICK_BLOCK));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockDecoration, 4), "www", "www", "www", 'w', "plankWood"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockNoteBlock, 8), " n ", "ncn", " n ", 'n', new ItemStack(Blocks.NOTEBLOCK),
                'c', "itemMarioCoin"));
        addShaped(new ItemStack(ModBlocks.blockGroundUnderwater, 8), "ggg", "gwg", "ggg", 'g', new ItemStack(ModBlocks.blockGround), 'w', Items
                .WATER_BUCKET);
        addShapeless(new ItemStack(ModBlocks.blockGroundSnow), new ItemStack(ModBlocks.blockGround), new ItemStack(Items.SNOWBALL));
        addShaped(new ItemStack(ModBlocks.blockCastleWall, 4), "bb", "bb", 'b', new ItemStack(ModBlocks.blockMarioCastleBrick));

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new
                ItemStack(ModBlocks.blockMarioBrick)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB, 4), "bgb", "gbg", "bgb", 'g', "ingotGold", 'b',
                "blockGlass"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyQuestionMarkUndergroundSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold",
                'b', new ItemStack(ModBlocks.blockMarioBrickUnderground)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyQuestionMarkCastleSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b',
                new ItemStack(ModBlocks.blockMarioCastleBrick)));

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModItems.itemPipeLink), " p", "s ", 'p', new ItemStack(ModBlocks.blockPipe), 's',
                "stickWood"));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockPipe, 2), "i i", "iei", "iii", 'i', "ingotIron", 'e', "enderpearl"));

        // Block converting
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGroundSMW), new ItemStack(ModBlocks.blockGround), new ItemStack(ModItems
                .itemMarioCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGround), new ItemStack(ModBlocks.blockGroundSMW), new ItemStack(ModItems
                .itemMarioCoin)));

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGroundUndergroundSMW), new ItemStack(ModBlocks.blockGroundUnderground),
                new ItemStack(ModItems.itemMarioCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGroundUnderground), new ItemStack(ModBlocks.blockGroundUndergroundSMW),
                new ItemStack(ModItems.itemMarioCoin)));

        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModItems
                .itemMarioCoin));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack
                (ModItems.itemMarioCoin));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB), new ItemStack(ModBlocks.blockEmptyInvisibleBlock), new ItemStack
                (ModItems.itemMarioCoin));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB), new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB3), new ItemStack
                (ModItems.itemMarioCoin));

        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModItems
                .itemMarioCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModItems
                .itemMarioCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlock), new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB), new ItemStack
                (ModItems.itemMarioCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlock), new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB3), new ItemStack
                (ModItems.itemMarioCoin, 1, 1));

        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack
                (ModItems.itemMarioCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModItems
                .itemMarioCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB3), new ItemStack(ModBlocks.blockEmptyInvisibleBlock), new ItemStack
                (ModItems.itemMarioCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB3), new ItemStack(ModBlocks.blockEmptyInvisibleBlockSMB), new ItemStack
                (ModItems.itemMarioCoin, 1, 2));
    }

    public static void initSmeltingRecipes()
    {
        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockGround), new ItemStack(ModBlocks.blockGroundUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMarioBrick), new ItemStack(ModBlocks.blockMarioBrickUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockDecoration), new ItemStack(ModBlocks.blockUndergroundDecoration), 0.3f);

        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMarioBrickUnderground), new ItemStack(ModBlocks.blockMarioCastleBrick), 0.3f);

        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMarioLog), new ItemStack(Items.COAL, 1, 1), 0.15f);
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
