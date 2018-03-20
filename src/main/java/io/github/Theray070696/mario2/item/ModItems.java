package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.item.ItemRay;
import io.github.Theray070696.raycore.item.ItemRayRecord;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray070696 on 8/27/2015.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModItems
{
    public static final Item itemCoin = null;
    public static final Item itemMushroom = null;
    public static final Item item1Up = null;
    public static final Item itemCape = null;
    public static final Item itemFireFlower = null;
    public static final Item itemStarMan = null;
    public static final Item itemSuperLeaf = null;
    public static final Item itemDebug = null;
    public static final Item itemCoinCurrency = null;
    public static final Item itemPipeLink = null;

    @GameRegistry.ObjectHolder("records.smbUnderwater")
    public static final Item itemRecordSMBUnderwater = null;

    private static final Item[] ITEMS =
            {
                    new ItemCoin(),
                    new ItemMushroom(),
                    new Item1Up(),
                    new ItemCape(),
                    new ItemFireFlower(),
                    new ItemStarMan(),
                    new ItemPipeLink(),
                    new ItemCoinCurrency(),
                    new ItemRayRecord(ModInfo.MOD_ID, "smbUnderwater", 513, SoundHandler.recordUnderwaterSMB).setUnlocalizedName("records.smbUnderwater"),
                    new ItemSuperLeaf(),
                    new ItemDebug()
            };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        for(Item item : ITEMS)
        {
            event.getRegistry().register(item);
        }

        MarioMod2.INSTANCE.logger.info("Item Registration Complete");
    }

    @SubscribeEvent
    public static void loadItemModels(ModelRegistryEvent event)
    {
        for(Item item : ITEMS)
        {
            if(!item.getHasSubtypes())
            {
                RayCore.proxy.registerItemRenderer(item, 0, ModInfo.MOD_ID, item.getRegistryName().getResourcePath());
            } else if(item instanceof ItemRay)
            {
                for(int i = 0; i < ((ItemRay) item).getMaxMetadata(); i++)
                {
                    RayCore.proxy.registerItemRenderer(item, i, ModInfo.MOD_ID, item.getRegistryName().getResourcePath() + "_" + i);
                }
            }
        }
    }

    public static void initItems()
    {
        MarioMod2.INSTANCE.logger.info("Loading Extra Item Data");

        OreDictionary.registerOre("itemMushroom", new ItemStack(itemMushroom, 1, 0));
        OreDictionary.registerOre("itemMushroom", new ItemStack(itemMushroom, 1, 1));
        OreDictionary.registerOre("itemMushroom", new ItemStack(itemMushroom, 1, 2));

        OreDictionary.registerOre("itemMario1Up", new ItemStack(item1Up, 1, 0));
        OreDictionary.registerOre("itemMario1Up", new ItemStack(item1Up, 1, 1));
        OreDictionary.registerOre("itemMario1Up", new ItemStack(item1Up, 1, 2));

        OreDictionary.registerOre("itemCoin", new ItemStack(itemCoin, 1, 0));
        OreDictionary.registerOre("itemCoin", new ItemStack(itemCoin, 1, 1));
        OreDictionary.registerOre("itemCoin", new ItemStack(itemCoin, 1, 2));

        OreDictionary.registerOre("record", itemRecordSMBUnderwater);

        MarioMod2.INSTANCE.logger.info("Extra Item Data Loading Complete");
    }
}