package io.github.Theray070696.mario2.crafting;

import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 * Code from vanilla.
 */
public class ShapelessRecipeMario implements IMarioRecipe
{
    public final List<ItemStack> recipeItems;
    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    private final ItemStack recipeOutput;

    public ShapelessRecipeMario(ItemStack output, List<ItemStack> inputList)
    {
        this.recipeOutput = output;
        this.recipeItems = inputList;
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
        List<ItemStack> list = Lists.newArrayList(this.recipeItems);

        for(int i = 0; i < inv.getHeight(); ++i)
        {
            for(int j = 0; j < inv.getWidth(); ++j)
            {
                ItemStack itemStack = inv.getStackInRowAndColumn(j, i);

                if(itemStack != null)
                {
                    boolean flag = false;

                    for(ItemStack itemStack1 : list)
                    {
                        if(itemStack.getItem() == itemStack1.getItem() && (itemStack1.getMetadata() == 32767 || itemStack.getMetadata() ==
                                itemStack1.getMetadata()))
                        {
                            flag = true;
                            list.remove(itemStack1);
                            break;
                        }
                    }

                    if(!flag)
                    {
                        return false;
                    }
                }
            }
        }

        return list.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        return this.recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeItems.size();
    }
}
