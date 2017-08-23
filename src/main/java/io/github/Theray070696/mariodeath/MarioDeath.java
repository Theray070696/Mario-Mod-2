package io.github.Theray070696.mariodeath;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.capability.CapabilityHandler;
import io.github.Theray070696.mariodeath.capability.CoinCount;
import io.github.Theray070696.mariodeath.capability.CoinCountStorage;
import io.github.Theray070696.mariodeath.capability.ICoinCount;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.core.CraftingHandler;
import io.github.Theray070696.mariodeath.core.EventHandler;
import io.github.Theray070696.mariodeath.core.GuiHandler;
import io.github.Theray070696.mariodeath.item.ModItems;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.network.PacketGetCoins;
import io.github.Theray070696.mariodeath.network.PacketSyncCoinCounter;
import io.github.Theray070696.mariodeath.plugins.PluginHandler;
import io.github.Theray070696.mariodeath.potion.PotionEffectsMario;
import io.github.Theray070696.mariodeath.proxy.IProxy;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.mariodeath.world.ModBiomes;
import io.github.Theray070696.mariodeath.world.WorldGenMario;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by Theray070696 on 8/25/15.
 */
@SuppressWarnings("unused")
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, dependencies = ModInfo.DEPENDENCIES)
public class MarioDeath
{
    @Mod.Instance(ModInfo.MOD_ID)
    public static MarioDeath INSTANCE;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy proxy;

    public static CreativeTabs tabMarioItems = new CreativeTabs("tabMarioItems")
    {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(ModItems.itemMarioMushroom, 1, 1);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public int getIconItemDamage()
        {
            return 1;
        }

        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem()
        {
            return new ItemStack(ModItems.itemMarioMushroom, 1, 1).getItem();
        }
    };

    public static CreativeTabs tabMarioBlocks = new CreativeTabs("tabMarioBlocks")
    {
        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(ModBlocks.blockMarioMaker);
        }

        @Nonnull
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem()
        {
            return new ItemStack(ModBlocks.blockMarioMaker).getItem();
        }
    };

    public static SimpleNetworkWrapper network;

    public static boolean isCTMLoaded = false;

    public MarioDeath()
    {
        PluginHandler.getInstance().registerBuiltInPlugins();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Pre-Init");

        ConfigHandler.loadConfig(event);

        SoundHandler.init();

        ModBlocks.initBlocks();
        ModItems.initItems();
        PotionEffectsMario.init();

        LootTableList.register(new ResourceLocation(ModInfo.MOD_ID, "inject/disks"));

        proxy.preInit(event);

        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
        network.registerMessage(PacketSyncCoinCounter.Handler.class, PacketSyncCoinCounter.class, 0, Side.CLIENT);
        network.registerMessage(PacketGetCoins.Handler.class, PacketGetCoins.class, 1, Side.SERVER);

        PluginHandler.getInstance().preInit();

        LogHelper.info("Pre-Init Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Init");

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        CapabilityManager.INSTANCE.register(ICoinCount.class, new CoinCountStorage(), CoinCount.class);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

        MinecraftForge.EVENT_BUS.register(new EventHandler());

        LogHelper.info("Loading Crafting Recipes");
        CraftingHandler.initCraftingRecipes();
        CraftingHandler.initSmeltingRecipes();
        CraftingHandler.initMarioMakerRecipes();
        LogHelper.info("Crafting Recipes Loaded");

        LogHelper.info("Registering World Generation");
        GameRegistry.registerWorldGenerator(new WorldGenMario(), 0);

        ModBiomes.initBiomes();

        // These two will come back as soon an I can make them interesting and not just "Overworld with Mario blocks".
        //DimensionManager.registerDimension(ConfigHandler.marioDimensionID, DimensionType.register("Marios World", "_mario", ConfigHandler
        // .marioDimensionID, WorldProviderMario.class, false));
        //ModBiomes.registerWithBiomeDictionary();
        LogHelper.info("World Generation Registered");

        proxy.init(event);

        PluginHandler.getInstance().init();

        LogHelper.info("Init Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Init");

        PluginHandler.getInstance().postInit();

        isCTMLoaded = Loader.isModLoaded("ctm");

        if(isCTMLoaded)
        {
            LogHelper.info("Connected Textures Mod Detected");
        }

        LogHelper.info("Post-Init Complete");
    }
}