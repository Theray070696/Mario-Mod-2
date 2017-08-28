package io.github.Theray070696.mariodeath.world.decorator;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.world.gen.WorldGenMarioTree;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class MarioForestBiomeDecorator extends BiomeDecorator
{
    public MarioForestBiomeDecorator()
    {
        treesPerChunk = 10;
        flowersPerChunk = 0;
        grassPerChunk = 0;
        deadBushPerChunk = 0;
        mushroomsPerChunk = 0;
        reedsPerChunk = 0;
        cactiPerChunk = 0;
        sandPerChunk = 0;
        sandPerChunk2 = 0;
        clayPerChunk = 0;
        bigMushroomsPerChunk = 0;
    }

    @Override
    public void decorate(World world, Random rand, Biome biome, BlockPos pos)
    {
        if(this.decorating)
        {
            throw new RuntimeException("Already decorating");
        } else
        {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(ModBlocks.blockGround.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.graniteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.graniteSize);
            this.dioriteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.dioriteSize);
            this.andesiteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.andesiteSize);
            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize, BlockMatcher.forBlock(Blocks
                    .STONE));
            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize, BlockMatcher.forBlock(Blocks
                    .STONE));
            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize, BlockMatcher.forBlock(Blocks
                    .STONE));
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize, BlockMatcher
                    .forBlock(Blocks.STONE));
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize, BlockMatcher
                    .forBlock(Blocks.STONE));
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize, BlockMatcher.forBlock
                    (Blocks.STONE));

            // For Mario dimension.
            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUndergroundSMW));
            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUndergroundSMW));
            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUndergroundSMW));
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize, BlockMatcher
                    .forBlock(ModBlocks.blockGroundUndergroundSMW));
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize, BlockMatcher
                    .forBlock(ModBlocks.blockGroundUndergroundSMW));
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUndergroundSMW));

            this.genDecorations(biome, world, rand);
            this.decorating = false;
        }
    }

    protected void genDecorations(Biome biome, World world, Random rand)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, chunkPos));
        this.generateOres(world, rand);

        if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SAND))
        {
            for(int i = 0; i < this.sandPerChunk2; ++i)
            {
                int j = rand.nextInt(16) + 8;
                int k = rand.nextInt(16) + 8;
                this.sandGen.generate(world, rand, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }
        }

        if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.CLAY))
        {
            for(int i1 = 0; i1 < this.clayPerChunk; ++i1)
            {
                int l1 = rand.nextInt(16) + 8;
                int i6 = rand.nextInt(16) + 8;
                this.clayGen.generate(world, rand, world.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
            }
        }

        if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SAND_PASS2))
        {
            for(int j1 = 0; j1 < this.sandPerChunk; ++j1)
            {
                int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                this.gravelAsSandGen.generate(world, rand, world.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z)));
            }
        }

        int treeChance = this.treesPerChunk;

        if(rand.nextFloat() < this.extraTreeChance)
        {
            ++treeChance;
        }

        if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
        {
            for(int j2 = 0; j2 < treeChance; ++j2)
            {
                int x = rand.nextInt(16) + 8;
                int z = rand.nextInt(16) + 8;
                WorldGenMarioTree worldGenTree = new WorldGenMarioTree();
                worldGenTree.setDecorationDefaults();
                BlockPos blockpos = world.getHeight(this.chunkPos.add(x, 0, z));

                worldGenTree.generate(world, rand, blockpos);
            }
        }

        if(this.generateLakes)
        {
            if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_WATER))
            {
                for(int k5 = 0; k5 < 50; ++k5)
                {
                    int x = rand.nextInt(16) + 8;
                    int z = rand.nextInt(16) + 8;
                    int i17 = rand.nextInt(248) + 8;

                    if(i17 > 0)
                    {
                        int y = rand.nextInt(i17);
                        BlockPos pos = this.chunkPos.add(x, y, z);
                        (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(world, rand, pos);
                    }
                }
            }

            if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA))
            {
                for(int l5 = 0; l5 < 20; ++l5)
                {
                    int x = rand.nextInt(16) + 8;
                    int y = rand.nextInt(rand.nextInt(rand.nextInt(240) + 8) + 8);
                    int z = rand.nextInt(16) + 8;
                    BlockPos pos = this.chunkPos.add(x, y, z);
                    (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(world, rand, pos);
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, chunkPos));
    }
}
