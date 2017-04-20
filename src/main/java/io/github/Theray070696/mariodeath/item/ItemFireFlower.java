package io.github.Theray070696.mariodeath.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray on 9/15/2015.
 */
public class ItemFireFlower extends ItemMario
{
    public ItemFireFlower()
    {
        super();

        this.setUnlocalizedName("itemFireFlower");
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            // Shoot a fireball
            Vec3 playerLook = player.getLookVec();

            EntitySmallFireball fireball = new EntitySmallFireball(world, player, 1, 1, 1);
            fireball.setPosition(player.posX + playerLook.xCoord, player.posY + 1 + playerLook.yCoord, player.posZ + playerLook.zCoord);

            fireball.accelerationX = playerLook.xCoord * 0.1;
            fireball.accelerationY = playerLook.yCoord * 0.1;
            fireball.accelerationZ = playerLook.zCoord * 0.1;

            world.spawnEntityInWorld(fireball);

            world.playSoundAtEntity(player, "mariodeath:item.fireball", 1.0F, 1.0F);

            itemStack.damageItem(1, player);
        }

        return itemStack;
    }
}