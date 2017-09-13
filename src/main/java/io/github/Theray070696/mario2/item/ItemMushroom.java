package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 9/6/2015.
 */
public class ItemMushroom extends ItemMario
{
    public ItemMushroom()
    {
        super();

        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemMushroom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList list)
    {
        for(int i = 0; i < 3; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer)) // If we're on the server side, the ItemStack is not null,
            // the player is not null, AND this was not used by a fake player...
        {
            if(player.getHealth() < player.getMaxHealth()) // If the player needs healing...
            {
                if(!world.getWorldInfo().isHardcoreModeEnabled()) // If we are not in hardcore mode...
                {
                    if(player.getHealth() + 5F >= player.getMaxHealth()) // If the mushroom would give the player more than their max health...
                    {
                        player.setHealth(player.getMaxHealth()); // Set their health to their max health.
                    } else // Otherwise...
                    {
                        player.setHealth(player.getHealth() + 5F); // Set their health to their current health plus 5.
                    }
                } else // Otherwise...
                {
                    if(player.getHealth() + 10F >= player.getMaxHealth()) // If the mushroom would give the player more than their max health...
                    {
                        player.setHealth(player.getMaxHealth()); // Set their health to their max health.
                    } else // Otherwise...
                    {
                        player.setHealth(player.getHealth() + 10F); // Set their health to their current health plus 10.
                    }
                }

                int meta = itemStack.getItemDamage(); // Get the damage value of the item.
                if(meta == 0 || meta == 2) // If it was from Mario 1 or Mario 3...
                {
                    world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler
                            .mushroomSMB, SoundCategory.PLAYERS, 1.0F, 1.0F); // Play this sound.
                } else if(meta == 1) // If it was from Mario World...
                {
                    world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler
                            .mushroom, SoundCategory.PLAYERS, 1.0F, 1.0F); // Play this sound.
                }

                itemStack.setCount(itemStack.getCount() - 1); // Decrease amount of items in stack by one.
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void registerItemModel(Item item)
    {
        RayCore.proxy.registerItemRenderer(this, 0, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_0");
        RayCore.proxy.registerItemRenderer(this, 1, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_1");
        RayCore.proxy.registerItemRenderer(this, 2, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_2");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(itemStack, player, tooltip, advanced);

        if(player == null)
        {
            return;
        }

        if(!player.getWorldInfo().isHardcoreModeEnabled())
        {
            tooltip.add("Restores 2.5 hearts"); // Add a helpful tooltip.
        } else
        {
            tooltip.add("Restores 5 hearts"); // Add a helpful tooltip.
        }
    }

    @Override
    public int getMaxMetadata()
    {
        return 3;
    }
}