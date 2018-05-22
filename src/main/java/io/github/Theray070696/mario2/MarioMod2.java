package io.github.Theray070696.mario2;

import com.google.common.base.Stopwatch;
import io.github.Theray070696.mario2.api.MarioModAPI;
import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.capability.CapabilityHandler;
import io.github.Theray070696.mario2.capability.CoinCount;
import io.github.Theray070696.mario2.capability.CoinCountStorage;
import io.github.Theray070696.mario2.capability.ICoinCount;
import io.github.Theray070696.mario2.command.CommandMario;
import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.core.CraftingHandler;
import io.github.Theray070696.mario2.core.EventHandler;
import io.github.Theray070696.mario2.core.GuiHandler;
import io.github.Theray070696.mario2.core.api.CraftingHandlerAPI;
import io.github.Theray070696.mario2.item.ModItems;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.network.PacketGetCoins;
import io.github.Theray070696.mario2.network.PacketSyncCoinCounter;
import io.github.Theray070696.mario2.plugins.PluginHandler;
import io.github.Theray070696.mario2.potion.PotionEffectsMario;
import io.github.Theray070696.mario2.proxy.IProxy;
import io.github.Theray070696.mario2.world.ModBiomes;
import io.github.Theray070696.mario2.world.ModDimension;
import io.github.Theray070696.mario2.world.WorldGenMario;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Theray070696 on 8/25/15.
 */
@SuppressWarnings("unused")
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, dependencies = ModInfo.DEPENDENCIES)
public class MarioMod2
{
    @Mod.Instance(ModInfo.MOD_ID)
    public static MarioMod2 INSTANCE;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy proxy;

    public static CreativeTabs tabMarioItems = new CreativeTabs("tabMarioItems")
    {
        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.itemMushroom, 1, 1);
        }
    };

    public static CreativeTabs tabMarioBlocks = new CreativeTabs("tabMarioBlocks")
    {
        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModBlocks.marioBlockMarioMaker);
        }
    };

    public static SimpleNetworkWrapper network;
    private Stopwatch stopwatchInitPhases;
    public Logger logger;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event)
    {
        MarioModAPI.marioCraftingHandler = new CraftingHandlerAPI();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception
    {
        proxy.construct(event);

        this.stopwatchInitPhases = Stopwatch.createStarted();
        event.getModLog().info("Pre-Init");

        ConfigHandler.loadConfig(event);

        this.logger = event.getModLog();

        PluginHandler.getInstance().registerBuiltInPlugins();

        SoundHandler.init();

        LootTableList.register(new ResourceLocation(ModInfo.MOD_ID, "inject/disks"));

        proxy.preInit(event);

        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
        network.registerMessage(PacketSyncCoinCounter.Handler.class, PacketSyncCoinCounter.class, 0, Side.CLIENT);
        network.registerMessage(PacketGetCoins.Handler.class, PacketGetCoins.class, 1, Side.SERVER);

        PluginHandler.getInstance().preInit();

        long time = this.stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Pre-Init Complete in " + time + "ms");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        this.stopwatchInitPhases = Stopwatch.createStarted();
        this.logger.info("Init");

        ModBlocks.initBlocks();
        ModItems.initItems();
        PotionEffectsMario.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        CapabilityManager.INSTANCE.register(ICoinCount.class, new CoinCountStorage(), CoinCount.class);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

        MinecraftForge.EVENT_BUS.register(new EventHandler());

        this.logger.info("Loading Crafting Recipes");
        CraftingHandler.initCraftingRecipes();
        CraftingHandler.initSmeltingRecipes();
        CraftingHandler.initMarioMakerRecipes();
        this.logger.info("Crafting Recipes Loaded");

        this.logger.info("Registering World Generation");
        GameRegistry.registerWorldGenerator(new WorldGenMario(), 0);

        ModBiomes.initBiomes();
        ModDimension.initDimension();

        this.logger.info("World Generation Registered");

        proxy.init(event);

        PluginHandler.getInstance().init();

        long time = this.stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Init Complete in " + time + "ms");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        this.stopwatchInitPhases = Stopwatch.createStarted();
        this.logger.info("Post-Init");

        PluginHandler.getInstance().postInit();

        long time = this.stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Post-Init Complete in " + time + "ms");

        this.stopwatchInitPhases = null;
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        if(ConfigHandler.developerModeEnabled)
        {
            event.registerServerCommand(new CommandMario());
        }
    }
}