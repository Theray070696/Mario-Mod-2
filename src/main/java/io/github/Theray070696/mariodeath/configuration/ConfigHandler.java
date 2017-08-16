package io.github.Theray070696.mariodeath.configuration;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by Theray on 3/31/2016.
 */
public class ConfigHandler
{
    private static Configuration config;

    public static boolean retrogenEnabled;

    public static final boolean retrogenEnabledDefault = false;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        retrogenEnabled = config.getBoolean("Retrogen Enabled", "Retrogen", retrogenEnabledDefault, "Whether or not to enable retrogen. In EXTREMELY early testing, BACKUP YOUR SAVE!");

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
