package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.lib.ModInfo;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.HoverChecker;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public abstract class AbstractShapelessRecipeWrapper implements IRecipeWrapper
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

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        if(shapelessIconHoverChecker.checkHover(mouseX, mouseY))
        {
            return Collections.singletonList(I18n.format("jei.tooltip.shapeless.recipe"));
        }

        return Collections.emptyList();
    }
}
