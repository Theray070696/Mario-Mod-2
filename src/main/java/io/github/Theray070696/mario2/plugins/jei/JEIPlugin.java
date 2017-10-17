package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.client.gui.GuiMarioMaker;
import io.github.Theray070696.mario2.container.ContainerMarioMaker;
import io.github.Theray070696.mario2.crafting.MarioMakerCraftingManager;
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

        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlock));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlockSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockInvisibleBlockSMB3));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMark));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkSMB3));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkUndergroundSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.blockQuestionMarkCastleSMB));
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
        public List<Rectangle> getGuiExtraAreas(GuiMarioMaker guiMarioMaker)
        {
            return guiMarioMaker.getExtraGuiAreas();
        }
    }
}
