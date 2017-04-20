package io.github.Theray070696.mariodeath.plugins.jei;

import io.github.Theray070696.mariodeath.crafting.ShapedRecipeMario;
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
            if(itemStack != null && itemStack.stackSize != 1)
            {
                itemStack.stackSize = 1;
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> recipeItems = Arrays.asList(recipe.recipeItems);
        ItemStack recipeOutput = recipe.getRecipeOutput();
        try
        {
            ingredients.setInputs(ItemStack.class, recipeItems);
            if(recipeOutput != null)
            {
                ingredients.setOutput(ItemStack.class, recipeOutput);
            }
        } catch(RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getInputs()
    {
        return Arrays.asList(recipe.recipeItems);
    }

    @Override
    public List<ItemStack> getOutputs()
    {
        return Collections.singletonList(recipe.getRecipeOutput());
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
