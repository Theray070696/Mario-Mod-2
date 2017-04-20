package io.github.Theray070696.mariodeath.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class ConfigHandler
{
    private static Configuration config;

    public static boolean debugModeEnabled;

    public static final boolean debugModeEnabledDefault = false;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        debugModeEnabled = config.getBoolean("Debug Mode Enabled", "Misc", debugModeEnabledDefault, "Whether or not to enable debug mode. Use this if you want to have items that are not complete or don't function at all.");

        saveConfig();
    }

    public static void saveConfig()
    {
        if(config != null)
        {
            config.save();
        }
    }
}
