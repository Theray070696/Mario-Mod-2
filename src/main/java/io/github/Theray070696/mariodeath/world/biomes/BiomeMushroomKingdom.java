package io.github.Theray070696.mariodeath.world.biomes;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.world.biomes.decorators.BiomeDecoratorMod;

/**
 * Created by Theray on 4/15/2016.
 */
public class BiomeMushroomKingdom extends ModBiomes
{

    protected BiomeDecoratorMod decorator;

    public BiomeMushroomKingdom(int biomeId)
    {
        super(biomeId);
        this.topBlock = ModBlocks.blockGround;
        this.fillerBlock = ModBlocks.blockMarioBrick;
    }
}