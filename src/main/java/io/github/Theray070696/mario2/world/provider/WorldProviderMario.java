package io.github.Theray070696.mario2.world.provider;

import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.world.biome.BiomeProviderMario;
import io.github.Theray070696.mario2.world.gen.ChunkGeneratorMario;
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
        return DimensionType.getById(ConfigHandler.marioDimensionID);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorMario(this.worldObj, getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getWorldInfo()
                .getGeneratorOptions());
    }

    @Override
    public void createBiomeProvider()
    {
        this.setDimension(ConfigHandler.marioDimensionID);
        this.biomeProvider = new BiomeProviderMario(this.worldObj.getWorldInfo());
    }
}
