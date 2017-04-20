package io.github.Theray070696.mariodeath.plugins.nei.helpers;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import io.github.Theray070696.mariodeath.client.gui.GuiMarioMaker;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray on 1/10/2017.
 */
public class MarioShapelessRecipeHandler extends TemplateRecipeHandler
{
    public int[][] stackorder = new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 2}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};

    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiMarioMaker.class;
    }

    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.shapeless", new Object[0]);
    }

    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("crafting") && this.getClass() == MarioShapelessRecipeHandler.class)
        {
            List<IRecipe> recipes = MarioMakerCraftingManager.getInstance().getRecipeList();

            for(IRecipe recipe : recipes)
            {
                if(recipe instanceof ShapelessRecipes)
                {
                    CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessRecipes) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                } else if(recipe instanceof ShapelessOreRecipe)
                {
                    CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessOreRecipe) recipe);
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
            if(recipe instanceof ShapelessRecipes)
            {
                if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result))
                {
                    CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessRecipes) recipe);
                    cachedRecipe.computeVisuals();
                    this.arecipes.add(cachedRecipe);
                }
            } else if(recipe instanceof ShapelessOreRecipe)
            {
                if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result))
                {
                    CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessOreRecipe) recipe);
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
            if(recipe instanceof ShapelessRecipes)
            {
                CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessRecipes) recipe);

                if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient.getItem()))
                {
                    cachedRecipe.computeVisuals();
                    if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient))
                    {
                        cachedRecipe.setIngredientPermutation(cachedRecipe.ingredients, ingredient);
                        this.arecipes.add(cachedRecipe);
                    }
                }
            } else if(recipe instanceof ShapelessOreRecipe)
            {
                CachedShapelessRecipe cachedRecipe = new CachedShapelessRecipe((ShapelessOreRecipe) recipe);

                if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient.getItem()))
                {
                    cachedRecipe.computeVisuals();
                    if(cachedRecipe.contains(cachedRecipe.ingredients, ingredient))
                    {
                        cachedRecipe.setIngredientPermutation(cachedRecipe.ingredients, ingredient);
                        this.arecipes.add(cachedRecipe);
                    }
                }
            }
        }
    }

    @Override
    public String getGuiTexture()
    {
        return new ResourceLocation(ModInfo.MOD_ID, "textures/gui/marioMakerGUI.png").toString();
    }

    public class CachedShapelessRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedShapelessRecipe(ShapelessRecipes recipe)
        {
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
            this.ingredients = new ArrayList<PositionedStack>();
            this.setIngredients(recipe.recipeItems);
        }

        public CachedShapelessRecipe(ShapelessOreRecipe recipe)
        {
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
            this.ingredients = new ArrayList<PositionedStack>();
            this.setIngredients(recipe.getInput());
        }

        public void setIngredients(List<?> items)
        {
            this.ingredients.clear();

            for(int ingred = 0; ingred < items.size(); ++ingred) {
                PositionedStack stack = new PositionedStack(items.get(ingred), 25 + MarioShapelessRecipeHandler.this.stackorder[ingred][0] * 18, 6 + MarioShapelessRecipeHandler.this.stackorder[ingred][1] * 18);
                stack.setMaxSize(1);
                this.ingredients.add(stack);
            }
        }

        public void setResult(ItemStack output)
        {
            this.result = new PositionedStack(output, 119, 24);
        }

        public List<PositionedStack> getIngredients()
        {
            return this.getCycledIngredients(MarioShapelessRecipeHandler.this.cycleticks / 20, this.ingredients);
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