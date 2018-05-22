package io.github.Theray070696.mario2.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
        this.recipes.sort(new Comparator<IMarioRecipe>()
        {
            public int compare(IMarioRecipe recipe1, IMarioRecipe recipe2)
            {
                return recipe1 instanceof ShapelessRecipeMario && recipe2 instanceof ShapedRecipeMario ? 1 : (recipe2 instanceof
                        ShapelessRecipeMario && recipe1 instanceof ShapedRecipeMario ? -1 : (recipe2.getRecipeSize() < recipe1.getRecipeSize() ? -1
                        : (recipe2.getRecipeSize() > recipe1.getRecipeSize() ? 1 : 0)));
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
     * Adds a shaped recipe to the recipe list.
     */
    public ShapedRecipeMario addRecipe(ItemStack stack, Object... recipeComponents)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if(recipeComponents[i] instanceof String[])
        {
            String[] aString = (String[]) recipeComponents[i++];

            for(String s2 : aString)
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
            ItemStack itemStack = ItemStack.EMPTY;

            if(recipeComponents[i + 1] instanceof Item)
            {
                itemStack = new ItemStack((Item) recipeComponents[i + 1]);
            } else if(recipeComponents[i + 1] instanceof Block)
            {
                itemStack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
            } else if(recipeComponents[i + 1] instanceof ItemStack)
            {
                itemStack = (ItemStack) recipeComponents[i + 1];
            }

            map.put(character, itemStack);
        }

        ItemStack[] aItemStack = new ItemStack[j * k];

        for(int l = 0; l < j * k; ++l)
        {
            char c0 = s.charAt(l);

            if(map.containsKey(c0))
            {
                aItemStack[l] = map.get(c0).copy();
            } else
            {
                aItemStack[l] = ItemStack.EMPTY;
            }
        }

        ShapedRecipeMario shapedRecipe = new ShapedRecipeMario(j, k, aItemStack, stack);
        this.recipes.add(shapedRecipe);
        return shapedRecipe;
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
