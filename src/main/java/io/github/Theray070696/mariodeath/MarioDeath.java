package io.github.Theray070696.mariodeath;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.core.CraftingHandler;
import io.github.Theray070696.mariodeath.core.FMLEventHandler;
import io.github.Theray070696.mariodeath.core.ForgeEventHandler;
import io.github.Theray070696.mariodeath.core.GuiHandler;
import io.github.Theray070696.mariodeath.item.ModItems;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.plugins.PluginHandler;
import io.github.Theray070696.mariodeath.proxy.IProxy;
import io.github.Theray070696.mariodeath.util.LogHelper;
import io.github.Theray070696.mariodeath.world.WorldGenMario;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Theray on 8/25/15.
 */
@SuppressWarnings("unused")
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, dependencies = ModInfo.DEPENDENCIES)
public class MarioDeath
{
    @Mod.Instance(ModInfo.MOD_ID)
    public static MarioDeath INSTANCE;
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy proxy;

    ForgeEventHandler forgeEventHandler;

    public MarioDeath()
    {
        PluginHandler.getInstance().registerBuiltInPlugins();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Pre-Init");

        ConfigHandler.loadConfig(event);

        ModBlocks.initBlocks();
        ModItems.initItems();

        proxy.preInit(event);

        PluginHandler.getInstance().preInit();

        LogHelper.info("Pre-Init Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Init");

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        forgeEventHandler = new ForgeEventHandler();

        MinecraftForge.EVENT_BUS.register(forgeEventHandler);
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());

        LogHelper.info("Loading Crafting Recipes");

        CraftingHandler.initCraftingRecipes();
        CraftingHandler.initSmeltingRecipes();
        CraftingHandler.initMarioMakerRecipes();

        LogHelper.info("Crafting Recipes Loaded");

        LogHelper.info("Registering World Generation");

        GameRegistry.registerWorldGenerator(new WorldGenMario(), 0);

        LogHelper.info("World Generation Registered");

        PluginHandler.getInstance().init();

        LogHelper.info("Init Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Init");

        PluginHandler.getInstance().postInit();

        LogHelper.info("Post-Init Complete");
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        forgeEventHandler.serverAboutToStart(event);
    }
}