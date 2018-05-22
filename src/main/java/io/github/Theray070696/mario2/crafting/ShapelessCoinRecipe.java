package io.github.Theray070696.mario2.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ShapelessCoinRecipe extends ShapelessOreRecipeMario implements ICoinRecipe
{
    private final int requiredCoins;

    public ShapelessCoinRecipe(Block result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }

    public ShapelessCoinRecipe(Item result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }

    public ShapelessCoinRecipe(ItemStack result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }


    public int getRequiredCoins()
    {
        return this.requiredCoins;
    }
}