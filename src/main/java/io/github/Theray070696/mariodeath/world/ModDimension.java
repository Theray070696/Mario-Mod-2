package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.world.provider.WorldProviderMario;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class ModDimension
{
    public static final int DIMENTION_MARIO_ID = DimensionManager.getNextFreeDimId();
    public static final DimensionType DIMENSION_MARIO = DimensionType.register("Mario", "_mario", DIMENTION_MARIO_ID, WorldProviderMario.class,
            false);

    public static void initDimension()
    {
        DimensionManager.registerDimension(DIMENTION_MARIO_ID, DIMENSION_MARIO);
    }
}
