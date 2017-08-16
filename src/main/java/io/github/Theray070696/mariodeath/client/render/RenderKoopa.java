package io.github.Theray070696.mariodeath.client.render;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class RenderKoopa extends RenderLiving
{
    public RenderKoopa(RenderManager manager)
    {
        super(manager, new ModelKoopa(), 0);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(ModInfo.MOD_ID, "textures/entities/koopa.png");
    }
}
