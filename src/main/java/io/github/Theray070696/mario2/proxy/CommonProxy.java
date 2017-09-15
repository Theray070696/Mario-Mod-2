package io.github.Theray070696.mario2.proxy;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.entity.EntityFireball;
import io.github.Theray070696.mario2.entity.EntityGoomba;
import io.github.Theray070696.mario2.entity.EntityKoopa;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.world.ModBiomes;
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
    public void construct(FMLPreInitializationEvent event) throws Exception
    {

    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        EntityRegistry.registerModEntity(EntityGoomba.class, ModInfo.MOD_ID + ":goomba", 0, MarioMod2.INSTANCE, 128, 1, false);
        EntityRegistry.registerModEntity(EntityKoopa.class, ModInfo.MOD_ID + ":koopa", 1, MarioMod2.INSTANCE, 128, 1, false);
        EntityRegistry.registerModEntity(EntityFireball.class, ModInfo.MOD_ID + ":fireball", 2, MarioMod2.INSTANCE, 64, 1, true);

        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>();
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.ICY));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.WARM));
        List<Biome> biomes = new ArrayList<>();
        for(BiomeManager.BiomeEntry b : biomeEntries)
        {
            biomes.add(b.biome);
        }
        biomes.addAll(BiomeManager.oceanBiomes);

        biomes.remove(ModBiomes.biomeMarioPlains);
        biomes.remove(ModBiomes.biomeMarioForest);
        biomes.remove(ModBiomes.biomeMarioForestHills);

        EntityRegistry.addSpawn(EntityGoomba.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
        EntityRegistry.addSpawn(EntityKoopa.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
    }
}