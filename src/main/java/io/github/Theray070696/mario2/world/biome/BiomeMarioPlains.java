package io.github.Theray070696.mario2.world.biome;

import io.github.Theray070696.mario2.world.decorator.BiomeDecoratorMarioPlains;
import net.minecraft.world.biome.BiomeDecorator;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class BiomeMarioPlains extends BiomeMario
{
    public BiomeMarioPlains()
    {
        super("biomemarioplains", new BiomeProperties("Mario Plains").setBaseBiome("Plains").setBaseHeight(0.125F).setHeightVariation(0.05F)
                .setTemperature(0.8F).setRainfall(0.4F));

        this.decorator.treesPerChunk = 0;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.clayPerChunk = 0;
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorMarioPlains());
    }

    @Override
    public short getWeight()
    {
        return 10;
    }
}
