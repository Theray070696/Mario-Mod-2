package io.github.Theray070696.mariodeath.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by Theray on 4/15/2016.
 */
public class WorldProviderMario extends WorldProvider
{
    @Override
    /** tells Minecraft to use our new Terrain Generator */
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderMario(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    /** tells Minecraft to use our new WorldChunkManager **/
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerMario(worldObj.getSeed(), terrainType);
        this.dimensionId = ConfigHandler.marioDimensionID;
    }

    /** Get Provider for Dimension **/
    public static WorldProvider getProviderForDimension(int id)
    {
        return DimensionManager.createProviderFor(ConfigHandler.marioDimensionID);
    }

    @Override
    /**
     * @return the name of the dimension
     */
    public String getDimensionName()
    {
        return "Marios World";
    }

    @Override
    /** sets/creates the save folder */
    public String getSaveFolder()
    {
        return "DIM" + ConfigHandler.marioDimensionID;
    }

    @SideOnly(Side.CLIENT)
    /** should stars be rendered? */
    public boolean renderStars()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    /** should clouds be rendered? */
    public boolean renderClouds()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean renderVoidFog()
    {
        return false;
    }

    @Override
    /** is this a surface world or an underworld */
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** @return the dimension join message */
    public String getWelcomeMessage()
    {
        return "Entering Marios World";
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** @return the dimension leave message */
    public String getDepartMessage()
    {
        return "Leaving Marios World";
    }
}