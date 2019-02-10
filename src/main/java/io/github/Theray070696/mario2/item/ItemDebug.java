package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.util.MarioTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class ItemDebug extends ItemMario
{
    public ItemDebug()
    {
        super();

        this.setTranslationKey("itemDebug");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!ConfigHandler.developerModeEnabled)
        {
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer))
        {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;

            if(playerMP.dimension != ConfigHandler.marioDimensionID)
            {
                playerMP.server.getPlayerList().transferPlayerToDimension(playerMP, ConfigHandler.marioDimensionID, new MarioTeleporter(playerMP
                        .server.getWorld(ConfigHandler.marioDimensionID)));
            } else if(playerMP.dimension != 0)
            {
                playerMP.server.getPlayerList().transferPlayerToDimension(playerMP, 0, new MarioTeleporter(playerMP.server.getWorld(0)));
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
