package io.github.Theray070696.mario2.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 4/13/2017.
 * Code from vanilla.
 */
public class ShapedRecipeMario implements IMarioRecipe
{
    /**
     * How many horizontal slots this recipe is wide.
     */
    public final int recipeWidth;
    /**
     * How many vertical slots this recipe uses.
     */
    public final int recipeHeight;
    /**
     * Is a array of ItemStack that composes the recipe.
     */
    public final ItemStack[] recipeItems;
    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    private final ItemStack recipeOutput;
    private boolean copyIngredientNBT;

    public ShapedRecipeMario(int width, int height, ItemStack[] recipeItems, ItemStack output)
    {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = recipeItems;
        this.recipeOutput = output;
    }

    @Nullable
    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        ItemStack[] itemStack = new ItemStack[inv.getSizeInventory()];

        for(int i = 0; i < itemStack.length; ++i)
        {
            ItemStack itemStack1 = inv.getStackInSlot(i);
            itemStack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemStack1);
        }

        return itemStack;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World world)
    {
        for(int i = 0; i <= 3 - this.recipeWidth; ++i)
        {
            for(int j = 0; j <= 3 - this.recipeHeight; ++j)
            {
                if(this.checkMatch(inv, i, j, true))
                {
                    return true;
                }

                if(this.checkMatch(inv, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                int k = i - p_77573_2_;
                int l = j - p_77573_3_;
                ItemStack itemStack = null;

                if(k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
                {
                    if(p_77573_4_)
                    {
                        itemStack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
                    } else
                    {
                        itemStack = this.recipeItems[k + l * this.recipeWidth];
                    }
                }

                ItemStack itemStack1 = p_77573_1_.getStackInRowAndColumn(i, j);

                if(itemStack1 != null || itemStack != null)
                {
                    if(itemStack1 == null || itemStack == null)
                    {
                        return false;
                    }

                    if(itemStack.getItem() != itemStack1.getItem())
                    {
                        return false;
                    }

                    if(itemStack.getMetadata() != 32767 && itemStack.getMetadata() != itemStack1.getMetadata())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemStack = this.getRecipeOutput().copy();

        if(this.copyIngredientNBT)
        {
            for(int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemStack1 = inv.getStackInSlot(i);

                if(itemStack1 != null && itemStack1.hasTagCompound())
                {
                    itemStack.setTagCompound(itemStack1.getTagCompound().copy());
                }
            }
        }

        return itemStack;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }
}
