package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.raycore.api.item.RayItemRegistry;
import io.github.Theray070696.raycore.item.ItemRay;
import io.github.Theray070696.raycore.item.ItemRayRecord;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class ModItems
{
    public static ItemRay itemMarioCoin;
    public static ItemRay itemMarioMushroom;
    public static ItemRay item1Up;
    public static ItemRay itemCape;
    public static ItemRay itemFireFlower;
    public static ItemRay itemStarMan;

    public static Item itemRecordSMBUnderwater;

    public static ItemRay itemGalaxyFireFlower;

    public static ItemRay itemCoinCurrency;

    public static ItemRay itemPipeLink;

    public static void initItems()
    {
        LogHelper.info("Loading Items");

        itemMarioCoin = RayItemRegistry.registerItem(new ItemCoin());
        itemMarioMushroom = RayItemRegistry.registerItem(new ItemMushroom());
        item1Up = RayItemRegistry.registerItem(new Item1Up());
        itemCape = RayItemRegistry.registerItem(new ItemCape());
        itemFireFlower = RayItemRegistry.registerItem(new ItemFireFlower());
        itemStarMan = RayItemRegistry.registerItem(new ItemStarMan());
        itemPipeLink = RayItemRegistry.registerItem(new ItemPipeLink());

        itemCoinCurrency = RayItemRegistry.registerItem(new ItemCoinCurrency());

        //itemRecordSuperSpiceBros = RayItemRegistry.registerItem(new ItemRayRecord(ModInfo.MOD_ID, "spiceman", 1121, SoundHandler.recordSpiceman).setUnlocalizedName("records.spiceman"));
        itemRecordSMBUnderwater = RayItemRegistry.registerItem(new ItemRayRecord(ModInfo.MOD_ID, "smbUnderwater", 513, SoundHandler.recordUnderwaterSMB).setUnlocalizedName("records.smbUnderwater"));
        //itemRecordLOLUDied = RayItemRegistry.registerItem(new ItemRayRecord(ModInfo.MOD_ID, "lolUDied", 1619, SoundHandler.rareDeath1).setUnlocalizedName("records.rareDeath1"));

        if(ConfigHandler.debugModeEnabled)
        {
            itemGalaxyFireFlower = RayItemRegistry.registerItem(new ItemGalaxyFireFlower());
        }

        OreDictionary.registerOre("itemMarioCoin", new ItemStack(itemMarioCoin, 1, 0));
        OreDictionary.registerOre("itemMarioCoin", new ItemStack(itemMarioCoin, 1, 1));
        OreDictionary.registerOre("itemMarioCoin", new ItemStack(itemMarioCoin, 1, 2));

        OreDictionary.registerOre("record", itemRecordSMBUnderwater);

        LogHelper.info("Item Loading Complete");
    }
}