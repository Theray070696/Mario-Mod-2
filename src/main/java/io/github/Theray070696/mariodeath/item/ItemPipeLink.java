package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.block.BlockPipe;
import io.github.Theray070696.mariodeath.block.tile.TilePipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 5/2/2017.
 */
public class ItemPipeLink extends ItemMario
{
    public ItemPipeLink()
    {
        super();

        this.setUnlocalizedName("itemPipeLink");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // First check a few things.
        // Is this the server?
        // Is the ItemStack not null?
        // Is the Player not null?
        // Is the Player a real player?
        // Is the Block we're right clicking on a Pipe?
        // Is it a Multiblock?
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer) && world.getBlockState(pos).getBlock() instanceof BlockPipe && world.getBlockState(pos).getValue(BlockPipe.ISMULTIBLOCK))
        {
            // Get Pipe TileEntity
            TilePipe tilePipe = (TilePipe) world.getTileEntity(pos);

            // Are we not linking to a Pipe?
            if(!itemStack.getTagCompound().getBoolean("linking"))
            {
                // Set the linking flag to true
                itemStack.getTagCompound().setBoolean("linking", true);

                // Save the position and dimension of the Pipe
                itemStack.getTagCompound().setIntArray("pipePos", new int[] {pos.getX(), pos.getY(), pos.getZ(), world.provider.getDimension()});

                // Inform the Player that linking has started
                player.addChatComponentMessage(new TextComponentString("Link started."));
            } else // We are linking to a Pipe.
            {
                // Save the data to this int array for later use
                int[] posDim = itemStack.getTagCompound().getIntArray("pipePos");

                // Get the World the other Pipe is in
                World otherPipeWorld = world.getMinecraftServer().worldServerForDimension(posDim[3]);

                // Make sure the original Pipe is still valid
                if(otherPipeWorld.getTileEntity(new BlockPos(posDim[0], posDim[1], posDim[2])) != null && otherPipeWorld.getTileEntity(new BlockPos(posDim[0], posDim[1], posDim[2])) instanceof TilePipe)
                {
                    // Make sure the Player is not trying to link a Pipe to itself
                    if(!(new BlockPos(posDim[0], posDim[1], posDim[2]).equals(pos)))
                    {
                        return EnumActionResult.PASS;
                    }

                    // Set data for the Pipe the Player right clicked on
                    tilePipe.setOtherPipePos(posDim[0], posDim[1], posDim[2], posDim[3]);

                    // Set data for the original Pipe
                    ((TilePipe) otherPipeWorld.getTileEntity(new BlockPos(posDim[0], posDim[1], posDim[2]))).setOtherPipePos(pos, world.provider.getDimension());

                    // Clear linking flag
                    itemStack.getTagCompound().setBoolean("linking", false);

                    // Reset pipePos tag
                    itemStack.getTagCompound().setIntArray("pipePos", new int[] {0, 0, 0, 0});

                    // Inform the Player that the linking is complete
                    player.addChatComponentMessage(new TextComponentString("Pipes linked!"));
                } else // INVALID PIPE
                {
                    // Clear linking flag
                    itemStack.getTagCompound().setBoolean("linking", false);

                    // Reset pipePos tag
                    itemStack.getTagCompound().setIntArray("pipePos", new int[] {0, 0, 0, 0});

                    // Inform the Player that something went wrong
                    player.addChatComponentMessage(new TextComponentString("The Pipe at X: " + posDim[0] + " Y: " + posDim[1] + " Z: " + posDim[2] + ", in dimension " + posDim[3] + " could not be found!"));
                }
            }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(!itemStack.hasTagCompound()) // This should only run once
        {
            itemStack.setTagCompound(new NBTTagCompound());
            itemStack.getTagCompound().setBoolean("linking", false);
            itemStack.getTagCompound().setIntArray("pipePos", new int[] {0, 0, 0, 0});
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced)
    {
        // I'm not sure if this is needed, but better to be safe than sorry
        super.addInformation(itemStack, player, list, advanced);

        // Game crashes without the hasTagCompound check
        if(itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("linking"))
        {
            int[] posDim = itemStack.getTagCompound().getIntArray("pipePos");

            list.add("Linked to Pipe at:");
            list.add("X: " + posDim[0]);
            list.add("Y: " + posDim[1]);
            list.add("Z: " + posDim[2]);
            list.add("Dimension: " + posDim[3]);
        }
    }
}
