package io.github.Theray070696.mariodeath.item;

import cpw.mods.fml.common.registry.GameRegistry;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.raycore.item.ItemRay;
import io.github.Theray070696.raycore.item.ItemRayRecord;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray on 8/27/2015.
 */
public class ModItems
{
    public static final ItemRay itemMarioCoin = new ItemCoin();
    public static final ItemRay itemMarioMushroom = new ItemMushroom();
    public static final ItemRay item1Up = new Item1Up();
    public static final ItemRay itemCape = new ItemCape();
    public static final ItemRay itemFireFlower = new ItemFireFlower();
    public static final ItemRay itemStarMan = new ItemStarMan();
    public static final ItemRay itemSpiceMan = new ItemSpiceMan();

    public static final Item itemRecordSuperSpiceBros = new ItemRayRecord(ModInfo.MOD_ID, "spiceman", 1121).setUnlocalizedName("records.spiceman");
    public static final Item itemRecordSMBUnderwater = new ItemRayRecord(ModInfo.MOD_ID, "smbUnderwater", 513).setUnlocalizedName("records.smbUnderwater");

    public static ItemRay itemGalaxyFireFlower;
    public static ItemRay itemDebug;

    public static final ItemRay itemCoinCurrency = new ItemCoinCurrency();

    public static void initItems()
    {
        LogHelper.info("Loading Items");

        GameRegistry.registerItem(itemMarioCoin, "itemCoin");
        GameRegistry.registerItem(itemMarioMushroom, "itemMushroom");
        GameRegistry.registerItem(item1Up, "item1Up");
        GameRegistry.registerItem(itemCape, "itemCape");
        GameRegistry.registerItem(itemFireFlower, "itemFireFlower");
        GameRegistry.registerItem(itemStarMan, "itemStarMan");
        GameRegistry.registerItem(itemSpiceMan, "itemSpiceMan");

        GameRegistry.registerItem(itemCoinCurrency, "itemCoinCurrency");

        GameRegistry.registerItem(itemRecordSuperSpiceBros, "itemRecordSuperSpiceBros");
        GameRegistry.registerItem(itemRecordSMBUnderwater, "itemRecordSMBUnderwater");

        if(ConfigHandler.debugModeEnabled)
        {
            itemGalaxyFireFlower = new ItemGalaxyFireFlower();
            itemDebug = new ItemDebug();

            GameRegistry.registerItem(itemGalaxyFireFlower, "itemGalaxyFireFlower");
            GameRegistry.registerItem(itemDebug, "itemDebug");
        }

        OreDictionary.registerOre("itemMarioCoin", new ItemStack(itemMarioCoin, 1, 0));
        OreDictionary.registerOre("itemMarioCoin", new ItemStack(itemMarioCoin, 1, 1));

        LogHelper.info("Item Loading Complete");
    }
}