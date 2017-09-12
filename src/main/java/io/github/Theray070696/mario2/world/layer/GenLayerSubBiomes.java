package io.github.Theray070696.mario2.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class GenLayerSubBiomes extends GenLayerMario
{
    public GenLayerSubBiomes(long seed, GenLayer parentGenLayer)
    {
        super(seed);
        parent = parentGenLayer;
    }

    @Override
    public int[] getInts(int x, int z, int sizeX, int sizeZ)
    {
        int[] currentBiomeInts = parent.getInts(x - 2, z - 2, sizeX + 4, sizeZ + 4);
        int[] biomeInts = IntCache.getIntCache(sizeX * sizeZ);

        for(int zz = 0; zz < sizeZ; ++zz)
        {
            for(int xx = 0; xx < sizeX; ++xx)
            {
                initChunkSeed(xx + x, zz + z);
                biomeInts[xx + zz * sizeX] = currentBiomeInts[xx + 2 + (zz + 2) * (sizeX + 4)];
            }
        }

        initChunkSeed(x, z);

        return biomeInts;
    }
}
