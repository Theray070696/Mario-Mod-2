package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.crafting.ShapelessOreRecipeMario;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapelessOreRecipeWrapper extends AbstractShapelessRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final ShapelessOreRecipeMario recipe;

    public ShapelessOreRecipeWrapper(IJeiHelpers jeiHelpers, ShapelessOreRecipeMario recipe)
    {
        super(jeiHelpers.getGuiHelper());
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
