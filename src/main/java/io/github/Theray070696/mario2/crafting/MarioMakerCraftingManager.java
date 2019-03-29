package io.github.Theray070696.mario2.crafting;

import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Theray070696 on 3/31/2016.
 * Code from vanilla 1.10.2
 */
public class MarioMakerCraftingManager
{
    /**
     * The static instance of this class
     */
    private static final MarioMakerCraftingManager INSTANCE = new MarioMakerCraftingManager();
    private final List<IMarioRecipe> recipes = Lists.<IMarioRecipe>newArrayList();

    private MarioMakerCraftingManager()
    {
        this.recipes.sort(new Comparator<IMarioRecipe>()
        {
            public int compare(IMarioRecipe recipe1, IMarioRecipe recipe2)
            {
                return recipe1 instanceof ShapelessOreRecipeMario && recipe2 instanceof ShapedOreRecipeMario ? 1 : (recipe2 instanceof
                        ShapelessOreRecipeMario && recipe1 instanceof ShapedOreRecipeMario ? -1 : (recipe2.getRecipeSize() < recipe1.getRecipeSize
                        () ? -1 : (recipe2.getRecipeSize() > recipe1.getRecipeSize() ? 1 : 0)));
            }
        });
    }

    /**
     * Returns the static instance of this class
     */
    public static MarioMakerCraftingManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * Adds an IMarioRecipe to the list of crafting recipes.
     */
    public void addRecipe(IMarioRecipe recipe)
    {
        this.recipes.add(recipe);
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public ItemStack findMatchingRecipe(InventoryCrafting craftMatrix, World world)
    {
        for(IMarioRecipe recipe : this.recipes)
        {
            if(recipe.matches(craftMatrix, world))
            {
                return recipe.getCraftingResult(craftMatrix);
            }
        }

        return ItemStack.EMPTY;
    }

    /**
     * Returns an IMarioRecipe that matches the craft matrix.
     */
    public IMarioRecipe findRecipeForMatrix(InventoryCrafting craftMatrix, World world)
    {
        for(IMarioRecipe recipe : this.recipes)
        {
            if(recipe.matches(craftMatrix, world))
            {
                return recipe;
            }
        }

        return null;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World world)
    {
        for(IMarioRecipe recipe : this.recipes)
        {
            if(recipe.matches(craftMatrix, world))
            {
                return recipe.getRemainingItems(craftMatrix);
            }
        }

        ItemStack[] itemStack = new ItemStack[craftMatrix.getSizeInventory()];

        for(int i = 0; i < itemStack.length; ++i)
        {
            itemStack[i] = craftMatrix.getStackInSlot(i);
        }

        return itemStack;
    }

    public List<IMarioRecipe> getRecipeList()
    {
        return this.recipes;
    }
}
