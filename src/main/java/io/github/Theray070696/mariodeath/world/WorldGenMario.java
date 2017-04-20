package io.github.Theray070696.mariodeath.world;

import cpw.mods.fml.common.IWorldGenerator;
import io.github.Theray070696.mariodeath.block.BlockQuestionMarkBase;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.block.SMBQBlock;
import io.github.Theray070696.mariodeath.block.SMWQBlock;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import io.github.Theray070696.mariodeath.world.gen.WorldGenMinableSingle;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray on 9/15/2015.
 */
public class WorldGenMario implements IWorldGenerator
{
    private WorldGenerator questionMarkSMB;
    private WorldGenerator questionMarkUndergroundSMB;
    private WorldGenerator invisibleBlockSMB;

    private WorldGenerator questionMark;
    private WorldGenerator invisibleBlock;

    private WorldGenerator noteBlock;

    public WorldGenMario()
    {
        this.questionMarkSMB = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkSMB, Blocks.air, true);
        this.questionMarkUndergroundSMB = new WorldGenMinableSingle(ModBlocks.blockQuestionMarkUndergroundSMB, Blocks.air, false);
        this.invisibleBlockSMB = new WorldGenMinableSingle(ModBlocks.blockInvisibleBlockSMB, Blocks.air, true);

        this.questionMark = new WorldGenMinableSingle(ModBlocks.blockQuestionMark, Blocks.air, true);
        this.invisibleBlock = new WorldGenMinableSingle(ModBlocks.blockInvisibleBlock, Blocks.air, true);

        this.noteBlock = new WorldGenMinableSingle(ModBlocks.blockNoteBlock, Blocks.air, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider instanceof WorldProviderHell)
        {
            // Nether or Hellish dimensions

            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 100);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 3, 100);
        } else if(world.provider instanceof WorldProviderEnd)
        {
            // End or End-like dimensions

            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

        } else if(world.provider instanceof WorldProviderMario)
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(6), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(10), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(20), 25, 100);
        } else if(!world.provider.getDimensionName().equalsIgnoreCase("CompactMachinesWorld") && !world.provider.getDimensionName().contains("Tardis"))
        {
            // Overworld or some mod dimension

            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 3, 85);
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

                Block block = world.getBlock(x, y-1, z);

                if(block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone || block == Blocks.sand || block == ModBlocks.blockGround || block == ModBlocks.blockGroundUnderground || block == ModBlocks.blockGroundUnderwater)
                {
                    worldGenerator.generate(world, rand, x, y, z);
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
                worldGenerator.generate(world, rand, x, y, z);
                this.onBlockGenerated(world, x, y, z, rand);
            }
        }
    }

    private void onBlockGenerated(World world, int x, int y, int z, Random rand)
    {
        Block block = world.getBlock(x, y, z);

        if(block != null)
        {
            if(block instanceof BlockQuestionMarkBase)
            {
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if(tileEntity instanceof TileQuestionMark && block instanceof SMWQBlock)
                {
                    TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                    int item = 1 + rand.nextInt(6);

                    if(item == ItemsInQuestionMarks.ITEM_CAPE)
                    {
                        if(rand.nextInt(50) < 25)
                        {
                            questionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_CAPE);
                        } else
                        {
                            questionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_COIN);
                        }
                    } else
                    {
                        questionMark.setItemInBlock(item);
                    }
                } else if(tileEntity instanceof TileQuestionMark && block instanceof SMBQBlock)
                {
                    TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                    int item = 1 + rand.nextInt(6);

                    if(item == ItemsInQuestionMarks.ITEM_CAPE)
                    {
                        while(item == ItemsInQuestionMarks.ITEM_CAPE) // This will gaurentee there will be ZERO capes in these question mark blocks. If your luck is bad enough, it could lock up the client/server, but that should not EVER happen.
                        {
                            item = 1 + rand.nextInt(6);
                        }
                    }

                    questionMark.setItemInBlock(item);
                }
            }
        }
    }
}