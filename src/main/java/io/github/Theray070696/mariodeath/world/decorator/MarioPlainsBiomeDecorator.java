package io.github.Theray070696.mariodeath.world.decorator;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class MarioPlainsBiomeDecorator extends BiomeDecorator
{
    public MarioPlainsBiomeDecorator()
    {
        treesPerChunk = 5;
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
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        if(this.decorating)
        {
            throw new RuntimeException("Already decorating");
        } else
        {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(ModBlocks.blockGround.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelGen = new WorldGenMinable(ModBlocks.blockMarioBrickUnderground.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.graniteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.graniteSize);
            this.dioriteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.dioriteSize);
            this.andesiteGen = new WorldGenMinable(ModBlocks.blockGroundUnderground.getDefaultState(), this.chunkProviderSettings.andesiteSize);
            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUnderground));

            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUnderground));
            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUnderground));
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize, BlockMatcher
                    .forBlock(ModBlocks.blockGroundUnderground));
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize, BlockMatcher
                    .forBlock(ModBlocks.blockGroundUnderground));
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize, BlockMatcher.forBlock
                    (ModBlocks.blockGroundUnderground));
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }
}
