package io.github.Theray070696.mariodeath.world.biomes.decorators;

import io.github.Theray070696.mariodeath.world.biomes.ModBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

/**
 * Created by Theray on 4/15/2016.
 */
public class BiomeDecoratorHelper
{

    public BiomeDecoratorHelper()
    {

    }

    protected static void decorateBiome(BiomeGenBase biome)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(BiomeDecoratorMod.currentWorld, BiomeDecoratorMod.randomGenerator, BiomeDecoratorMod.chunk_X, BiomeDecoratorMod.chunk_Z));
        //perpere ores for generation
        initOres();
        //GenerateOres
        generateOreInBiome(biome);
    }

    /**
     * Prepare ores for generation
     */
    private static void initOres()
    {

    }

    /**
     * Geberate Ores In a Biome
     *
     * @param biome
     */
    private static void generateOreInBiome(BiomeGenBase biome)
    {
        if(biome == ModBiomes.mushroomKingdom)
        {
            // Later
        }
    }

    /**
     * Generate ores in chunks.
     *
     * @param spawnWeight
     * @param generatorToSpawn
     * @param minSpawnHeight
     * @param maxYSpawnHeight
     */
    private static void genStandardOre(int spawnWeight, WorldGenerator generatorToSpawn, int minSpawnHeight, int maxYSpawnHeight)
    {
        for(int l = 0; l < spawnWeight; ++l)
        {
            int i1 = BiomeDecoratorMod.chunk_X + BiomeDecoratorMod.randomGenerator.nextInt(16);
            int j1 = BiomeDecoratorMod.randomGenerator.nextInt(maxYSpawnHeight - minSpawnHeight) + minSpawnHeight;
            int k1 = BiomeDecoratorMod.chunk_Z + BiomeDecoratorMod.randomGenerator.nextInt(16);
            generatorToSpawn.generate(BiomeDecoratorMod.currentWorld, BiomeDecoratorMod.randomGenerator, i1, j1, k1);
        }
    }
}