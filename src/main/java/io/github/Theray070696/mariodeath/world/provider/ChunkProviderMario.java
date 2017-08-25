package io.github.Theray070696.mariodeath.world.provider;

import com.google.common.collect.Sets;
import io.github.Theray070696.mariodeath.world.gen.ChunkGeneratorMario;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class ChunkProviderMario implements IChunkProvider
{
    public final IChunkGenerator chunkGenerator;
    /**
     * map of chunk Id's to Chunk instances
     */
    public final Long2ObjectMap<Chunk> id2ChunkMap = new Long2ObjectOpenHashMap(8192);
    private final World worldObj;
    private final Set<Long> droppedChunksSet = Sets.newHashSet();
    private Set<Long> loadingChunks = Sets.newHashSet();

    public ChunkProviderMario(World world)
    {
        worldObj = world;
        chunkGenerator = new ChunkGeneratorMario(world, this.worldObj.getSeed(), true, this.worldObj.getWorldInfo().getGeneratorOptions());
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z)
    {
        long i = ChunkPos.asLong(x, z);
        Chunk chunk = this.id2ChunkMap.get(i);

        if(chunk != null)
        {
            chunk.unloaded = false;
        }

        return chunk;
    }

    @Override
    public Chunk provideChunk(int x, int z)
    {
        Chunk chunk = this.loadChunk(x, z);

        if(chunk == null)
        {
            long i = ChunkPos.asLong(x, z);

            try
            {
                chunk = chunkGenerator.provideChunk(x, z);
            } catch(Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception generating new chunk");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Chunk to be generated");
                crashreportcategory.addCrashSection("Location", String.format("%d,%d", x, z));
                crashreportcategory.addCrashSection("Position hash", i);
                crashreportcategory.addCrashSection("Generator", chunkGenerator);
                throw new ReportedException(crashreport);
            }

            id2ChunkMap.put(i, chunk);
            chunk.onChunkLoad();
        }

        return chunk;
    }

    @Override
    public boolean unloadQueuedChunks()
    {
        if(!droppedChunksSet.isEmpty())
        {
            for(ChunkPos forced : worldObj.getPersistentChunks().keySet())
            {
                droppedChunksSet.remove(ChunkPos.asLong(forced.chunkXPos, forced.chunkZPos));
            }

            Iterator<Long> iterator = droppedChunksSet.iterator();

            for(int i = 0; i < 100 && iterator.hasNext(); iterator.remove())
            {
                Long oLong = iterator.next();
                Chunk chunk = id2ChunkMap.get(oLong);

                if(chunk != null && chunk.unloaded)
                {
                    chunk.onChunkUnload();
                    id2ChunkMap.remove(oLong);
                    ++i;
                    ForgeChunkManager.putDormantChunk(ChunkPos.asLong(chunk.xPosition, chunk.zPosition), chunk);
                    if(id2ChunkMap.size() == 0 && ForgeChunkManager.getPersistentChunksFor(worldObj).size() == 0 && !worldObj.provider
                            .getDimensionType().shouldLoadSpawn())
                    {
                        DimensionManager.unloadWorld(worldObj.provider.getDimension());
                        break;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String makeString()
    {
        return "ServerChunkCache: " + id2ChunkMap.size() + " Drop: " + droppedChunksSet.size();
    }

    @Nullable
    public Chunk loadChunk(int x, int z)
    {
        return loadChunk(x, z, null);
    }

    @Nullable
    public Chunk loadChunk(int x, int z, Runnable runnable)
    {
        Chunk chunk = getLoadedChunk(x, z);
        if(chunk == null)
        {
            long pos = ChunkPos.asLong(x, z);
            chunk = ForgeChunkManager.fetchDormantChunk(pos, worldObj);
            if(chunk != null)
            {
                if(!loadingChunks.add(pos))
                {
                    FMLLog.bigWarning("There is an attempt to load a chunk (%d,%d) in dimension %d that is already being loaded. This will cause " +
                            "weird chunk breakages.", x, z, worldObj.provider.getDimension());
                }

                id2ChunkMap.put(ChunkPos.asLong(x, z), chunk);
                chunk.onChunkLoad();
                chunk.populateChunk(this, chunkGenerator);

                loadingChunks.remove(pos);
            }
        }

        // If we didn't load the chunk async and have a callback run it now
        if(runnable != null)
        {
            runnable.run();
        }
        return chunk;
    }
}
