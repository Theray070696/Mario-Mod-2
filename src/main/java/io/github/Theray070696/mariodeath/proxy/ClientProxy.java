package io.github.Theray070696.mariodeath.proxy;

import io.github.Theray070696.mariodeath.capability.CoinCountProvider;
import io.github.Theray070696.mariodeath.client.render.RenderGoomba;
import io.github.Theray070696.mariodeath.core.ClientEventHandler;
import io.github.Theray070696.mariodeath.entity.EntityGoomba;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 8/25/15.
 */
public class ClientProxy extends CommonProxy
{

    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityGoomba.class, new IRenderFactory<EntityGoomba>()
        {
            @Override
            public Render<? super EntityGoomba> createRenderFor(RenderManager manager)
            {
                return new RenderGoomba(manager);
            }
        });
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void setClientCoinCount(int amount)
    {
        FMLClientHandler.instance().getClientPlayerEntity().getCapability(CoinCountProvider.COIN_COUNT, null).setCoinCount(amount);
    }
}