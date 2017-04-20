package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.util.MarioTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray on 4/15/2016.
 */
public class ItemDebug extends ItemMario
{
    public ItemDebug()
    {
        super();

        this.setUnlocalizedName("itemDebug");
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote && itemStack != null && player != null && player instanceof EntityPlayerMP && !(player instanceof FakePlayer))
        {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;

            if(playerMP.dimension != ConfigHandler.marioDimensionID)
            {
                playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, ConfigHandler.marioDimensionID, new MarioTeleporter(playerMP.mcServer.worldServerForDimension(ConfigHandler.marioDimensionID)));
            } else if(playerMP.dimension != 0)
            {
                playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, 0, new MarioTeleporter(playerMP.mcServer.worldServerForDimension(0)));
            }
        }

        return itemStack;
    }
}