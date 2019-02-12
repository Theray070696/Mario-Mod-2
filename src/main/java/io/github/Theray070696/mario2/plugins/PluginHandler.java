package io.github.Theray070696.mario2.plugins;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.plugins.crafttweaker.CraftTweakerPlugin;
import net.minecraftforge.fml.common.Loader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class PluginHandler
{
    private static PluginHandler instance;
    private List<IPlugin> plugins = new LinkedList<IPlugin>();
    private Phase currentPhase = Phase.PRELAUNCH;
    private PluginHandler()
    {
    }

    public static PluginHandler getInstance()
    {
        if(instance == null)
        {
            instance = new PluginHandler();
        }
        return instance;
    }

    public void registerPlugin(IPlugin plugin)
    {
        loadPlugin(plugin);
    }

    private void loadPlugin(IPlugin plugin)
    {
        if(!Loader.isModLoaded(plugin.getModID()))
        {
            return;
        }

        MarioMod2.INSTANCE.logger.info("Registering plugin for " + plugin.getModID());
        this.plugins.add(plugin);

        switch(this.currentPhase)
        {
            case DONE:
            case POSTINIT:
                plugin.preInit();
                plugin.init();
                plugin.postInit();
                break;
            case INIT:
                plugin.preInit();
                plugin.init();
                break;
            case PREINIT:
                plugin.preInit();
                break;
            default:
                break;
        }
    }

    public void preInit()
    {
        this.currentPhase = Phase.PREINIT;
        for(IPlugin plugin : this.plugins)
        {
            plugin.preInit();
        }
    }

    public void init()
    {
        this.currentPhase = Phase.INIT;
        for(IPlugin plugin : this.plugins)
        {
            plugin.init();
        }
    }

    public void postInit()
    {
        this.currentPhase = Phase.POSTINIT;
        for(IPlugin plugin : this.plugins)
        {
            plugin.postInit();
        }
        this.currentPhase = Phase.DONE;
    }

    public void registerBuiltInPlugins()
    {
        registerPlugin(new CraftTweakerPlugin());
    }

    private enum Phase
    {
        PRELAUNCH, PREINIT, INIT, POSTINIT, DONE
    }
}
