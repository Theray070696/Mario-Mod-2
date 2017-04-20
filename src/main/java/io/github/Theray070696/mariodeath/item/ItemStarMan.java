package io.github.Theray070696.mariodeath.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray on 9/15/2015.
 */
public class ItemStarMan extends ItemMario
{
    public ItemStarMan()
    {
        super();

        this.setUnlocalizedName("itemStarMan");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(itemStack != null && world != null && player != null && !(player instanceof FakePlayer))
        {
            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 258, 10));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 258, 3));

            if(player.getDisplayName().equalsIgnoreCase("JasterMK3"))
            {
                world.playSoundAtEntity(player, "mariodeath:item.jasterStarman", 1.0f, 1.0f);
            }

            world.playSoundAtEntity(player, "mariodeath:item.starman", 1.0F, 1.0F);

            itemStack.stackSize--;
        }

        return itemStack;
    }
}