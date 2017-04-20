package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.block.tile.TilePipe;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.raycore.block.BlockRayContainer;
import io.github.Theray070696.raycore.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Theray on 2/9/2017.
 */
public class BlockPipeEntrance extends BlockRayContainer
{
    public BlockPipeEntrance()
    {
        super(ModInfo.MOD_ID);
        
        this.setBlockName("marioBlockPipeEntrance");
    }
    
    public static boolean isMultiBlock(World world, int x, int y, int z)
    {
        if(world.getBlock(x, y, z) instanceof BlockPipeEntrance && world.getBlock(x, y + 1, z) instanceof BlockPipeEntrance && world.getBlock(x + 1, y, z) instanceof BlockPipeEntrance && world.getBlock(x + 1, y + 1, z) instanceof BlockPipeEntrance
                && world.getBlock(x, y, z - 1) instanceof BlockPipeBase && world.getBlock(x + 1, y, z - 1) instanceof BlockPipeBase && world.getBlock(x, y + 1, z - 1) instanceof BlockPipeBase && world.getBlock(x + 1, y + 1, z - 1) instanceof BlockPipeBase)
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    // onBlockActivated
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        if(!world.isRemote)
        {
            if(isMultiBlock(world, x, y, z))
            {
                LogHelper.info("Multiblock status: is a multiblock");
                return true;
            } else
            {
                LogHelper.info("Multiblock status: is NOT a multiblock");
                return false;
            }
        }
        
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TilePipe();
    }
}
