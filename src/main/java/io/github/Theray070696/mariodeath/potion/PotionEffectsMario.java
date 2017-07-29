package io.github.Theray070696.mariodeath.potion;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 3/25/2017.
 */
public class PotionEffectsMario
{
    public static Potion potionStarman = new PotionMario(false, 16776960).setPotionName("effect.starman").setBeneficial();

    public static void init()
    {
        potionStarman.setRegistryName(ModInfo.MOD_ID, "potionStarman");

        GameRegistry.register(potionStarman);
    }
}