package io.github.Theray070696.mariodeath.plugins;

/**
 * Created by Theray on 3/31/2016.
 */
public interface IPlugin
{
    public String getModID();

    public void preInit();

    public void init();

    public void postInit();
}
