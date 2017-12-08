package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedRecipeMario;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedRecipesWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper
{
    private final ShapedRecipeMario recipe;

    public ShapedRecipesWrapper(ShapedRecipeMario recipe)
    {
        this.recipe = recipe;
        for(ItemStack itemStack : this.recipe.recipeItems)
        {
            if(itemStack != ItemStack.EMPTY && itemStack.getCount() != 1)
            {
                itemStack.setCount(1);
            }
        }
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
        return this.recipe.recipeWidth;
    }

    @Override
    public int getHeight()
    {
        return this.recipe.recipeHeight;
    }
}
