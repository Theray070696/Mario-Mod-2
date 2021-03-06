package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Theray070696 on 1/7/2017.
 */
public class ItemCoinCurrency extends ItemMario
{
    public ItemCoinCurrency()
    {
        super();

        this.setHasSubtypes(true);
        this.setTranslationKey("itemCoinCurrency");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList list)
    {
        if(this.isInCreativeTab(tab))
        {
            for(int i = 0; i < 5; i++)
            {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return "item." + ModInfo.MOD_ID.toLowerCase() + ":" + CoinType.getTypeNameByMeta(stack.getMetadata());
    }

    @Override
    public void registerItemModel(Item item)
    {
        RayCore.proxy.registerItemRenderer(this, 0, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_0");
        RayCore.proxy.registerItemRenderer(this, 1, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_1");
        RayCore.proxy.registerItemRenderer(this, 2, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_2");
        RayCore.proxy.registerItemRenderer(this, 3, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_3");
        RayCore.proxy.registerItemRenderer(this, 4, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_4");
    }

    private enum CoinType
    {
        COIN_GREEN(0, "itemCoinGreen"),
        COIN_BLUE(1, "itemCoinBlue"),
        COIN_RED(2, "itemCoinRed"),
        COIN_PURPLE(3, "itemCoinPurple"),
        COIN_BLACK(4, "itemCoinBlack");

        private final int meta;
        private final String typeName;

        CoinType(int meta, String name)
        {
            this.meta = meta;
            typeName = name;
        }

        public static String getTypeNameByMeta(int meta)
        {
            for(CoinType type : CoinType.values())
            {
                if(type.meta == meta)
                {
                    return type.typeName;
                }
            }

            return "errorname";
        }
    }

    @Override
    public int getMaxMetadata()
    {
        return 5;
    }
}