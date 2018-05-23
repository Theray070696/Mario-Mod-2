package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapedCoinRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ShapedCoinRecipeWrapper implements IShapedCraftingRecipeWrapper, IRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapedCoinRecipe recipe;
    private final int width;
    private final int height;

    public ShapedCoinRecipeWrapper(IJeiHelpers jeiHelpers, ShapedCoinRecipe recipe)
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
        this.width = ObfuscationReflectionHelper.getPrivateValue(ShapedCoinRecipe.class, this.recipe, "width");
        this.height = ObfuscationReflectionHelper.getPrivateValue(ShapedCoinRecipe.class, this.recipe, "height");
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
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        minecraft.fontRenderer.drawString(this.recipe.getRequiredCoins() + " coins", recipeWidth - 58, recipeHeight - 54, Color.gray.getRGB());
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }
}
