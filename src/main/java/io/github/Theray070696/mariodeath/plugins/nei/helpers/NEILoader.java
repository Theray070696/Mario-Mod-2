package io.github.Theray070696.mariodeath.plugins.nei.helpers;

import codechicken.nei.api.API;
import cpw.mods.fml.relauncher.Side;
import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.plugins.nei.helpers.MarioShapedRecipeHandler;
import io.github.Theray070696.mariodeath.plugins.nei.helpers.MarioShapelessRecipeHandler;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class NEILoader
{
    public static void init()
    {
        if(MarioDeath.proxy.getSide().equals(Side.SERVER))
        {
            return;
        }

        API.registerRecipeHandler(new MarioShapedRecipeHandler());
        API.registerUsageHandler(new MarioShapedRecipeHandler());

        API.registerRecipeHandler(new MarioShapelessRecipeHandler());
        API.registerUsageHandler(new MarioShapelessRecipeHandler());
    }
}
