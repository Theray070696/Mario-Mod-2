package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessRecipeMario;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapelessRecipesWrapper extends AbstractShapelessRecipeWrapper
{
    private final ShapelessRecipeMario recipe;

    public ShapelessRecipesWrapper(IGuiHelper guiHelper, ShapelessRecipeMario recipe)
    {
        super(guiHelper);
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ItemStack recipeOutput = this.recipe.getRecipeOutput();

        try
        {
            ingredients.setInputs(ItemStack.class, this.recipe.recipeItems);
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
