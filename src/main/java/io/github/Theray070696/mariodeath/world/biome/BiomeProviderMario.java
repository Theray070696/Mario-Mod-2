package io.github.Theray070696.mariodeath.world.biome;

import io.github.Theray070696.mariodeath.world.ModBiomes;
import io.github.Theray070696.mariodeath.world.layer.GenLayerMario;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.storage.WorldInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class BiomeProviderMario extends BiomeProvider
{
    private static final List<Biome> allowedBiomes = Arrays.asList(ModBiomes.biomeMarioPlains, ModBiomes.biomeMarioForest, ModBiomes
            .biomeMarioForestHills);

    public BiomeProviderMario(WorldInfo info)
    {
        super(info);
    }

    @Override
    public List<Biome> getBiomesToSpawnIn()
    {
        return allowedBiomes;
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
        original = GenLayerMario.initializeAllBiomeGenerators(seed, worldType);
        return super.getModdedBiomeGenerators(worldType, seed, original);
    }
}
