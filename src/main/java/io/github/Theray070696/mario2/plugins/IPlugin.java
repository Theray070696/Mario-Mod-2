package io.github.Theray070696.mario2.plugins;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public interface IPlugin
{
    public String getModID();

    public void preInit();

    public void init();

    public void postInit();
}
