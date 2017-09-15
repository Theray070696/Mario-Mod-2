package io.github.Theray070696.mario2.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 8/25/15.
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public Side getSide()
    {
        return Side.SERVER;
    }

    @Override
    public void construct(FMLPreInitializationEvent event) throws Exception
    {
        super.construct(event);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void setClientCoinCount(int amount)
    {
    }
}