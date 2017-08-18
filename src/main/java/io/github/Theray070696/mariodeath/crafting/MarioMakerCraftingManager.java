package io.github.Theray070696.mariodeath.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Theray070696 on 3/31/2016.
 * Code from vanilla.
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
        Collections.sort(this.recipes, new Comparator<IMarioRecipe>()
        {
            public int compare(IMarioRecipe p_compare_1_, IMarioRecipe p_compare_2_)
            {
                return p_compare_1_ instanceof ShapelessRecipeMario && p_compare_2_ instanceof ShapedRecipeMario ? 1 : (p_compare_2_ instanceof
                        ShapelessRecipeMario && p_compare_1_ instanceof ShapedRecipeMario ? -1 : (p_compare_2_.getRecipeSize() < p_compare_1_
                        .getRecipeSize() ? -1 : (p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize() ? 1 : 0)));
            }
        });
    }

    /**
     * Returns the static instance of this class
     */
    public static MarioMakerCraftingManager getInstance()
    {
        /** The static instance of this class */
        return INSTANCE;
    }

    /**
     * Adds a shaped recipe to the games recipe list.
     */
    public ShapedRecipeMario addRecipe(ItemStack stack, Object... recipeComponents)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if(recipeComponents[i] instanceof String[])
        {
            String[] astring = (String[]) ((String[]) recipeComponents[i++]);

            for(String s2 : astring)
            {
                ++k;
                j = s2.length();
                s = s + s2;
            }
        } else
        {
            while(recipeComponents[i] instanceof String)
            {
                String s1 = (String) recipeComponents[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }

        Map<Character, ItemStack> map;

        for(map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2)
        {
            Character character = (Character) recipeComponents[i];
            ItemStack itemstack = null;

            if(recipeComponents[i + 1] instanceof Item)
            {
                itemstack = new ItemStack((Item) recipeComponents[i + 1]);
            } else if(recipeComponents[i + 1] instanceof Block)
            {
                itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
            } else if(recipeComponents[i + 1] instanceof ItemStack)
            {
                itemstack = (ItemStack) recipeComponents[i + 1];
            }

            map.put(character, itemstack);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for(int l = 0; l < j * k; ++l)
        {
            char c0 = s.charAt(l);

            if(map.containsKey(Character.valueOf(c0)))
            {
                aitemstack[l] = ((ItemStack) map.get(Character.valueOf(c0))).copy();
            } else
            {
                aitemstack[l] = null;
            }
        }

        ShapedRecipeMario shapedrecipes = new ShapedRecipeMario(j, k, aitemstack, stack);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }

    /**
     * Adds a shapeless crafting recipe to the the game.
     */
    public void addShapelessRecipe(ItemStack stack, Object... recipeComponents)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for(Object object : recipeComponents)
        {
            if(object instanceof ItemStack)
            {
                list.add(((ItemStack) object).copy());
            } else if(object instanceof Item)
            {
                list.add(new ItemStack((Item) object));
            } else
            {
                if(!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                list.add(new ItemStack((Block) object));
            }
        }

        this.recipes.add(new ShapelessRecipeMario(stack, list));
    }

    /**
     * Adds an IRecipe to the list of crafting recipes.
     */
    public void addRecipe(IMarioRecipe recipe)
    {
        this.recipes.add(recipe);
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    @Nullable
    public ItemStack findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        for(IMarioRecipe irecipe : this.recipes)
        {
            if(irecipe.matches(craftMatrix, worldIn))
            {
                return irecipe.getCraftingResult(craftMatrix);
            }
        }

        return null;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World worldIn)
    {
        for(IMarioRecipe irecipe : this.recipes)
        {
            if(irecipe.matches(craftMatrix, worldIn))
            {
                return irecipe.getRemainingItems(craftMatrix);
            }
        }

        ItemStack[] aitemstack = new ItemStack[craftMatrix.getSizeInventory()];

        for(int i = 0; i < aitemstack.length; ++i)
        {
            aitemstack[i] = craftMatrix.getStackInSlot(i);
        }

        return aitemstack;
    }

    public List<IMarioRecipe> getRecipeList()
    {
        return this.recipes;
    }
}
