package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedCoinRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapedCoinRecipeHandler implements IRecipeHandler<ShapedCoinRecipe>
{
    private final IJeiHelpers jeiHelpers;

    public ShapedCoinRecipeHandler(IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<ShapedCoinRecipe> getRecipeClass()
    {
        return ShapedCoinRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(ShapedCoinRecipe recipe)
    {
        return "Mario Maker";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedCoinRecipe recipe)
    {
        return new ShapedCoinRecipeWrapper(jeiHelpers, recipe);
    }

    @Override
    public boolean isRecipeValid(ShapedCoinRecipe recipe)
    {
        if(recipe.getRecipeOutput() == ItemStack.EMPTY)
        {
            return false;
        }

        int inputCount = 0;
        for(Object input : recipe.getInput())
        {
            if(input instanceof List)
            {
                if(((List) input).isEmpty())
                {
                    // missing items for an oreDict name. This is normal behavior, but the recipe is invalid.
                    return false;
                }
            }

            if(input != null)
            {
                inputCount++;
            }
        }

        return inputCount <= 9 && inputCount != 0;

    }
}
