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
 * Created by Theray on 1/7/2017.
 */
public class ItemCoinCurrency extends ItemMario
{
    public ItemCoinCurrency()
    {
        super();

        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        icons = new IIcon[5];

        icons[0] = reg.registerIcon(ModInfo.MOD_ID + ":itemCoinGreen"); // Green, blue, red, purple coin, black coin.
        icons[1] = reg.registerIcon(ModInfo.MOD_ID + ":itemCoinBlue");
        icons[2] = reg.registerIcon(ModInfo.MOD_ID + ":itemCoinRed");
        icons[3] = reg.registerIcon(ModInfo.MOD_ID + ":itemCoinPurple");
        icons[4] = reg.registerIcon(ModInfo.MOD_ID + ":itemCoinBlack");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        if(meta > icons.length)
        {
            meta = 0;
        }

        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < icons.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int damage = stack.getItemDamage();

        if(damage == 0)
        {
            return String.format("item.%s", ModInfo.MOD_ID.toLowerCase() + ":itemCoinGreen");
        } else if(damage == 1)
        {
            return String.format("item.%s", ModInfo.MOD_ID.toLowerCase() + ":itemCoinBlue");
        } else if(damage == 2)
        {
            return String.format("item.%s", ModInfo.MOD_ID.toLowerCase() + ":itemCoinRed");
        } else if(damage == 3)
        {
            return String.format("item.%s", ModInfo.MOD_ID.toLowerCase() + ":itemCoinPurple");
        } else if(damage == 4)
        {
            return String.format("item.%s", ModInfo.MOD_ID.toLowerCase() + ":itemCoinBlack");
        }

        return "item.mariodeath:errorname";
    }
}