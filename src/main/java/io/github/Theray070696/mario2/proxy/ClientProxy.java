package io.github.Theray070696.mario2.proxy;

import com.google.common.collect.ImmutableSet;
import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.client.render.RenderFireball;
import io.github.Theray070696.mario2.client.render.RenderGoomba;
import io.github.Theray070696.mario2.client.render.RenderKoopa;
import io.github.Theray070696.mario2.core.ClientEventHandler;
import io.github.Theray070696.mario2.entity.EntityFireball;
import io.github.Theray070696.mario2.entity.EntityGoomba;
import io.github.Theray070696.mario2.entity.EntityKoopa;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionRange;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;

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
    public void construct(FMLPreInitializationEvent event) throws Exception
    {
        super.construct(event);

        if(!(Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
        {
            Field missingException = ReflectionHelper.findField(FMLClientHandler.class, "modsMissing");
            VersionRange range = VersionRange.createFromVersionSpec("[MC1.10,)");
            if(!Loader.isModLoaded("ctm") || !range.containsVersion(Loader.instance().getIndexedModList().get("ctm").getProcessedVersion()))
            {
                if(missingException.get(FMLClientHandler.instance()) == null)
                {
                    missingException.set(FMLClientHandler.instance(), new MissingModsException(ImmutableSet.of(new DefaultArtifactVersion("CTM",
                            range)), ModInfo.MOD_ID, ModInfo.MOD_NAME));
                }
            }
        }
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

        RenderingRegistry.registerEntityRenderingHandler(EntityKoopa.class, new IRenderFactory<EntityKoopa>()
        {
            @Override
            public Render<? super EntityKoopa> createRenderFor(RenderManager manager)
            {
                return new RenderKoopa(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityFireball.class, new IRenderFactory<EntityFireball>()
        {
            @Override
            public Render<? super EntityFireball> createRenderFor(RenderManager manager)
            {
                return new RenderFireball(manager);
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