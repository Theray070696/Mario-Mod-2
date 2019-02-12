package io.github.Theray070696.mario2.api;

import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 9/26/2017.
 */
public interface ICraftingHandler
{
    /**
     * Just use addShapedOreRecipe
     */
    @Deprecated
    void addShaped(ItemStack output, Object... inputs);

    /**
     * Just use addShapelessOreRecipe
     */
    @Deprecated
    void addShapeless(ItemStack output, Object... inputs);

    /**
     * Adds a shaped oredict recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Same as {@link net.minecraftforge.oredict.ShapedOreRecipe} input
     */
    void addShapedOreRecipe(ItemStack output, Object... inputs);

    /**
     * Adds a shapeless oredict recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param inputs Same as {@link net.minecraftforge.oredict.ShapelessOreRecipe}
     */
    void addShapelessOreRecipe(ItemStack output, Object... inputs);

    /**
     * Adds a shaped coin recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param requiredCoins How many coins are required to craft the item
     * @param inputs Same as {@link net.minecraftforge.oredict.ShapedOreRecipe}
     */
    void addShapedCoinRecipe(ItemStack output, int requiredCoins, Object... inputs);

    /**
     * Adds a shapeless coin recipe to the Mario Maker
     *
     * @param output Result of crafting
     * @param requiredCoins How many coins are required to craft the item
     * @param inputs Same as {@link net.minecraftforge.oredict.ShapelessOreRecipe}
     */
    void addShapelessCoinRecipe(ItemStack output, int requiredCoins, Object... inputs);
}
