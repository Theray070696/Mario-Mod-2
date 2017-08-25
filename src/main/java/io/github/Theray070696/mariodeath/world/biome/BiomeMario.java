package io.github.Theray070696.mariodeath.world.biome;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.entity.EntityGoomba;
import io.github.Theray070696.mariodeath.entity.EntityKoopa;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.util.IWeightProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2017
 */
public abstract class BiomeMario extends Biome implements IWeightProvider
{
    public BiomeMario(String registryName, Biome.BiomeProperties properties)
    {
        super(properties);
        this.setRegistryName(ModInfo.MOD_ID, registryName);
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGoomba.class, 200, 3, 6));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityKoopa.class, 200, 3, 6));
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noiseVal)
    {
        int seaLevel = world.getSeaLevel();
        IBlockState topBlockState = this.topBlock;
        IBlockState fillerBlockState = this.fillerBlock;
        int fillBlocksToFill = -1;
        int fillerDepth = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int localX = x & 15;
        int localZ = z & 15;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int y = 255; y >= 0; --y)
        {
            if(y <= rand.nextInt(5))
            {
                chunkPrimer.setBlockState(localZ, y, localX, BEDROCK);
            } else
            {
                IBlockState blockState = chunkPrimer.getBlockState(localZ, y, localX);

                if(blockState.getMaterial() == Material.AIR)
                {
                    fillBlocksToFill = -1;
                } else if(blockState.getBlock() == Blocks.STONE)
                {
                    if(fillBlocksToFill == -1)
                    {
                        if(fillerDepth <= 0)
                        {
                            topBlockState = AIR;
                            fillerBlockState = ModBlocks.blockGroundUndergroundSMW.getDefaultState();
                        } else if(y >= seaLevel - 4 && y <= seaLevel + 1)
                        {
                            topBlockState = this.topBlock;
                            fillerBlockState = this.fillerBlock;
                        }

                        if(y < seaLevel && (topBlockState == null || topBlockState.getMaterial() == Material.AIR))
                        {
                            if(this.getFloatTemperature(mutableBlockPos.setPos(x, y, z)) < 0.15F)
                            {
                                topBlockState = ModBlocks.blockGroundSnow.getDefaultState();
                            } else
                            {
                                topBlockState = WATER;
                            }
                        }

                        fillBlocksToFill = fillerDepth;

                        if(y >= seaLevel - 1)
                        {
                            chunkPrimer.setBlockState(localZ, y, localX, topBlockState);
                        } else if(y < seaLevel - 7 - fillerDepth)
                        {
                            topBlockState = AIR;
                            fillerBlockState = ModBlocks.blockGroundUndergroundSMW.getDefaultState();
                            chunkPrimer.setBlockState(localZ, y, localX, GRAVEL); // I'll let gravel generate for now. Would be more useful than
                            // the brick blocks
                        } else
                        {
                            chunkPrimer.setBlockState(localZ, y, localX, fillerBlockState);
                        }
                    } else if(fillBlocksToFill > 0)
                    {
                        --fillBlocksToFill;
                        chunkPrimer.setBlockState(localZ, y, localX, fillerBlockState);
                    }
                }
            }
        }
    }

    public abstract BiomeDecorator createBiomeDecorator();
}
