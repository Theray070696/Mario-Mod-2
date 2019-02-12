package io.github.Theray070696.mario2.plugins.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.api.MarioModAPI;
import io.github.Theray070696.mario2.core.CraftingHandler;
import io.github.Theray070696.mario2.crafting.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 2/12/2019.
 */
@ZenClass("mods.mario2.MarioMaker")
@ZenRegister
public class MarioMakerCT2Handler
{
    private static List<IItemStack> shapedRecipesToRemove = new ArrayList<IItemStack>();
    private static List<IItemStack> shapedCoinRecipesToRemove = new ArrayList<IItemStack>();
    private static List<IItemStack> shapelessRecipesToRemove = new ArrayList<IItemStack>();
    private static List<IItemStack> shapelessCoinRecipesToRemove = new ArrayList<IItemStack>();

    @ZenMethod
    public static void addShaped(IItemStack output, IIngredient[][] ingredients)
    {
        MarioModAPI.marioCraftingHandler.addShapedOreRecipe(CraftTweakerMC.getItemStack(output), InputHelper.toShapedObjects(ingredients));
    }

    @ZenMethod
    public static void addShapedCoin(IItemStack output, int coinCount, IIngredient[][] ingredients)
    {
        MarioModAPI.marioCraftingHandler.addShapedCoinRecipe(CraftTweakerMC.getItemStack(output), coinCount, InputHelper.toShapedObjects
                (ingredients));
    }

    @ZenMethod
    public static void addShapeless(IItemStack output, IIngredient[] ingredients)
    {
        MarioModAPI.marioCraftingHandler.addShapelessOreRecipe(CraftTweakerMC.getItemStack(output), InputHelper.toObjects(ingredients));
    }

    @ZenMethod
    public static void addShapelessCoin(IItemStack output, int coinCount, IIngredient[] ingredients)
    {
        MarioModAPI.marioCraftingHandler.addShapelessCoinRecipe(CraftTweakerMC.getItemStack(output), coinCount, InputHelper.toObjects(ingredients));
    }

    @ZenMethod
    public static void removeAll()
    {
        MarioMod2.INSTANCE.logger.info("Received call from ZenScript to remove all Mario Maker recipes!");
        MarioMakerCraftingManager.getInstance().getRecipeList().clear();
        CraftingHandler.zsCalledRemoveAll = true;
    }

    @ZenMethod
    public static void removeShaped(IItemStack output)
    {
        shapedRecipesToRemove.add(output);
    }

    @ZenMethod
    public static void removeShapedCoin(IItemStack output)
    {
        shapedCoinRecipesToRemove.add(output);
    }

    @ZenMethod
    public static void removeShapeless(IItemStack output)
    {
        shapelessRecipesToRemove.add(output);
    }

    @ZenMethod
    public static void removeShapelessCoin(IItemStack output)
    {
        shapelessCoinRecipesToRemove.add(output);
    }

    public static void postInit()
    {
        List<IMarioRecipe> recipeList = MarioMakerCraftingManager.getInstance().getRecipeList();

        int i = 0;

        // Shaped recipes

        if(!shapedRecipesToRemove.isEmpty())
        {
            while(i < recipeList.size())
            {
                for(IItemStack output : shapedRecipesToRemove)
                {
                    if((recipeList.get(i) instanceof ShapedRecipeMario || (recipeList.get(i) instanceof ShapedOreRecipeMario && !(recipeList.get(i)
                            instanceof ICoinRecipe))) && recipeList.get(i).getRecipeOutput().isItemEqual(CraftTweakerMC.getItemStack(output)))
                    {
                        recipeList.remove(i);
                    } else
                    {
                        i++;
                    }
                }
            }

            i = 0;
        }

        // Shaped coin recipes

        if(!shapedCoinRecipesToRemove.isEmpty())
        {
            while(i < recipeList.size())
            {
                for(IItemStack output : shapedCoinRecipesToRemove)
                {
                    if(recipeList.get(i) instanceof ShapedCoinRecipe && recipeList.get(i).getRecipeOutput().isItemEqual(CraftTweakerMC.getItemStack
                            (output)))
                    {
                        recipeList.remove(i);
                    } else
                    {
                        i++;
                    }
                }
            }

            i = 0;
        }

        // Shapeless recipes

        if(!shapelessRecipesToRemove.isEmpty())
        {
            while(i < recipeList.size())
            {
                for(IItemStack output : shapelessRecipesToRemove)
                {
                    if((recipeList.get(i) instanceof ShapelessRecipeMario || (recipeList.get(i) instanceof ShapelessOreRecipeMario && !(recipeList
                            .get(i) instanceof ICoinRecipe))) && recipeList.get(i).getRecipeOutput().isItemEqual(CraftTweakerMC.getItemStack(output)))
                    {
                        recipeList.remove(i);
                    } else
                    {
                        i++;
                    }
                }
            }

            i = 0;
        }

        // Shapeless coin recipes

        if(!shapelessCoinRecipesToRemove.isEmpty())
        {
            while(i < recipeList.size())
            {
                for(IItemStack output : shapelessCoinRecipesToRemove)
                {
                    if(recipeList.get(i) instanceof ShapelessCoinRecipe && recipeList.get(i).getRecipeOutput().isItemEqual(CraftTweakerMC
                            .getItemStack(output)))
                    {
                        recipeList.remove(i);
                    } else
                    {
                        i++;
                    }
                }
            }
        }
    }
}
