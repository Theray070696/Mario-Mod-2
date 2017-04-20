package io.github.Theray070696.mariodeath.potion;

import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 3/25/2017.
 */
public class PotionEffectsMario
{
    public static Potion potionStarman = new PotionMario(false, 16776960).setPotionName("effect.starman").setBeneficial();
    public static Potion potionSpiceman = new PotionMario(false, 16711680).setPotionName("effect.spiceman").setBeneficial();

    public static void init()
    {
        potionSpiceman.setRegistryName(ModInfo.MOD_ID, "potionSpiceman");
        potionStarman.setRegistryName(ModInfo.MOD_ID, "potionStarman");

        GameRegistry.register(potionSpiceman);
        GameRegistry.register(potionStarman);
    }
}