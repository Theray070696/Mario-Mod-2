package io.github.Theray070696.mariodeath;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.capability.CapabilityHandler;
import io.github.Theray070696.mariodeath.capability.CoinCount;
import io.github.Theray070696.mariodeath.capability.CoinCountStorage;
import io.github.Theray070696.mariodeath.capability.ICoinCount;
import io.github.Theray070696.mariodeath.command.CommandMario;
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
import io.github.Theray070696.mariodeath.world.ModDimension;
import io.github.Theray070696.mariodeath.world.WorldGenMario;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.functions.ArtifactVersionNameFunction;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;
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

    private Stopwatch stopwatch;
    private Stopwatch stopwatchInitPhases;

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

    public MarioMod2()
    {
        PluginHandler.getInstance().registerBuiltInPlugins();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        stopwatch = Stopwatch.createStarted();
        stopwatchInitPhases = Stopwatch.createStarted();
        LogHelper.info("Pre-Init");

        if(proxy.getSide().isClient() && !Loader.isModLoaded("ctm")) // We need CTM for some of the blocks in the mod.
        {
            Map<String, ArtifactVersion> names = Maps.uniqueIndex(Loader.instance().activeModContainer().getRequirements(), new
                    ArtifactVersionNameFunction());
            Set<ArtifactVersion> versionMissingMods = Sets.newHashSet();

            FMLLog.severe("The mod %s (%s) requires mods %s to be available", ModInfo.MOD_ID, ModInfo.MOD_NAME, "ctm");
            versionMissingMods.add(names.get("ctm"));
            RuntimeException ret = new MissingModsException(versionMissingMods, ModInfo.MOD_ID, ModInfo.MOD_NAME);
            FMLLog.severe(ret.getMessage());
            throw ret;
        }

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

        long time = stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        LogHelper.info("Pre-Init Complete in " + time + "ms");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        stopwatchInitPhases = Stopwatch.createStarted();
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
        ModDimension.initDimension();

        LogHelper.info("World Generation Registered");

        proxy.init(event);

        PluginHandler.getInstance().init();

        long time = stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        LogHelper.info("Init Complete in " + time + "ms");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        stopwatchInitPhases = Stopwatch.createStarted();
        LogHelper.info("Post-Init");

        PluginHandler.getInstance().postInit();

        long time = stopwatchInitPhases.stop().elapsed(TimeUnit.MILLISECONDS);
        LogHelper.info("Post-Init Complete in " + time + "ms");

        time = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        LogHelper.info("Mario Mod 2 loaded in " + time + "ms");
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