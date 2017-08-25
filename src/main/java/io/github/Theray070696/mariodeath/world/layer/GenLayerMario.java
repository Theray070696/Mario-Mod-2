package io.github.Theray070696.mariodeath.world.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.*;

/**
 * Created by Theray070696 on 8/25/2017
 */
public abstract class GenLayerMario extends GenLayer
{
    public GenLayerMario(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType)
    {
        int biomeSize = getModdedBiomeSize(worldType, (byte) (worldType == WorldType.LARGE_BIOMES ? 7 : 5));

        GenLayer genLayer = new GenLayerIsland(1L);
        genLayer = new GenLayerFuzzyZoom(2000L, genLayer);

        genLayer = new GenLayerMarioBiome(100L, genLayer);
        genLayer = GenLayerZoom.magnify(2000L, genLayer, 1);

        genLayer = new GenLayerSubBiomes(101L, genLayer);
        genLayer = GenLayerZoom.magnify(2100L, genLayer, biomeSize);

        GenLayer indexLayer = new GenLayerVoronoiZoom(10L, genLayer);
        indexLayer.initWorldGenSeed(seed);
        genLayer.initWorldGenSeed(seed);

        return new GenLayer[]{genLayer, indexLayer, genLayer};
    }
}
