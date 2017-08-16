package io.github.Theray070696.mariodeath.proxy;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.entity.EntityGoomba;
import io.github.Theray070696.mariodeath.entity.EntityKoopa;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 8/25/15.
 */
public abstract class CommonProxy implements IProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        EntityRegistry.registerModEntity(EntityGoomba.class, ModInfo.MOD_ID + ":goomba", 0, MarioDeath.INSTANCE, 64, 1, true);
        EntityRegistry.registerModEntity(EntityKoopa.class, ModInfo.MOD_ID + ":koopa", 1, MarioDeath.INSTANCE, 64, 1, true);

        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<BiomeManager.BiomeEntry>();
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.ICY));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.WARM));
        List<Biome> biomes = new ArrayList<Biome>();
        for(BiomeManager.BiomeEntry b : biomeEntries)
        {
            biomes.add(b.biome);
        }
        biomes.addAll(BiomeManager.oceanBiomes);

        EntityRegistry.addSpawn(EntityGoomba.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
        EntityRegistry.addSpawn(EntityKoopa.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
    }

    @Override
    public void init(FMLInitializationEvent event)
    {

    }
}