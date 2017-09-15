package io.github.Theray070696.mario2.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 8/25/15.
 */
public interface IProxy
{
    public Side getSide();

    public void construct(FMLPreInitializationEvent event) throws Exception;

    public void preInit(FMLPreInitializationEvent event);

    public void init(FMLInitializationEvent event);

    public void setClientCoinCount(int amount);
}