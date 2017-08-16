package io.github.Theray070696.mariodeath.plugins.nei;

import codechicken.nei.api.API;
import cpw.mods.fml.relauncher.Side;
import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.plugins.IPlugin;
import io.github.Theray070696.mariodeath.plugins.nei.helpers.MarioShapedRecipeHandler;
import io.github.Theray070696.mariodeath.plugins.nei.helpers.MarioShapelessRecipeHandler;
import io.github.Theray070696.mariodeath.plugins.nei.helpers.NEILoader;

/**
 * Created by Theray on 3/31/2016.
 */
public class NotEnoughItems implements IPlugin
{
    
    @Override
    public String getModID()
    {
        return "NotEnoughItems";
    }

    @Override
    public void preInit() {}

    @Override
    public void init()
    {
        NEILoader.init();
    }

    @Override
    public void postInit() {}
}
