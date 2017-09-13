package io.github.Theray070696.mario2.world;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.world.biome.BiomeMario;
import io.github.Theray070696.mario2.world.gen.WorldGenCastle;
import io.github.Theray070696.mario2.world.gen.WorldGenMinableSingle;
import io.github.Theray070696.mario2.world.gen.WorldGenQuestionMark;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class WorldGenMario implements IWorldGenerator
{
    private WorldGenerator questionMarkSMB;
    private WorldGenerator questionMarkUndergroundSMB;
    private WorldGenerator questionMarkUndergroundRareSMB;
    private WorldGenerator invisibleBlockSMB;

    private WorldGenerator questionMarkSMB3;
    private WorldGenerator questionMarkNotRareSMB3;
    private WorldGenerator invisibleBlockSMB3;

    private WorldGenerator questionMark;
    private WorldGenerator questionMarkNotRare;
    private WorldGenerator invisibleBlock;

    private WorldGenerator noteBlock;

    private WorldGenerator castle;

    public WorldGenMario()
    {
        this.questionMarkSMB = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMarkSMB, true);
        this.questionMarkUndergroundSMB = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkUndergroundSMB, Blocks.AIR, false);
        this.questionMarkUndergroundRareSMB = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkUndergroundSMB, Blocks.AIR, true);
        this.invisibleBlockSMB = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlockSMB, Blocks.AIR, true);

        this.questionMarkSMB3 = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMarkSMB3, true);
        this.questionMarkNotRareSMB3 = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkSMB, Blocks.AIR, false);
        this.invisibleBlockSMB3 = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlockSMB3, Blocks.AIR, true);

        this.questionMark = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMark, true);
        this.questionMarkNotRare = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMark, Blocks.AIR, false);
        this.invisibleBlock = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlock, Blocks.AIR, true);

        this.noteBlock = new WorldGenMinableSingle(ModBlocks.marioBlockNoteBlock, Blocks.AIR, false);

        this.castle = new WorldGenCastle();
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider instanceof WorldProviderHell)
        {
            // Nether or Hellish dimensions
            this.runGenerator(this.questionMarkUndergroundRareSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);
        } else if(world.provider instanceof WorldProviderEnd)
        {
            // End or End-like dimensions
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);
        } else if(world.provider.getBiomeForCoords(new BlockPos(chunkX * 16, 0, chunkZ * 16)) instanceof BiomeMario)
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(20), 25, 100);

            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
        } else if(world.getWorldType().getName().equalsIgnoreCase("atg"))
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 50, 128);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 50, 128);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 50, 128);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(15), 25, 128);
        } else if(!world.provider.getDimensionType().getName().equalsIgnoreCase("CompactMachinesWorld") && !world.provider.getDimensionType()
                .getName().contains("Tardis") && !world.provider.getDimensionType().getName().contains("etd"))
        {
            // Overworld or some mod dimension
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(15), 25, 100);

            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
        }
    }

    private void runGenerator(WorldGenerator worldGenerator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight,
                              int maxHeight)
    {
        if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
        {
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
        }

        if(worldGenerator == this.noteBlock)
        {
            int heightDiff = maxHeight - minHeight + 1;
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);

                Block block = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();

                if(block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.STONE || block == Blocks.SAND || block == Blocks.MYCELIUM ||
                        block == ModBlocks.marioBlockGround || block == ModBlocks.marioBlockGroundUnderground || block == ModBlocks.marioBlockGroundUnderwater ||
                        block == ModBlocks.marioBlockGroundSnow || block == ModBlocks.marioBlockCastleWall || block == ModBlocks.marioBlockGroundSMW || block ==
                        ModBlocks.marioBlockGroundUndergroundSMW)
                {
                    worldGenerator.generate(world, rand, new BlockPos(x, y, z));
                }
            }
        } else if(worldGenerator instanceof WorldGenMinableSingle)
        {
            int heightDiff = maxHeight - minHeight + 1;
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);
                worldGenerator.generate(world, rand, new BlockPos(x, y, z));
                WorldGenQuestionMark.onQuestionMarkGenerated(world, x, y, z, rand);
            }
        } else if(worldGenerator instanceof WorldGenQuestionMark) // Doing this to test out the new spawning code.
        {
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int z = chunk_Z * 16 + rand.nextInt(16);
                worldGenerator.generate(world, rand, new BlockPos(x, minHeight, z));
            }
        } else if(worldGenerator == this.castle)
        {
            int x = chunk_X * 16 + rand.nextInt(16);
            int z = chunk_Z * 16 + rand.nextInt(16);

            if((!world.getBiomeForCoordsBody(new BlockPos(x, 50, z)).equals(Biomes.OCEAN) && !world.getBiomeForCoordsBody(new BlockPos(x, 50, z))
                    .equals(Biomes.DEEP_OCEAN)) && !world.getBiomeForCoordsBody(new BlockPos(x, 50, z)).equals(Biomes.FROZEN_OCEAN) && rand.nextInt
                    (80) == 0)
            {
                int y = 50;
                BlockPos position = new BlockPos(x, y, z);

                position = world.getTopSolidOrLiquidBlock(position);

                if(!(world.getBlockState(world.getTopSolidOrLiquidBlock(position)).getBlock() instanceof BlockLiquid) && !(world.getBlockState
                        (world.getTopSolidOrLiquidBlock(position)).getBlock() instanceof BlockFluidBase))
                {
                    this.castle.generate(world, rand, position);
                }
            }
        }
    }
}