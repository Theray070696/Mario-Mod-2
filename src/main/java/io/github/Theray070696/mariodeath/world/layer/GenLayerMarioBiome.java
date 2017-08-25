package io.github.Theray070696.mariodeath.world.layer;

import io.github.Theray070696.mariodeath.util.WeightedList;
import io.github.Theray070696.mariodeath.world.ModBiomes;
import io.github.Theray070696.mariodeath.world.biome.BiomeMario;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class GenLayerMarioBiome extends GenLayerMario
{
    private final WeightedList<BiomeMario> biomesToGenerate = new WeightedList<>();
    private final int totalWeight;

    public GenLayerMarioBiome(long seed, GenLayer parentGenLayer)
    {
        super(seed);

        this.biomesToGenerate.add(ModBiomes.biomeMarioPlains);
        this.biomesToGenerate.add(ModBiomes.biomeMarioForest);
        this.biomesToGenerate.add(ModBiomes.biomeMarioForestHills);

        this.totalWeight = this.biomesToGenerate.getTotalWeight();
        this.parent = parentGenLayer;
    }

    @Override
    public int[] getInts(int x, int z, int sizeX, int sizeZ)
    {
        this.parent.getInts(x, z, sizeX, sizeZ);
        int[] ints = IntCache.getIntCache(sizeX * sizeZ);

        for(int zz = 0; zz < sizeZ; ++zz)
        {
            for(int xx = 0; xx < sizeX; ++xx)
            {
                this.initChunkSeed(xx + x, zz + z);
                ints[xx + zz * sizeX] = Biome.getIdForBiome(this.biomesToGenerate.getRandomItem(this.nextInt(this.totalWeight)));
            }
        }

        return ints;
    }
}
