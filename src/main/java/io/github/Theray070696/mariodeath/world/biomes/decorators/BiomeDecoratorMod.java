package io.github.Theray070696.mariodeath.world.biomes.decorators;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/**
 * Created by Theray on 4/15/2016.
 */
public class BiomeDecoratorMod extends BiomeDecorator
{

    /**
     * The world the BiomeDecorator is currently decorating
     */
    public static World currentWorld;
    /**
     * The Biome Decorator's random number generator.
     */
    public static Random randomGenerator;
    /**
     * The X-coordinate of the chunk currently being decorated
     */
    public static int chunk_X;
    /**
     * The Z-coordinate of the chunk currently being decorated
     */
    public static int chunk_Z;
    /**
     * True if decorator should generate surface lava & water
     */
    public static boolean generateLakes;

    public BiomeDecoratorMod()
    {
        /*coalGen = new WorldGenMinable(Blocks.coal_ore, 16, ModBlocks.blockGroundUnderground);
        ironGen = new WorldGenMinable(Blocks.iron_ore, 8, ModBlocks.blockGroundUnderground);
        goldGen = new WorldGenMinable(Blocks.gold_ore, 8, ModBlocks.blockGroundUnderground);
        redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7, ModBlocks.blockGroundUnderground);
        diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7, ModBlocks.blockGroundUnderground);
        lapisGen = new WorldGenMinable(Blocks.lapis_ore, 6, ModBlocks.blockGroundUnderground);*/ // Later

        // generates lakes and lava lakes in dimension.
        generateLakes = true;
    }

    public void decorateChunk(World world, Random random, BiomeGenBase biomeGenBase, int chunkX, int chunkZ)
    {
        if(currentWorld != null)
        {
            throw new RuntimeException("Already decorating!!");
        } else
        {
            currentWorld = world;
            randomGenerator = random;
            chunk_X = chunkX;
            chunk_Z = chunkZ;
            genDecorationsForBiome(biomeGenBase);
            currentWorld = null;
            randomGenerator = null;
        }
    }

    /**
     * Decorate's biome.
     *
     * @param biome
     */
    protected void genDecorationsForBiome(BiomeGenBase biome)
    {
        BiomeDecoratorHelper.decorateBiome(biome);
    }
}