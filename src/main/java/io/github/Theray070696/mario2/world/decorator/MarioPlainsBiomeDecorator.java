package io.github.Theray070696.mario2.world.decorator;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.world.gen.WorldGenMarioTree;
import io.github.Theray070696.mario2.world.provider.WorldProviderMario;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class MarioPlainsBiomeDecorator extends BiomeDecorator
{
    public MarioPlainsBiomeDecorator()
    {
        this.treesPerChunk = 0;
        this.flowersPerChunk = 0;
        this.grassPerChunk = 0;
        this.deadBushPerChunk = 0;
        this.mushroomsPerChunk = 0;
        this.reedsPerChunk = 0;
        this.cactiPerChunk = 0;
        this.sandPatchesPerChunk = 0;
        this.gravelPatchesPerChunk = 0;
        this.clayPerChunk = 0;
        this.bigMushroomsPerChunk = 0;
    }

    @Override
    public void decorate(World world, Random rand, Biome biome, BlockPos pos)
    {
        if(this.decorating)
        {
            throw new RuntimeException("Already decorating");
        } else
        {
            this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(ModBlocks.marioBlockGround.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.graniteGen = new WorldGenMinable(ModBlocks.marioBlockGroundUnderground.getDefaultState(), this.chunkProviderSettings.graniteSize);
            this.dioriteGen = new WorldGenMinable(ModBlocks.marioBlockGroundUnderground.getDefaultState(), this.chunkProviderSettings.dioriteSize);
            this.andesiteGen = new WorldGenMinable(ModBlocks.marioBlockGroundUnderground.getDefaultState(), this.chunkProviderSettings.andesiteSize);

            if(world.provider instanceof WorldProviderMario)
            {
                // Mario dimension.
                this.coalGen = new WorldGenMinable(ModBlocks.marioBlockCoalOreSMW.getDefaultState(), this.chunkProviderSettings.coalSize, BlockMatcher
                        .forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
                this.ironGen = new WorldGenMinable(ModBlocks.marioBlockIronOreSMW.getDefaultState(), this.chunkProviderSettings.ironSize, BlockMatcher
                        .forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
                this.goldGen = new WorldGenMinable(ModBlocks.marioBlockGoldOreSMW.getDefaultState(), this.chunkProviderSettings.goldSize, BlockMatcher
                        .forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
                this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize, BlockMatcher
                        .forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
                this.diamondGen = new WorldGenMinable(ModBlocks.marioBlockDiamondOreSMW.getDefaultState(), this.chunkProviderSettings.diamondSize,
                        BlockMatcher.forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
                this.lapisGen = new WorldGenMinable(ModBlocks.marioBlockLapisOreSMW.getDefaultState(), this.chunkProviderSettings.lapisSize,
                        BlockMatcher.forBlock(ModBlocks.marioBlockGroundUndergroundSMW));
            } else
            {
                this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize, BlockMatcher.forBlock
                        (Blocks.STONE));
                this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize, BlockMatcher.forBlock
                        (Blocks.STONE));
                this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize, BlockMatcher.forBlock
                        (Blocks.STONE));
                this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize, BlockMatcher
                        .forBlock(Blocks.STONE));
                this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize, BlockMatcher
                        .forBlock(Blocks.STONE));
                this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize, BlockMatcher.forBlock
                        (Blocks.STONE));
            }

            this.genDecorations(biome, world, rand);
            this.decorating = false;
        }
    }

    protected void genDecorations(Biome biome, World world, Random rand)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, this.chunkPos));
        this.generateOres(world, rand);

        if(TerrainGen.decorate(world, rand, this.chunkPos, DecorateBiomeEvent.Decorate.EventType.SAND))
        {
            for(int i = 0; i < this.sandPatchesPerChunk; ++i)
            {
                int j = rand.nextInt(16) + 8;
                int k = rand.nextInt(16) + 8;
                this.sandGen.generate(world, rand, world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }
        }

        if(TerrainGen.decorate(world, rand, this.chunkPos, DecorateBiomeEvent.Decorate.EventType.CLAY))
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
            for(int j1 = 0; j1 < this.gravelPatchesPerChunk; ++j1)
            {
                int i2 = rand.nextInt(16) + 8;
                int j6 = rand.nextInt(16) + 8;
                this.gravelGen.generate(world, rand, world.getTopSolidOrLiquidBlock(this.chunkPos.add(i2, 0, j6)));
            }
        }

        int treeChance = this.treesPerChunk;

        if(rand.nextFloat() < this.extraTreeChance)
        {
            ++treeChance;
        }

        if(TerrainGen.decorate(world, rand, this.chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
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

        if(this.generateFalls)
        {
            if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_WATER))
            {
                for(int k5 = 0; k5 < 50; ++k5)
                {
                    int i10 = rand.nextInt(16) + 8;
                    int l13 = rand.nextInt(16) + 8;
                    int i17 = rand.nextInt(248) + 8;

                    if(i17 > 0)
                    {
                        int k19 = rand.nextInt(i17);
                        BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
                        (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(world, rand, blockpos6);
                    }
                }
            }

            if(TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA))
            {
                for(int l5 = 0; l5 < 20; ++l5)
                {
                    int j10 = rand.nextInt(16) + 8;
                    int i14 = rand.nextInt(16) + 8;
                    int j17 = rand.nextInt(rand.nextInt(rand.nextInt(240) + 8) + 8);
                    BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
                    (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(world, rand, blockpos3);
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, this.chunkPos));
    }
}
