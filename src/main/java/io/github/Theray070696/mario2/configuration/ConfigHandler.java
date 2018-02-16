package io.github.Theray070696.mario2.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class ConfigHandler
{
    public static final boolean developerModeEnabledDefault = false;
    public static final boolean enableDeathSoundsDefault = true;
    public static final boolean enableMarioSoundsDefault = true;
    public static final boolean enableCurrencyCoinsDefault = true;

    public static boolean developerModeEnabled;
    public static boolean enableDeathSounds;
    public static boolean enableMarioSounds;
    public static boolean enableCurrencyCoins;

    public static int marioDimensionID;
    public static int marioDimensionIDDefault = 75;

    private static Configuration config;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        developerModeEnabled = config.getBoolean("Developer Mode Enabled", "Misc", developerModeEnabledDefault, "Whether or not to enable " +
                "developer" + " mode. Use this if you want to test items that are not complete or don't function at all.");

        enableMarioSounds = config.getBoolean("Enable Join/Leave sounds", "Misc", enableMarioSoundsDefault, "Whether or not joining and leaving " +
                "sounds should be enabled.");

        enableCurrencyCoins = config.getBoolean("Enable Currency Coins", "Misc", enableCurrencyCoinsDefault, "Whether or not currency coins sounds " +
                "should be enabled.");

        enableDeathSounds = config.getBoolean("Enable Join/Leave sounds", "Misc", enableDeathSoundsDefault, "Whether or not death sounds should be " +
                "" + "" + "enabled.");

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
