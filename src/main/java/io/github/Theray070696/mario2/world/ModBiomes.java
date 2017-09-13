package io.github.Theray070696.mario2.world;

import io.github.Theray070696.mario2.world.biome.BiomeMario;
import io.github.Theray070696.mario2.world.biome.BiomeMarioForest;
import io.github.Theray070696.mario2.world.biome.BiomeMarioPlains;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class ModBiomes
{
    public static BiomeMario biomeMarioPlains = new BiomeMarioPlains();
    public static BiomeMario biomeMarioForest = new BiomeMarioForest("biomemarioforest", new Biome.BiomeProperties("Mario Forest").setBaseBiome
            ("Forest").setTemperature(0.7F).setRainfall(0.8F));
    public static BiomeMario biomeMarioForestHills = new BiomeMarioForest("biomemarioforesthills", new Biome.BiomeProperties("Mario Forest Hills")
            .setBaseBiome("ForestHills").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.7F).setRainfall(0.8F));

    public static void initBiomes()
    {
        ForgeRegistries.BIOMES.register(biomeMarioPlains);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioPlains, 5));
        BiomeManager.addSpawnBiome(biomeMarioPlains);
        BiomeDictionary.addTypes(biomeMarioPlains, BiomeDictionary.Type.PLAINS);

        ForgeRegistries.BIOMES.register(biomeMarioForest);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForest, 5));
        BiomeManager.addSpawnBiome(biomeMarioForest);
        BiomeDictionary.addTypes(biomeMarioForest, BiomeDictionary.Type.FOREST);

        ForgeRegistries.BIOMES.register(biomeMarioForestHills);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForestHills, 5));
        BiomeManager.addSpawnBiome(biomeMarioForestHills);
        BiomeDictionary.addTypes(biomeMarioForestHills, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);
    }
}
