package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedRecipeMario;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedRecipesHandler implements IRecipeHandler<ShapedRecipeMario>
{
    @Override
    public Class<ShapedRecipeMario> getRecipeClass()
    {
        return ShapedRecipeMario.class;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return "Mario Maker";
    }

    @Override
    public String getRecipeCategoryUid(ShapedRecipeMario recipe)
    {
        return "Mario Maker";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedRecipeMario recipe)
    {
        return new ShapedRecipesWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(ShapedRecipeMario recipe)
    {
        if(recipe.getRecipeOutput() == null)
        {
            return false;
        }

        int inputCount = 0;
        for(ItemStack input : recipe.recipeItems)
        {
            if(input != null)
            {
                inputCount++;
            }
        }

        return inputCount <= 9 && inputCount != 0;
    }
}
