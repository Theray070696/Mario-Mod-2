package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.lib.ModInfo;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class MarioMakerRecipeCategory extends BlankRecipeCategory<IRecipeWrapper>
{
    public static final int width = 116;
    public static final int height = 54;
    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;
    private final IDrawable background;
    private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public MarioMakerRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/marioMakerGUI.png");
        this.background = guiHelper.createDrawable(location, 29, 16, width, height);
        this.localizedName = I18n.format("gui.mario2:marioMaker");
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid()
    {
        return "Mario Maker";
    }

    @Override
    public String getTitle()
    {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, false, 94, 18);

        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 3; ++x)
            {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        if(recipeWrapper instanceof ICustomCraftingRecipeWrapper)
        {
            ICustomCraftingRecipeWrapper customWrapper = (ICustomCraftingRecipeWrapper) recipeWrapper;
            customWrapper.setRecipe(recipeLayout, ingredients);
            return;
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        if(recipeWrapper instanceof IShapedCraftingRecipeWrapper)
        {
            IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
            craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
        } else
        {
            craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }

        guiItemStacks.set(craftOutputSlot, outputs.get(0));
    }
}
