package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessCoinRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.List;

public class ShapelessCoinRecipeWrapper extends AbstractShapelessRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapelessCoinRecipe recipe;

    public ShapelessCoinRecipeWrapper(IJeiHelpers jeiHelpers, ShapelessCoinRecipe recipe)
    {
        super(jeiHelpers.getGuiHelper());
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

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        minecraft.fontRenderer.drawString(this.recipe.getRequiredCoins() + " coins", recipeWidth - 58, recipeHeight - 54, Color.gray.getRGB());
    }
}
