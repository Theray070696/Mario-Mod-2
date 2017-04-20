package io.github.Theray070696.mariodeath.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Theray on 8/25/15.
 */
public interface IProxy
{
    public Side getSide();

    public void preInit(FMLPreInitializationEvent event);
}