package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessOreRecipeMario;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapelessOreRecipeWrapper extends BlankRecipeWrapper implements ICraftingRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapelessOreRecipeMario recipe;

    public ShapelessOreRecipeWrapper(IJeiHelpers jeiHelpers, ShapelessOreRecipeMario recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        for(Object input : this.recipe.getInput())
        {
            if(input instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) input;
                if(itemStack.getCount() != 1)
                {
                    itemStack.setCount(1);
                }
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        IStackHelper stackHelper = this.jeiHelpers.getStackHelper();
        ItemStack recipeOutput = this.recipe.getRecipeOutput();

        try
        {
            List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(this.recipe.getInput());
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
}
