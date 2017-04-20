package io.github.Theray070696.mariodeath.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Theray on 8/25/15.
 */
@SuppressWarnings("unused")
public class ServerProxy extends CommonProxy
{

    @Override
    public Side getSide()
    {
        return Side.SERVER;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }
}