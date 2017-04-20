package io.github.Theray070696.mariodeath.plugins.jei;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.HoverChecker;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public abstract class AbstractShapelessRecipeWrapper extends BlankRecipeWrapper implements ICraftingRecipeWrapper
{
    private static final double shapelessIconScale = 0.5;
    private final IDrawable shapelessIcon;
    private final HoverChecker shapelessIconHoverChecker;

    public AbstractShapelessRecipeWrapper(IGuiHelper guiHelper)
    {
        ResourceLocation shapelessIconLocation = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/recipeBackground2.png");
        shapelessIcon = guiHelper.createDrawable(shapelessIconLocation, 196, 0, 19, 15);

        int iconBottom = (int) (shapelessIcon.getHeight() * shapelessIconScale);
        int iconLeft = MarioMakerRecipeCategory.width - (int) (shapelessIcon.getWidth() * shapelessIconScale);
        int iconRight = iconLeft + (int) (shapelessIcon.getWidth() * shapelessIconScale);
        shapelessIconHoverChecker = new HoverChecker(0, iconBottom, iconLeft, iconRight, 0);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

        if(hasMultipleIngredients())
        {
            int shapelessIconX = recipeWidth - (int) (shapelessIcon.getWidth() * shapelessIconScale);

            GlStateManager.pushMatrix();
            GlStateManager.scale(shapelessIconScale, shapelessIconScale, 1.0);
            GlStateManager.color(1f, 1f, 1f, 1f);
            shapelessIcon.draw(minecraft, (int) (shapelessIconX / shapelessIconScale), 0);
            GlStateManager.popMatrix();
        }
    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        if(hasMultipleIngredients() && shapelessIconHoverChecker.checkHover(mouseX, mouseY))
        {
            return Collections.singletonList(I18n.format("jei.tooltip.shapeless.recipe"));
        }

        return super.getTooltipStrings(mouseX, mouseY);
    }

    private boolean hasMultipleIngredients()
    {
        return getInputs().size() > 1;
    }
}
