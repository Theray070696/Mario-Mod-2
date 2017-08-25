package io.github.Theray070696.mariodeath.world.provider;

import io.github.Theray070696.mariodeath.world.ModDimension;
import io.github.Theray070696.mariodeath.world.biome.BiomeProviderMario;
import io.github.Theray070696.mariodeath.world.gen.ChunkGeneratorMario;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class WorldProviderMario extends WorldProvider
{
    @Override
    public DimensionType getDimensionType()
    {
        return DimensionType.getById(ModDimension.DIMENTION_MARIO_ID);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorMario(worldObj, getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), worldObj.getWorldInfo()
                .getGeneratorOptions());
    }

    @Override
    public void createBiomeProvider()
    {
        this.setDimension(ModDimension.DIMENTION_MARIO_ID);
        this.biomeProvider = new BiomeProviderMario(worldObj.getWorldInfo());
    }
}
