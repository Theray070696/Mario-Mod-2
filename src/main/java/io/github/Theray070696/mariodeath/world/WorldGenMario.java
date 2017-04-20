package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.block.*;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import io.github.Theray070696.mariodeath.world.gen.WorldGenMinableSingle;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class WorldGenMario implements IWorldGenerator
{
    private WorldGenerator questionMarkSMB;
    private WorldGenerator questionMarkUndergroundSMB;
    private WorldGenerator invisibleBlockSMB;

    private WorldGenerator questionMarkSMB3;
    private WorldGenerator questionMarkNotRareSMB3;
    private WorldGenerator invisibleBlockSMB3;

    private WorldGenerator questionMark;
    private WorldGenerator questionMarkNotRare;
    private WorldGenerator invisibleBlock;

    private WorldGenerator noteBlock;

    public WorldGenMario()
    {
        this.questionMarkSMB = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkSMB, Blocks.AIR, true);
        this.questionMarkUndergroundSMB = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkUndergroundSMB, Blocks.AIR, false);
        this.invisibleBlockSMB = new WorldGenMinableSingle(ModBlocks.blockInvisibleBlockSMB, Blocks.AIR, true);

        this.questionMarkSMB3 = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkSMB3, Blocks.AIR, true);
        this.questionMarkNotRareSMB3 = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkSMB3, Blocks.AIR, false);
        this.invisibleBlockSMB3 = new WorldGenMinableSingle(ModBlocks.blockInvisibleBlockSMB3, Blocks.AIR, true);

        this.questionMark = new WorldGenMinableSingle(ModBlocks.blockQuestionMark, Blocks.AIR, true);
        this.questionMarkNotRare = new WorldGenMinableSingle(ModBlocks.blockQuestionMark, Blocks.AIR, false);
        this.invisibleBlock = new WorldGenMinableSingle(ModBlocks.blockInvisibleBlock, Blocks.AIR, true);

        this.noteBlock = new WorldGenMinableSingle(ModBlocks.blockNoteBlock, Blocks.AIR, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider instanceof WorldProviderHell)
        {
            // Nether or Hellish dimensions

            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 100);

            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 100);

            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 100);
        } else if(world.provider instanceof WorldProviderEnd)
        {
            // End or End-like dimensions

            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

        } /*else if(world.provider instanceof WorldProviderMario)
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(6), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(6), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(6), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(20), 25, 100);
        } */else if(!world.provider.getDimensionType().getName().equalsIgnoreCase("CompactMachinesWorld") && !world.provider.getDimensionType().getName().contains("Tardis"))
        {
            // Overworld or some mod dimension

            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(15), 25, 100);
        }
    }

    private void runGenerator(WorldGenerator worldGenerator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight)
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

                Block block = world.getBlockState(new BlockPos(x, y-1, z)).getBlock();

                if(block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.STONE || block == Blocks.SAND || block == ModBlocks.blockGround || block == ModBlocks.blockGroundUnderground || block == ModBlocks.blockGroundUnderwater)
                {
                    worldGenerator.generate(world, rand, new BlockPos(x, y, z));
                }
            }
        } else
        {
            int heightDiff = maxHeight - minHeight + 1;
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);
                worldGenerator.generate(world, rand, new BlockPos(x, y, z));
                this.onBlockGenerated(world, x, y, z, rand);
            }
        }
    }

    private void onBlockGenerated(World world, int x, int y, int z, Random rand)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        if(block instanceof BlockQuestionMarkBase)
        {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
            if(tileEntity instanceof TileQuestionMark && block instanceof SMWQBlock)
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item = 1 + rand.nextInt(6);

                if(item == ItemsInQuestionMarks.ITEM_CAPE)
                {
                    if(rand.nextInt(100) < 50)
                    {
                        questionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_CAPE);
                    } else
                    {
                        questionMark.setItemInBlock(1 + rand.nextInt(5)); // Will make everything except capes more common, but coins a bit rarer than previous versions.
                    }
                } else
                {
                    questionMark.setItemInBlock(item);
                }
            } else if(tileEntity instanceof TileQuestionMark && (block instanceof SMBQBlock || block instanceof SMB3QBlock))
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item = 1 + rand.nextInt(5); // There should never be a cape in this type of block.

                questionMark.setItemInBlock(item);
            }
        }
    }
}