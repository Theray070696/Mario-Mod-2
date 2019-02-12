package io.github.Theray070696.mario2.plugins.crafttweaker;

import io.github.Theray070696.mario2.plugins.IPlugin;

/**
 * Created by Theray070696 on 2/12/2019.
 */
public class CraftTweakerPlugin implements IPlugin
{
    @Override
    public String getModID()
    {
        return "crafttweaker";
    }

    @Override
    public void preInit()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {
        MarioMakerCT2Handler.postInit();
    }
}
