package io.github.Theray070696.mario2.world.biome;

import io.github.Theray070696.mario2.world.decorator.BiomeDecoratorForestOfIllusion;
import net.minecraft.world.biome.BiomeDecorator;

/**
 * Created by Theray070696 on 2/21/2019.
 */
public class BiomeForestOfIllusion extends BiomeMario
{
    public BiomeForestOfIllusion()
    {
        super("biomeforestofillusion", new BiomeProperties("Forest of Illusion").setBaseBiome("Forest").setBaseHeight(0.125F).setHeightVariation
                (0.05F).setTemperature(0.7F).setRainfall(0.8F));

        this.decorator.treesPerChunk = 20;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.clayPerChunk = 0;
    }

    @Override
    public short getWeight()
    {
        return 10;
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorForestOfIllusion());
    }
}
