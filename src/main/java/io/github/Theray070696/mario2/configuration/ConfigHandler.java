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
    public static boolean enableDeathSounds;
    public static final boolean enableDeathSoundsDefault = true;
    public static boolean enableJoinLeaveSounds;
    public static final boolean enableJoinLeaveSoundsDefault = true;
    public static boolean enableCoinDrops;
    public static final boolean enableCoinDropsDefault = true;
    public static int marioDimensionID;
    public static int marioDimensionIDDefault = 75;
    private static Configuration config;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        developerModeEnabled = config.getBoolean("Developer Mode Enabled", "Misc", developerModeEnabledDefault, "Whether or not to enable " +
                "developer mode. Use this if you want to test items that are not complete or don't function at all.");

        enableJoinLeaveSounds = config.getBoolean("Enable Join/Leave sounds", "Misc", enableJoinLeaveSoundsDefault, "Whether or not joining and " +
                "leaving sounds should be enabled.");

        enableDeathSounds = config.getBoolean("Enable Death sounds", "Misc", enableDeathSoundsDefault, "Whether or not death sounds should be " +
                "enabled.");

        enableCoinDrops = config.getBoolean("Enable special coin drops", "Misc", enableCoinDropsDefault, "Whether or not special coins should drop " +
                "from mobs.");

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
