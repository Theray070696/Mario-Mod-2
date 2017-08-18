package io.github.Theray070696.mariodeath.plugins;

import io.github.Theray070696.mariodeath.util.LogHelper;
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

        LogHelper.info("Registering plugin for " + plugin.getModID());
        plugins.add(plugin);

        switch(currentPhase)
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
        currentPhase = Phase.PREINIT;
        for(IPlugin plugin : plugins)
        {
            plugin.preInit();
        }
    }

    public void init()
    {
        currentPhase = Phase.INIT;
        for(IPlugin plugin : plugins)
        {
            plugin.init();
        }
    }

    public void postInit()
    {
        currentPhase = Phase.POSTINIT;
        for(IPlugin plugin : plugins)
        {
            plugin.postInit();
        }
        currentPhase = Phase.DONE;
    }

    public void registerBuiltInPlugins()
    {
        //registerPlugin(new NotEnoughItems());
    }

    private enum Phase
    {
        PRELAUNCH, PREINIT, INIT, POSTINIT, DONE
    }
}
