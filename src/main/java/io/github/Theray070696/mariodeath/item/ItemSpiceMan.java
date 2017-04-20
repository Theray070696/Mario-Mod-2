package io.github.Theray070696.mariodeath.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray on 12/19/2016.
 */
public class ItemSpiceMan extends ItemMario
{
    public ItemSpiceMan()
    {
        super();

        this.setUnlocalizedName("itemSpiceMan");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(itemStack != null && world != null && player != null && !(player instanceof FakePlayer))
        {
            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 1120, 10));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 1120, 5));
            world.playSoundAtEntity(player, "mariodeath:item.spiceman", 1.0F, 1.0F);
            itemStack.stackSize--;
        }

        return itemStack;
    }
}
