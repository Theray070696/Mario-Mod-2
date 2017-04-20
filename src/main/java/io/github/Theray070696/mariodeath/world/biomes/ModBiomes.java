package io.github.Theray070696.mariodeath.world.biomes;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.world.biomes.decorators.BiomeDecoratorMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

/**
 * Created by Theray on 4/15/2016.
 */
public class ModBiomes extends BiomeGenBase
{

    public static final BiomeGenBase.Height biomeHeight = new BiomeGenBase.Height(0.125F, 0.05F);

    public static BiomeGenBase mushroomKingdom;

    public ModBiomes(int biomeId)
    {
        super(biomeId);
        this.theBiomeDecorator = new BiomeDecoratorMod();
    }

    static
    {
        mushroomKingdom = (new BiomeMushroomKingdom(ConfigHandler.mushroomKingdomBiomeID).setColor(5470985).setTemperatureRainfall(0.95F, 0.9F).setHeight(biomeHeight).setBiomeName("Mushroom Kingdom"));
    }

    public static void registerWithBiomeDictionary()
    {
        BiomeDictionary.registerBiomeType(mushroomKingdom, BiomeDictionary.Type.PLAINS);
    }

    @Override
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
    {
        genBiomeModdedTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }

    /**
     * Replaces custom Stone to allow top/filler blocks to work in dimension.
     *
     * @param world
     * @param random
     * @param replacableBlock
     * @param aByte
     * @param x
     * @param y
     * @param z
     */
    public void genBiomeModdedTerrain(World world, Random random, Block[] replacableBlock, byte[] aByte, int x, int y, double z)
    {
        Block block = this.topBlock;
        byte b0 = (byte) (this.field_150604_aj & 255);
        Block block1 = this.fillerBlock;
        int k = -1;
        int l = (int) (z / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int i1 = x & 15;
        int j1 = y & 15;
        int k1 = replacableBlock.length / 256;
        for(int l1 = 255; l1 >= 0; --l1)
        {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if(l1 <= 0 + random.nextInt(5))
            {
                replacableBlock[i2] = Blocks.bedrock;
            } else
            {
                Block block2 = replacableBlock[i2];

                if(block2 != null && block2.getMaterial() != Material.air)
                {
                    if(block2 == ModBlocks.blockGroundUnderground)
                    {
                        if(k == -1)
                        {
                            if(l <= 0)
                            {
                                block = null;
                                b0 = 0;
                                block1 = ModBlocks.blockGroundUnderground;
                            } else if(l1 >= 59 && l1 <= 64)
                            {
                                block = this.topBlock;
                                b0 = (byte) (this.field_150604_aj & 255);
                                block1 = this.fillerBlock;
                            }

                            k = l;

                            if(l1 >= 62)
                            {
                                replacableBlock[i2] = block;
                                aByte[i2] = b0;
                            } else if(l1 < 56 - l)
                            {
                                block = null;
                                block1 = ModBlocks.blockGroundUnderground;
                                replacableBlock[i2] = ModBlocks.blockMarioBrickUnderground;
                            } else
                            {
                                replacableBlock[i2] = block1;
                            }
                        }
                    }
                } else
                {
                    k = -1;
                }
            }
        }
    }
}