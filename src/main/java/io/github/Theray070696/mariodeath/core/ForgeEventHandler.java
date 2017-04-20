package io.github.Theray070696.mariodeath.core;

import com.google.common.collect.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.mariodeath.world.WorldGenMario;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.ChunkDataEvent;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * Created by Theray on 8/25/2015.
 */
@SuppressWarnings("unused")
public class ForgeEventHandler
{
    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event)
    {
        if(event.entityLiving != null && event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            String playerName = entityPlayer.getDisplayName();

            Random rand = new Random();

            if(playerName.equalsIgnoreCase("JasterMK3"))
            {
                // It's be Jaster screaming :P

                if(rand.nextInt(10) == 0)
                {
                    int sound = rand.nextInt(2) + 1;

                    entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "mariodeath:player.jasterDeath" + sound, 1.0f, 1.0f);
                    return;
                }
            } else if(playerName.equalsIgnoreCase("Theray070696"))
            {
                if(rand.nextInt(10) == 0)
                {
                    int sound = rand.nextInt(2) + 1;

                    entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "mariodeath:player.rayDeath" + sound, 1.0f, 1.0f);
                    return;
                }
            } else if(playerName.equalsIgnoreCase("Ghostkirby0319"))
            {
                if(rand.nextInt(10) == 0)
                {
                    int sound = /*rand.nextInt(2) +*/ 1;

                    entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "mariodeath:player.ghostDeath" + sound, 1.0f, 1.0f);
                    return;
                }
            }

            int sound = rand.nextInt(5) + 1;

            entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "mariodeath:player.death" + sound, 1.0f, 1.0f);
        }
    }

    private Map<World, ListMultimap<ChunkCoordIntPair, String>> pendingWork;
    private Map<World, ListMultimap<ChunkCoordIntPair, String>> completedWork;
    int maxPerTick = 15;

    public ForgeEventHandler()
    {
        if(ConfigHandler.retrogenEnabled)
        {
            FMLCommonHandler.instance().bus().register(new LastTick());
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event)
    {
        if(!ConfigHandler.retrogenEnabled)
        {
            return;
        }

        World world = event.world;
        if(!(world instanceof WorldServer))
        {
            return;
        }

        Chunk chunk = event.getChunk();
        Set<String> existingGens = Sets.newHashSet();
        NBTTagCompound data = event.getData();
        NBTTagCompound marker = data.getCompoundTag("marioDeathGen");
        NBTTagList tagList = marker.getTagList("list", 8);
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            existingGens.add(tagList.getStringTagAt(i));
        }

        queueRetrogen("questionMarkBlockGen", world, chunk.getChunkCoordIntPair());

        for(String retro : existingGens)
        {
            completeRetrogen(chunk.getChunkCoordIntPair(), world, retro);
        }
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event)
    {
        if(!ConfigHandler.retrogenEnabled)
        {
            return;
        }

        World world = event.world;
        if(!(world instanceof WorldServer))
        {
            return;
        }

        if(completedWork.containsKey(world))
        {
            ListMultimap<ChunkCoordIntPair, String> doneChunks = completedWork.get(world);
            List<String> list = doneChunks.get(event.getChunk().getChunkCoordIntPair());
            if(list.isEmpty())
            {
                return;
            }
            NBTTagCompound data = event.getData();
            NBTTagCompound retro = new NBTTagCompound();
            NBTTagList lst = new NBTTagList();
            retro.setTag("list", lst);
            data.setTag("marioDeathGen", retro);
            for(String retrogen : list)
            {
                lst.appendTag(new NBTTagString(retrogen));
            }
        }
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        this.pendingWork = new MapMaker().weakKeys().makeMap();
        this.completedWork = new MapMaker().weakKeys().makeMap();
    }

    public class LastTick
    {
        private int counter = 0;

        @SubscribeEvent
        public void tickStart(TickEvent.WorldTickEvent tick)
        {
            World world = tick.world;
            if(!(world instanceof WorldServer))
            {
                return;
            }
            if(tick.phase == TickEvent.Phase.START)
            {
                counter = 0;
            } else
            {
                ListMultimap<ChunkCoordIntPair, String> pending = pendingWork.get(world);
                if(pending == null)
                {
                    return;
                }
                ImmutableList<Entry<ChunkCoordIntPair, String>> forProcessing = ImmutableList.copyOf(Iterables.limit(pending.entries(), maxPerTick + 1));
                for(Entry<ChunkCoordIntPair, String> entry : forProcessing)
                {
                    if(counter++ > maxPerTick)
                    {
                        LogHelper.info("Completed " + counter + " retrogens this tick. There are " + pending.size() + " left for world " + world.getWorldInfo().getWorldName());
                        return;
                    }
                    runRetrogen((WorldServer) world, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void queueRetrogen(String retro, World world, ChunkCoordIntPair chunkCoords)
    {
        if(world instanceof WorldServer)
        {
            ListMultimap<ChunkCoordIntPair, String> currentWork = pendingWork.get(world);
            if(currentWork == null)
            {
                currentWork = ArrayListMultimap.create();
                pendingWork.put(world, currentWork);
            }

            currentWork.put(chunkCoords, retro);
        }
    }

    private void completeRetrogen(ChunkCoordIntPair chunkCoords, World world, String marker)
    {
        ListMultimap<ChunkCoordIntPair, String> pendingMap = pendingWork.get(world);
        if(pendingMap != null && pendingMap.containsKey(chunkCoords))
        {
            pendingMap.remove(chunkCoords, marker);
        }

        ListMultimap<ChunkCoordIntPair, String> completedMap = completedWork.get(world);
        if(completedMap == null)
        {
            completedMap = ArrayListMultimap.create();
            completedWork.put(world, completedMap);
        }

        completedMap.put(chunkCoords, marker);
    }

    private void runRetrogen(WorldServer world, ChunkCoordIntPair chunkCoords, String retro)
    {
        long worldSeed = world.getSeed();
        Random retroRandom = new Random(worldSeed);
        long xSeed = retroRandom.nextLong() >> 2 + 1L;
        long zSeed = retroRandom.nextLong() >> 2 + 1L;
        long chunkSeed = (xSeed * chunkCoords.chunkXPos + zSeed * chunkCoords.chunkZPos) ^ worldSeed;

        retroRandom.setSeed(chunkSeed);
        ChunkProviderServer providerServer = world.theChunkProviderServer;
        IChunkProvider generator = providerServer.currentChunkProvider;
        new WorldGenMario().generate(retroRandom, chunkCoords.chunkXPos, chunkCoords.chunkZPos, world, generator, providerServer);
        completeRetrogen(chunkCoords, world, retro);
    }
}