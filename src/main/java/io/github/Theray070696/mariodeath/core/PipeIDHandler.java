package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.lib.BlockPosPair;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by Theray070696 on 4/27/2017.
 */
public class PipeIDHandler
{
    public static class PipeIDSaveHandler
    {
        @SubscribeEvent
        public void onWorldLoad(WorldEvent.Load event)
        {
            if(event.getWorld().isRemote)
            {
                reloadHandler(true);
            }
        }

        @SubscribeEvent
        public void onWorldSave(WorldEvent.Save event)
        {
            if(!event.getWorld().isRemote && instance(false) != null)
            {
                instance(false).save(false);
            }
        }
    }

    private static PipeIDHandler serverHandler;
    private static PipeIDHandler clientHandler;

    public final boolean client;
    private Map<Integer, BlockPosPair> idMap;
    private File saveDir;
    private File saveFile;
    private NBTTagCompound saveTag;
    private List<Integer> dirtyID;

    public PipeIDHandler(boolean client)
    {
        this.client = client;

        idMap = Collections.synchronizedMap(new HashMap<Integer, BlockPosPair>());
        dirtyID = Collections.synchronizedList(new LinkedList<Integer>());

        if(!client)
        {
            load();
        }
    }

    private void load()
    {
        saveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "mariodeath");

        try
        {
            if(!saveDir.exists())
            {
                saveDir.mkdirs();
            }

            saveFile = new File(saveDir, "pipeIDs.dat");

            if(saveFile.exists() && saveFile.length() > 0)
            {
                FileInputStream fin = new FileInputStream(saveFile);
                saveTag = CompressedStreamTools.readCompressed(fin);
                fin.close();
            } else
            {
                saveTag = new NBTTagCompound();
            }
        } catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void save(boolean force)
    {
        if(!dirtyID.isEmpty() || force)
        {
            for(int id : dirtyID)
            {
                BlockPosPair pair = idMap.get(id);

                if(pair == null)
                {
                    pair = new BlockPosPair(null, 0, null, 0);
                }

                BlockPos pos1 = pair.getPos1();
                BlockPos pos2 = pair.getPos2();

                if(pos1 == null)
                {
                    pos1 = new BlockPos(0, 0, 0);
                    pair = new BlockPosPair(pos1, 0, pos2, pair.getDim2());
                }

                if(pos2 == null)
                {
                    pos2 = new BlockPos(0, 0, 0);
                    pair = new BlockPosPair(pos1, pair.getDim1(), pos2, 0);
                }

                saveTag.setTag("coord" + id, new NBTTagIntArray(new int[] {pos1.getX(), pos1.getY(), pos1.getZ(), pair.getDim1(), pos2.getX(), pos2.getY(), pos2.getZ(), pair.getDim2()}));
            }

            dirtyID.clear();

            try
            {
                if(!saveFile.exists())
                {
                    saveFile.createNewFile();
                }

                DataOutputStream dout = new DataOutputStream(new FileOutputStream(saveFile));
                CompressedStreamTools.writeCompressed(saveTag, dout);
                dout.close();
            } catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static void reloadHandler(boolean client)
    {
        PipeIDHandler newHandler = new PipeIDHandler(client);

        if(client)
        {
            clientHandler = newHandler;
        } else
        {
            serverHandler = newHandler;
        }
    }

    public static PipeIDHandler instance(boolean client)
    {
        PipeIDHandler handler = client ? clientHandler : serverHandler;

        if(handler == null)
        {
            reloadHandler(client);
            handler = client ? clientHandler : serverHandler;
        }

        return handler;
    }

    public BlockPosPair getPosPair(int id)
    {
        BlockPosPair posPair = idMap.get(id);

        if(posPair == null)
        {
            if(!client && saveTag.hasKey("coord" + id))
            {
                int[] intArray = saveTag.getIntArray("coord" + id);

                posPair = new BlockPosPair(new BlockPos(intArray[0], intArray[1], intArray[2]), intArray[3], new BlockPos(intArray[4], intArray[5], intArray[6]), intArray[7]);
            }

            idMap.put(id, posPair);
        }

        return posPair;
    }

    public void requestSave(int id)
    {
        dirtyID.add(id);
    }

    public void setPosPair(int id, BlockPosPair posPair)
    {
        if(idMap.containsKey(id))
        {
            idMap.remove(id);
        }

        idMap.put(id, posPair);
        requestSave(id);
    }
}