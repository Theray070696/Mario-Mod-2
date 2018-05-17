package io.github.Theray070696.mario2.core.api;

import io.github.Theray070696.mario2.api.ICraftingHandler;
import io.github.Theray070696.mario2.crafting.MarioMakerCraftingManager;
import io.github.Theray070696.mario2.crafting.ShapedOreRecipeMario;
import io.github.Theray070696.mario2.crafting.ShapelessOreRecipeMario;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 9/26/2017.
 */
public class CraftingHandlerAPI implements ICraftingHandler
{
    @Override
    public void addShaped(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(output, inputs);
    }

    @Override
    public void addShapeless(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addShapelessRecipe(output, inputs);
    }

    @Override
    public void addShapedOreRecipe(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(new ShapedOreRecipeMario(output, inputs));
    }

    @Override
    public void addShapelessOreRecipe(ItemStack output, Object... inputs)
    {
        MarioMakerCraftingManager.getInstance().addRecipe(new ShapelessOreRecipeMario(output, inputs));
    }
}
