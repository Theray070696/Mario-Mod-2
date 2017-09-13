package io.github.Theray070696.mario2.world.biome;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.world.decorator.MarioForestBiomeDecorator;
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
        this.topBlock = ModBlocks.marioBlockGroundSMW.getDefaultState();
        this.fillerBlock = ModBlocks.marioBlockGroundSMW.getDefaultState();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new MarioForestBiomeDecorator());
    }

    @Override
    public short getWeight()
    {
        return 10;
    }
}
