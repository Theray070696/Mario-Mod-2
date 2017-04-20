package io.github.Theray070696.mariodeath.plugins.jei;

import io.github.Theray070696.mariodeath.crafting.ShapedOreRecipeMario;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapedOreRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapedOreRecipeMario recipe;
    private final int width;
    private final int height;

    public ShapedOreRecipeWrapper(IJeiHelpers jeiHelpers, ShapedOreRecipeMario recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        for(Object input : this.recipe.getInput())
        {
            if(input instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) input;
                if(itemStack.stackSize != 1)
                {
                    itemStack.stackSize = 1;
                }
            }
        }
        this.width = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipeMario.class, this.recipe, "width");
        this.height = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipeMario.class, this.recipe, "height");
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ItemStack recipeOutput = recipe.getRecipeOutput();

        try
        {
            List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(Arrays.asList(recipe.getInput()));
            ingredients.setInputLists(ItemStack.class, inputs);
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
        return Arrays.asList(recipe.getInput());
    }

    @Override
    public List<ItemStack> getOutputs()
    {
        return Collections.singletonList(recipe.getRecipeOutput());
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }
}
