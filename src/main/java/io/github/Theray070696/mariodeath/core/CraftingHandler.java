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
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Theray070696 on 8/27/2015.
 */
@SuppressWarnings("unchecked")
public class CraftingHandler
{
    private static ItemStack goldIngot = new ItemStack(Items.GOLD_INGOT);

    public static void initCraftingRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMarioMaker), "bgb", "gbg", "bgb", 'b', "emptyQuestionMarkBlock", 'g', "itemMarioCoin"));
    }

    public static void initMarioMakerRecipes()
    {
        addRecipe(new ShapelessOreRecipeMario(goldIngot, "itemMarioCoin", "itemMarioCoin"));

        addShapeless(new ItemStack(ModItems.itemMarioCoin, 2), goldIngot);

        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockGround, 8), "sss", "sss", "sss", 's', "stone"));
        addShaped(new ItemStack(ModBlocks.blockMarioBrick, 16), "bb", "bb", 'b', new ItemStack(Blocks.BRICK_BLOCK));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new ItemStack(ModBlocks.blockMarioBrick)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockEmptyQuestionMarkUndergroundSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new ItemStack(ModBlocks.blockMarioBrickUnderground)));
        addRecipe(new ShapedOreRecipeMario(new ItemStack(ModBlocks.blockNoteBlock, 8), " n ", "ncn", " n ", 'n', new ItemStack(Blocks.NOTEBLOCK), 'c', "itemMarioCoin"));
        addShaped(new ItemStack(ModBlocks.blockGroundUnderwater), "ggg", "gwg", "ggg", 'g', new ItemStack(ModBlocks.blockGround), 'w', Items.WATER_BUCKET);

        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGroundSMW), new ItemStack(ModBlocks.blockGround), new ItemStack(ModItems.itemMarioCoin, 1, 1)));
        addRecipe(new ShapelessOreRecipeMario(new ItemStack(ModBlocks.blockGround), new ItemStack(ModBlocks.blockGroundSMW), new ItemStack(ModItems.itemMarioCoin)));

        // Block converting
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModItems.itemMarioCoin));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModItems.itemMarioCoin));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModItems.itemMarioCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModItems.itemMarioCoin, 1, 1));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB), new ItemStack(ModItems.itemMarioCoin, 1, 2));
        addShapeless(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB3), new ItemStack(ModBlocks.blockEmptyQuestionMark), new ItemStack(ModItems.itemMarioCoin, 1, 2));

        addShaped(new ItemStack(ModItems.itemOldSpice), "ccc", "csc", "ccc", 'c', new ItemStack(ModItems.itemCoinCurrency, 1, 2), 's', ModItems.itemStarMan);
        addShaped(new ItemStack(ModBlocks.blockTerry), "ccc", "csc", "ccc", 'c', new ItemStack(ModItems.itemCoinCurrency, 1, 1), 's', ModItems.itemOldSpice);
        addShaped(new ItemStack(ModItems.itemSpiceMan), "ccc", "csc", "ccc", 'c', new ItemStack(ModItems.itemCoinCurrency, 1, 3), 's', ModItems.itemOldSpice);
    }

    public static void initSmeltingRecipes()
    {
        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockGround), new ItemStack(ModBlocks.blockGroundUnderground), 0.3f);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.blockMarioBrick), new ItemStack(ModBlocks.blockMarioBrickUnderground), 0.3f);
    }

    private static void addShaped(ItemStack output, Object ... inputs)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(output, inputs);
    }

    private static void addShapeless(ItemStack output, Object ... inputs)
    {
        MarioMakerCraftingManager.getInstance().addShapelessRecipe(output, inputs);
    }

    private static void addRecipe(IMarioRecipe recipe)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(recipe);
    }
}