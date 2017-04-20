package io.github.Theray070696.mariodeath.core;

import cpw.mods.fml.common.registry.GameRegistry;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mariodeath.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Theray on 8/27/2015.
 */
@SuppressWarnings("unchecked")
public class CraftingHandler
{
    private static ItemStack goldIngot = new ItemStack(Items.gold_ingot);

    public static void initCraftingRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMarioMaker), "bgb", "gbg", "bgb", 'b', "emptyQuestionMarkBlock", 'g', "itemMarioCoin"));
    }

    public static void initMarioMakerRecipes()
    {
        addRecipe(new ShapelessOreRecipe(goldIngot, "itemMarioCoin", "itemMarioCoin"));

        addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockGround, 8), "sss", "sss", "sss", 's', "stone"));
        addShaped(new ItemStack(ModBlocks.blockMarioBrick, 16), "bb", "bb", 'b', new ItemStack(Blocks.brick_block));
        addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockEmptyQuestionMarkSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new ItemStack(ModBlocks.blockMarioBrick)));
        addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockEmptyQuestionMarkUndergroundSMB, 4), " g ", "gbg", " g ", 'g', "ingotGold", 'b', new ItemStack(ModBlocks.blockMarioBrickUnderground)));
        addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockNoteBlock, 2), " n ", "ncn", " n ", 'n', new ItemStack(Blocks.noteblock), 'c', "itemMarioCoin"));
        addShaped(new ItemStack(ModBlocks.blockGroundUnderwater), "ggg", "gwg", "ggg", 'g', new ItemStack(ModBlocks.blockGround), 'w', Items.water_bucket);
    }

    public static void initSmeltingRecipes()
    {
        GameRegistry.addSmelting(goldIngot, new ItemStack(ModItems.itemMarioCoin, 2), 0.0f);

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

    private static void addRecipe(IRecipe recipe)
    {
        MarioMakerCraftingManager.getInstance().getRecipeList().add(recipe);
    }
}