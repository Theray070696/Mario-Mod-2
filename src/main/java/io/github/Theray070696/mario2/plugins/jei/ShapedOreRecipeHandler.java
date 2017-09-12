package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedOreRecipeMario;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedOreRecipeHandler implements IRecipeHandler<ShapedOreRecipeMario>
{
    private final IJeiHelpers jeiHelpers;

    public ShapedOreRecipeHandler(IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<ShapedOreRecipeMario> getRecipeClass()
    {
        return ShapedOreRecipeMario.class;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return "Mario Maker";
    }

    @Override
    public String getRecipeCategoryUid(ShapedOreRecipeMario recipe)
    {
        return "Mario Maker";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapedOreRecipeMario recipe)
    {
        return new ShapedOreRecipeWrapper(jeiHelpers, recipe);
    }

    @Override
    public boolean isRecipeValid(ShapedOreRecipeMario recipe)
    {
        if(recipe.getRecipeOutput() == null)
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
