package io.github.Theray070696.mario2.api;

import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 9/26/2017.
 */
public interface ICraftingHandler
{
    /**
     * Adds a shaped recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Same as normal {@link net.minecraft.item.crafting.ShapedRecipes} input
     */
    public void addShaped(ItemStack output, Object... inputs);

    /**
     * Adds a shapeless recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Ingredients
     */
    public void addShapeless(ItemStack output, Object... inputs);

    /**
     * Adds a shaped oredict recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Same as normal {@link net.minecraftforge.oredict.ShapedOreRecipe} input
     */
    public void addShapedOreRecipe(ItemStack output, Object... inputs);

    /**
     * Adds a shapeless oredict recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Ingredients
     */
    public void addShapelessOreRecipe(ItemStack output, Object... inputs);
}
