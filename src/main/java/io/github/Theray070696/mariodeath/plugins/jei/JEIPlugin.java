package io.github.Theray070696.mariodeath.plugins.jei;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.client.gui.GuiMarioMaker;
import io.github.Theray070696.mariodeath.container.ContainerMarioMaker;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.BlankAdvancedGuiHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new MarioMakerRecipeCategory(guiHelper));

        registry.addRecipeHandlers(new ShapedOreRecipeHandler(jeiHelpers), new ShapedRecipesHandler(), new ShapelessOreRecipeHandler(jeiHelpers),
                new ShapelessRecipesHandler(guiHelper));

        registry.addRecipeClickArea(GuiMarioMaker.class, 88, 32, 28, 23, "Mario Maker");

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

        recipeTransferRegistry.addRecipeTransferHandler(ContainerMarioMaker.class, "Mario Maker", 1, 9, 10, 36);

        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.blockMarioMaker), "Mario Maker");

        registry.addRecipes(MarioMakerCraftingManager.getInstance().getRecipeList());

        registry.addAdvancedGuiHandlers(new MarioMakerAdvancedGuiHandler());

        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlock));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlockSMB));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlockSMB3));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMark));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkSMB));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkSMB3));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkUndergroundSMB));
    }

    private static class MarioMakerAdvancedGuiHandler extends BlankAdvancedGuiHandler<GuiMarioMaker>
    {
        @Nonnull
        @Override
        public Class<GuiMarioMaker> getGuiContainerClass()
        {
            return GuiMarioMaker.class;
        }

        @Nullable
        @Override
        public List<Rectangle> getGuiExtraAreas(GuiMarioMaker guiContainer)
        {
            GuiMarioMaker guiMarioMaker = guiContainer;
            return guiMarioMaker.getExtraGuiAreas();
        }
    }
}
