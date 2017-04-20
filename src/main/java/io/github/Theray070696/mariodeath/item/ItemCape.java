package io.github.Theray070696.mariodeath.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray on 9/15/2015.
 */
public class ItemCape extends ItemMario implements IBauble
{
    public ItemCape()
    {
        super();

        this.setUnlocalizedName("itemCape");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
    {
        if(!world.isRemote)
        {
            if(entity != null && entity instanceof EntityPlayer && !(entity instanceof FakePlayer))
            {
                EntityPlayer player = (EntityPlayer) entity;

                for(int i = 0; i < 9; i++)
                {
                    ItemStack itemStack = player.inventory.mainInventory[i];

                    if(itemStack != null && itemStack.isItemEqual(stack))
                    {
                        player.fallDistance = 0.0F;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            player.fallDistance = 0.0F;

            world.playSoundAtEntity(player, "mariodeath:item.cape", 1.0F, 1.0F);
        }

        return itemStack;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player)
    {
        if(itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            player.fallDistance = 0.0F;
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
}