package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedRecipeMario;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedRecipesWrapper implements IRecipeWrapper, IShapedCraftingRecipeWrapper
{
    private final ShapedRecipeMario recipe;

    public ShapedRecipesWrapper(ShapedRecipeMario recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> recipeItems = Arrays.asList(this.recipe.recipeItems);
        ItemStack recipeOutput = this.recipe.getRecipeOutput();
        try
        {
            ingredients.setInputs(ItemStack.class, recipeItems);
            if(recipeOutput != ItemStack.EMPTY)
            {
                ingredients.setOutput(ItemStack.class, recipeOutput);
            }
        } catch(RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getWidth()
    {
        return recipe.recipeWidth;
    }

    @Override
    public int getHeight()
    {
        return recipe.recipeHeight;
    }
}
