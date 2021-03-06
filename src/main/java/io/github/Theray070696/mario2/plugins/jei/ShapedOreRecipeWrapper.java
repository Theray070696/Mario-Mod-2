package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedOreRecipeMario;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedOreRecipeWrapper implements IRecipeWrapper, IShapedCraftingRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapedOreRecipeMario recipe;

    public ShapedOreRecipeWrapper(IJeiHelpers jeiHelpers, ShapedOreRecipeMario recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        IStackHelper stackHelper = this.jeiHelpers.getStackHelper();
        ItemStack recipeOutput = this.recipe.getRecipeOutput();

        try
        {
            List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(Arrays.asList(this.recipe.getInput()));
            ingredients.setInputLists(ItemStack.class, inputs);
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
