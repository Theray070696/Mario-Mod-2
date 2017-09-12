package io.github.Theray070696.mario2.client.render;

import io.github.Theray070696.mario2.client.model.ModelGoomba;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Theray070696 on 5/2/2017.
 */
public class RenderGoomba extends RenderLiving
{
    public RenderGoomba(RenderManager manager)
    {
        super(manager, new ModelGoomba(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(ModInfo.MOD_ID, "textures/entities/goomba.png");
    }
}
