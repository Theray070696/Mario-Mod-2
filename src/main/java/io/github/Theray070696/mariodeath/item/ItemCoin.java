package io.github.Theray070696.mariodeath.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by Theray on 8/27/2015.
 */
public class ItemCoin extends ItemMario
{
    public ItemCoin()
    {
        super();

        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemCoin");
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        icons = new IIcon[2];

        icons[0] = reg.registerIcon(ModInfo.MOD_ID + ":itemSMB1Coin");
        icons[1] = reg.registerIcon(ModInfo.MOD_ID + ":itemSMWCoin");
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
}