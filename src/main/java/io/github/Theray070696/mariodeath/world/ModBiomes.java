package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.world.biome.BiomeMarioPlains;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class ModBiomes
{
    public static Biome biomeMarioPlains;

    public static void initBiomes()
    {
        biomeMarioPlains = GameRegistry.register(new BiomeMarioPlains());
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioPlains, 10));
        BiomeManager.addSpawnBiome(biomeMarioPlains);
        BiomeDictionary.registerBiomeType(biomeMarioPlains, BiomeDictionary.Type.PLAINS);
    }
}
