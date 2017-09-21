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
    private boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror)
    {
        for(int x = 0; x < 3; ++x)
        {
            for(int y = 0; y < 3; ++y)
            {
                int subX = x - startX;
                int subY = y - startY;
                ItemStack itemStack = ItemStack.EMPTY;

                if(subX >= 0 && subY >= 0 && subX < this.recipeWidth && subY < this.recipeHeight)
                {
                    if(mirror)
                    {
                        itemStack = this.recipeItems[this.recipeWidth - subX - 1 + subY * this.recipeWidth];
                    } else
                    {
                        itemStack = this.recipeItems[subX + subY * this.recipeWidth];
                    }
                }

                ItemStack itemStack1 = inv.getStackInRowAndColumn(x, y);

                if(!itemStack1.isEmpty() || !itemStack.isEmpty())
                {
                    if(itemStack1.isEmpty() || itemStack.isEmpty())
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

                if(!itemStack1.isEmpty() && itemStack1.hasTagCompound())
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
