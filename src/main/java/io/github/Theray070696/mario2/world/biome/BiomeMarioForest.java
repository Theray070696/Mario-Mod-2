package io.github.Theray070696.mario2.world.biome;

import io.github.Theray070696.mario2.world.decorator.BiomeDecoratorMarioForest;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class BiomeMarioForest extends BiomeMario
{
    public BiomeMarioForest(String registryName, BiomeProperties properties)
    {
        super(registryName, properties);

        this.decorator.treesPerChunk = 10;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.clayPerChunk = 0;

        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorMarioForest());
    }

    @Override
    public short getWeight()
    {
        return 10;
    }
}
