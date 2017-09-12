package io.github.Theray070696.mario2.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public interface IMarioRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(InventoryCrafting inv, World worldIn);

    /**
     * Returns an Item that is the result of this recipe
     */
    @Nullable
    ItemStack getCraftingResult(InventoryCrafting inv);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    @Nullable
    ItemStack getRecipeOutput();

    ItemStack[] getRemainingItems(InventoryCrafting inv);
}
