package io.github.Theray070696.mario2.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ShapedCoinRecipe extends ShapedOreRecipeMario implements ICoinRecipe
{
    private final int requiredCoins;

    public ShapedCoinRecipe(Block result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }

    public ShapedCoinRecipe(Item result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }

    public ShapedCoinRecipe(ItemStack result, int requiredCoins, Object... recipe)
    {
        super(result, recipe);

        this.requiredCoins = requiredCoins;
    }

    public int getRequiredCoins()
    {
        return this.requiredCoins;
    }
}
