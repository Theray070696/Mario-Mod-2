package io.github.Theray070696.mariodeath.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by Theray on 3/31/2016.
 */
public class MarioMakerCraftingManager
{
    /** The static instance of this class */
    private static final MarioMakerCraftingManager instance = new MarioMakerCraftingManager();
    /** A list of all the recipes added */
    private List recipes = new ArrayList();

    /**
     * Returns the static instance of this class
     */
    public static final MarioMakerCraftingManager getInstance()
    {
        /** The static instance of this class */
        return instance;
    }

    private MarioMakerCraftingManager()
    {
        Collections.sort(this.recipes, new Comparator()
        {
            public int compare(IRecipe p_compare_1_, IRecipe p_compare_2_)
            {
                return p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes ? 1 : (p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes ? -1 : (p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize() ? -1 : (p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize() ? 1 : 0)));
            }

            public int compare(Object p_compare_1_, Object p_compare_2_)
            {
                return this.compare((IRecipe) p_compare_1_, (IRecipe) p_compare_2_);
            }
        });
    }

    public ShapedRecipes addRecipe(ItemStack output, Object ... inputs)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (inputs[i] instanceof String[])
        {
            String[] astring = (String[])((String[])inputs[i++]);

            for(String s1 : astring)
            {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else
        {
            while (inputs[i] instanceof String)
            {
                String s2 = (String)inputs[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < inputs.length; i += 2)
        {
            Character character = (Character)inputs[i];
            ItemStack itemstack1 = null;

            if (inputs[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item)inputs[i + 1]);
            }
            else if (inputs[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block)inputs[i + 1], 1, 32767);
            }
            else if (inputs[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack)inputs[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, output);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }

    public void addShapelessRecipe(ItemStack output, Object ... inputs)
    {
        ArrayList arraylist = new ArrayList();
        int i = inputs.length;

        for(Object object1 : inputs)
        {
            if(object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object1).copy());
            } else if(object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object1));
            } else
            {
                if(!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        this.recipes.add(new ShapelessRecipes(output, arraylist));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World p_82787_2_)
    {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < p_82787_1_.getSizeInventory(); ++j)
        {
            ItemStack itemstack2 = p_82787_1_.getStackInSlot(j);

            if (itemstack2 != null)
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
        {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0)
            {
                i1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, i1);
        }
        else
        {
            for (j = 0; j < this.recipes.size(); ++j)
            {
                IRecipe irecipe = (IRecipe)this.recipes.get(j);

                if (irecipe.matches(p_82787_1_, p_82787_2_))
                {
                    return irecipe.getCraftingResult(p_82787_1_);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return this.recipes;
    }
}
