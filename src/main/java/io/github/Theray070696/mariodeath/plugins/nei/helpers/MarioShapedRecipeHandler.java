package io.github.Theray070696.mariodeath.plugins.nei.helpers;

import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.DefaultOverlayRenderer;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.api.IStackPositioner;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.Theray070696.mariodeath.client.gui.GuiMarioMaker;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class MarioShapedRecipeHandler extends TemplateRecipeHandler
{
    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting", new Object[0]));
    }

    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiMarioMaker.class;
    }

    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.shaped", new Object[0]);
    }

    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("crafting") && this.getClass() == MarioShapedRecipeHandler.class)
        {
            List<IRecipe> recipes = MarioMakerCraftingManager.getInstance().getRecipeList();
            for(IRecipe recipe : recipes)
            {
                if(recipe instanceof ShapedRecipes)
                {
                    CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedRecipes) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                } else if(recipe instanceof ShapedOreRecipe)
                {
                    CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedOreRecipe) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                }
            }
        } else
        {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public void loadCraftingRecipes(ItemStack result)
    {
        List<IRecipe> recipes = MarioMakerCraftingManager.getInstance().getRecipeList();

        for(IRecipe recipe : recipes)
        {
            if(recipe instanceof ShapedRecipes)
            {
                if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result))
                {
                    CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedRecipes) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                }
            } else if(recipe instanceof ShapedOreRecipe)
            {
                if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result))
                {
                    CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedOreRecipe) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                }
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient)
    {
        List<IRecipe> recipes = MarioMakerCraftingManager.getInstance().getRecipeList();

        for(IRecipe recipe : recipes)
        {
            if(recipe instanceof ShapedRecipes)
            {
                CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedRecipes) recipe);

                if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient.getItem()))
                {
                    cachedRecipe.setIngredientPermutation(cachedRecipe.ingredients, ingredient);
                    this.arecipes.add(cachedRecipe);
                }
            } else if(recipe instanceof ShapedOreRecipe)
            {
                CachedShapedRecipe cachedRecipe = new CachedShapedRecipe((ShapedOreRecipe) recipe);

                if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient.getItem()))
                {
                    cachedRecipe.setIngredientPermutation(cachedRecipe.ingredients, ingredient);
                    this.arecipes.add(cachedRecipe);
                }
            }
        }
    }

    public String getGuiTexture()
    {
        return new ResourceLocation(ModInfo.MOD_ID, "textures/gui/marioMakerGUI.png").toString();
    }

    public String getOverlayIdentifier()
    {
        return "crafting";
    }

    public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return super.hasOverlay(gui, container, recipe) || this.isRecipe2x2(recipe) && RecipeInfo.hasDefaultOverlay(gui, "crafting2x2");
    }

    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe)
    {
        IRecipeOverlayRenderer renderer = super.getOverlayRenderer(gui, recipe);
        if(renderer != null)
        {
            return renderer;
        } else
        {
            IStackPositioner positioner = RecipeInfo.getStackPositioner(gui, "crafting2x2");
            return positioner == null?null:new DefaultOverlayRenderer(this.getIngredientStacks(recipe), positioner);
        }
    }

    public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
    {
        IOverlayHandler handler = super.getOverlayHandler(gui, recipe);
        return handler != null?handler:RecipeInfo.getOverlayHandler(gui, "crafting2x2");
    }

    public boolean isRecipe2x2(int recipe)
    {
        Iterator i$ = this.getIngredientStacks(recipe).iterator();

        PositionedStack stack;
        do
        {
            if(!i$.hasNext())
            {
                return true;
            }

            stack = (PositionedStack)i$.next();
        } while(stack.relx <= 43 && stack.rely <= 24);

        return false;
    }

    public class CachedShapedRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedShapedRecipe(ShapedRecipes recipe)
        {
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
            this.ingredients = new ArrayList<PositionedStack>();
            this.setIngredients(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems);
        }

        public CachedShapedRecipe(ShapedOreRecipe recipe)
        {
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
            this.ingredients = new ArrayList<PositionedStack>();
            try
            {
                int width = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 4);
                int height = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 5);

                this.setIngredients(width, height, recipe.getInput());
            } catch(Exception e)
            {
                NEIClientConfig.logger.error("Error loading recipe: ", e);
            }
        }

        public void setIngredients(int width, int height, Object[] items)
        {
            for(int x = 0; x < width; ++x)
            {
                for(int y = 0; y < height; ++y)
                {
                    if(items[y * width + x] != null)
                    {
                        PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18, false);
                        stack.setMaxSize(1);
                        this.ingredients.add(stack);
                    }
                }
            }

        }

        public List<PositionedStack> getIngredients()
        {
            return this.getCycledIngredients(MarioShapedRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult()
        {
            return this.result;
        }

        private void computeVisuals()
        {
            for(PositionedStack p : this.ingredients)
            {
                p.generatePermutations();
            }

            this.result.generatePermutations();
        }
    }
}
