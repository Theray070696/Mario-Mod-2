package io.github.Theray070696.mariodeath.world;

import io.github.Theray070696.mariodeath.world.provider.ChunkProviderMario;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class WorldMario extends World
{
    protected WorldMario(ISaveHandler saveHandler, WorldInfo info, WorldProvider provider, Profiler profiler, boolean client)
    {
        super(saveHandler, info, provider, profiler, client);
    }

    @Override
    protected IChunkProvider createChunkProvider()
    {
        return new ChunkProviderMario(this);
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty)
    {
        return false;
    }
}
