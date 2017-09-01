package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.world.provider.WorldProviderMario;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class ModDimension
{
    public static DimensionType DIMENSION_MARIO;

    public static void initDimension()
    {
        DIMENSION_MARIO = DimensionType.register("Mario", "_mario", ConfigHandler.marioDimensionID, WorldProviderMario.class, false);
        DimensionManager.registerDimension(ConfigHandler.marioDimensionID, DIMENSION_MARIO);
    }
}
