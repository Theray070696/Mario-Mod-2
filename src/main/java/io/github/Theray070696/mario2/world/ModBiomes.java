package io.github.Theray070696.mario2.world;

import io.github.Theray070696.mario2.world.biome.BiomeForestOfIllusion;
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
    public static BiomeMario biomeForestOfIllusion = new BiomeForestOfIllusion();

    public static void initBiomes()
    {
        ForgeRegistries.BIOMES.register(biomeMarioPlains);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioPlains, 2));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biomeMarioPlains, 2));
        BiomeManager.addSpawnBiome(biomeMarioPlains);
        BiomeDictionary.addTypes(biomeMarioPlains, BiomeDictionary.Type.PLAINS);

        ForgeRegistries.BIOMES.register(biomeMarioForest);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForest, 2));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biomeMarioForest, 2));
        BiomeManager.addSpawnBiome(biomeMarioForest);
        BiomeDictionary.addTypes(biomeMarioForest, BiomeDictionary.Type.FOREST);

        ForgeRegistries.BIOMES.register(biomeMarioForestHills);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeMarioForestHills, 2));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biomeMarioForestHills, 2));
        BiomeManager.addSpawnBiome(biomeMarioForestHills);
        BiomeDictionary.addTypes(biomeMarioForestHills, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);

        ForgeRegistries.BIOMES.register(biomeForestOfIllusion);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biomeForestOfIllusion, 2));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biomeForestOfIllusion, 2));
        BiomeManager.addSpawnBiome(biomeForestOfIllusion);
        BiomeDictionary.addTypes(biomeForestOfIllusion, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL);
    }
}
