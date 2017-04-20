package io.github.Theray070696.mariodeath.world.genlayer;

import io.github.Theray070696.mariodeath.world.biomes.ModBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/**
 * Created by Theray on 4/15/2016.
 */
public class MarioGenLayerBiomes extends GenLayer
{

    protected BiomeGenBase[] allowedBiomes = {ModBiomes.mushroomKingdom};

    public MarioGenLayerBiomes(long seed)
    {
        super(seed);
    }

    public MarioGenLayerBiomes(long seed, GenLayer genlayer)
    {
        super(seed);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int[] dest = IntCache.getIntCache(width * depth);
        for(int dz = 0; dz < depth; dz++)
        {
            for(int dx = 0; dx < width; dx++)
            {
                this.initChunkSeed(dx + x, dz + z);
                dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
            }
        }
        return dest;
    }
}