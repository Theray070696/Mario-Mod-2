package io.github.Theray070696.mariodeath.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.util.LogHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;

/**
 * Created by Theray on 9/6/2015.
 */
public class ItemMushroom extends ItemMario
{
    public ItemMushroom()
    {
        super();

        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemMushroom");
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        icons = new IIcon[2];

        icons[0] = reg.registerIcon(ModInfo.MOD_ID + ":itemSMB1Mushroom");
        icons[1] = reg.registerIcon(ModInfo.MOD_ID + ":itemSMWMushroom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        if(meta > 1)
        {
            meta = 0;
        }

        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < 2; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            if(player.getHealth() < player.getMaxHealth())
            {
                if(player.getHealth() + 5F >= player.getMaxHealth())
                {
                    player.setHealth(player.getMaxHealth());
                } else
                {
                    player.setHealth(player.getHealth() + 5F);
                }

                int meta = itemStack.getItemDamage();
                if(meta == 0)
                {
                    world.playSoundAtEntity(player, "mariodeath:item.mushroom", 1.0F, 1.0F);
                } else if(meta == 1)
                {
                    world.playSoundAtEntity(player, "mariodeath:item.smwMushroom", 1.0F, 1.0F);
                } else if(meta == 2)
                {
                    world.playSoundAtEntity(player, "mariodeath:item.smb3Mushroom", 1.0F, 1.0F);
                }

                itemStack.stackSize--;
            }
        }

        return itemStack;
    }
}