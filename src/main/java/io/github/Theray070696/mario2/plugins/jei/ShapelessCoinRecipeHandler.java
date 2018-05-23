package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessCoinRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapelessCoinRecipeHandler implements IRecipeHandler<ShapelessCoinRecipe>
{
    private final IJeiHelpers jeiHelpers;

    public ShapelessCoinRecipeHandler(IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<ShapelessCoinRecipe> getRecipeClass()
    {
        return ShapelessCoinRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(ShapelessCoinRecipe recipe)
    {
        return "Mario Maker";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapelessCoinRecipe recipe)
    {
        return new ShapelessCoinRecipeWrapper(jeiHelpers, recipe);
    }

    @Override
    public boolean isRecipeValid(ShapelessCoinRecipe recipe)
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
