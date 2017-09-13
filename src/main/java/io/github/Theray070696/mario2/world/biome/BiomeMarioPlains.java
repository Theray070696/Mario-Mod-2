package io.github.Theray070696.mario2.world.biome;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.world.decorator.MarioPlainsBiomeDecorator;
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
        this.topBlock = ModBlocks.blockGroundSMW.getDefaultState();
        this.fillerBlock = ModBlocks.blockGroundSMW.getDefaultState();
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new MarioPlainsBiomeDecorator());
    }

    @Override
    public short getWeight()
    {
        return 10;
    }
}
