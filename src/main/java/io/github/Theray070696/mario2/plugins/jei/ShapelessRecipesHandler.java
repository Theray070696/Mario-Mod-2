package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessRecipeMario;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapelessRecipesHandler implements IRecipeHandler<ShapelessRecipeMario>
{
    private final IGuiHelper guiHelper;

    public ShapelessRecipesHandler(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
    }

    @Override
    public Class<ShapelessRecipeMario> getRecipeClass()
    {
        return ShapelessRecipeMario.class;
    }

    @Override
    public String getRecipeCategoryUid(ShapelessRecipeMario recipe)
    {
        return "Mario Maker";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapelessRecipeMario recipe)
    {
        return new ShapelessRecipesWrapper(this.guiHelper, recipe);
    }

    @Override
    public boolean isRecipeValid(ShapelessRecipeMario recipe)
    {
        if(recipe.getRecipeOutput() == ItemStack.EMPTY)
        {
            return false;
        }

        int inputCount = 0;
        for(Object input : recipe.recipeItems)
        {
            if(input instanceof ItemStack)
            {
                inputCount++;
            } else
            {
                return false;
            }
        }

        return inputCount <= 9 && inputCount > 0;
    }
}
