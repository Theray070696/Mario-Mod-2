package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.block.BlockQuestionMarkBase;
import io.github.Theray070696.mariodeath.block.EnumBlockType;
import io.github.Theray070696.mariodeath.block.ModBlocks;
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
    private WorldGenerator questionMarkUndergroundRareSMB;
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
        this.questionMarkUndergroundRareSMB = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkUndergroundSMB, Blocks.AIR, true);
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

            this.runGenerator(this.questionMarkUndergroundRareSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);
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

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(6), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(6), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(6), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(6), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(20), 25, 100);
        } */else if(world.getWorldType().getWorldTypeName().equalsIgnoreCase("atg"))
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
        } else if(!world.provider.getDimensionType().getName().equalsIgnoreCase("CompactMachinesWorld") && !world.provider.getDimensionType().getName().contains("Tardis"))
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
            if(tileEntity instanceof TileQuestionMark && (((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMW) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMW_INVISIBLE)))
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item;
                int chance = rand.nextInt(100);

                if(chance < 5)
                {
                    item = ItemsInQuestionMarks.ITEM_CAPE;
                } else if(chance >= 5 && chance < 15)
                {
                    item = ItemsInQuestionMarks.ITEM_STAR_MAN;
                } else if(chance >= 15 && chance < 25)
                {
                    item = ItemsInQuestionMarks.ITEM_1UP;
                } else if(chance >= 25 && chance < 40)
                {
                    item = ItemsInQuestionMarks.ITEM_FIRE_FLOWER;
                } else if(chance >= 40 && chance < 60)
                {
                    item = ItemsInQuestionMarks.ITEM_MUSHROOM;
                } else
                {
                    item = ItemsInQuestionMarks.ITEM_COIN;
                }

                questionMark.setItemInBlock(item);
            } else if(tileEntity instanceof TileQuestionMark && (((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB_INVISIBLE) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB_UNDERGROUND) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB_CASTLE) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB3) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB3_INVISIBLE)))
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item;
                int chance = rand.nextInt(100);

                if(chance < 10)
                {
                    item = ItemsInQuestionMarks.ITEM_STAR_MAN;
                } else if(chance >= 10 && chance < 20)
                {
                    item = ItemsInQuestionMarks.ITEM_1UP;
                } else if(chance >= 20 && chance < 35)
                {
                    item = ItemsInQuestionMarks.ITEM_FIRE_FLOWER;
                } else if(chance >= 35 && chance < 55)
                {
                    item = ItemsInQuestionMarks.ITEM_MUSHROOM;
                } else
                {
                    item = ItemsInQuestionMarks.ITEM_COIN;
                }

                questionMark.setItemInBlock(item);
            }
        }
    }
}