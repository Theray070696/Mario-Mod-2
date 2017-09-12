package io.github.Theray070696.mario2.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class ConfigHandler
{
    public static final boolean developerModeEnabledDefault = false;
    public static boolean developerModeEnabled;
    public static int marioDimensionID;
    public static int marioDimensionIDDefault = 75;
    private static Configuration config;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        developerModeEnabled = config.getBoolean("Developer Mode Enabled", "Misc", developerModeEnabledDefault, "Whether or not to enable developer" +
                " mode. Use this if you want to test items that are not complete or don't function at all.");

        marioDimensionID = config.getInt("Mario Dimension ID", "Dimension", marioDimensionIDDefault, Integer.MIN_VALUE, Integer.MAX_VALUE,
                "Dimension ID of the Mario dimension.");

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
