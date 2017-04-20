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
    public static int marioDimensionID;
    public static int mushroomKingdomBiomeID;
    public static boolean debugModeEnabled;

    public static final boolean retrogenEnabledDefault = false;
    public static final int marioDimensionIDDefault = 25;
    public static final int mushroomKingdomBiomeIDDefault = 144;
    public static final boolean debugModeEnabledDefault = false;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        retrogenEnabled = config.getBoolean("Retrogen Enabled", "Retrogen", retrogenEnabledDefault, "Whether or not to enable retrogen. In EXTREMELY early testing, BACKUP YOUR SAVE!");

        marioDimensionID = config.get("Mario Dimension ID", "Dimensions", marioDimensionIDDefault, "Dimension ID for the Mario Dimension.").getInt(marioDimensionIDDefault);

        mushroomKingdomBiomeID = config.get("Mushroom Kingdom Biome ID", "Biomes", mushroomKingdomBiomeIDDefault, "Biome ID for the Mushroom Kingdom.").getInt(mushroomKingdomBiomeIDDefault);

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
