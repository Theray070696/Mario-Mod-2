package io.github.Theray070696.mariodeath.client.render;

import io.github.Theray070696.mariodeath.client.model.ModelFireball;
import io.github.Theray070696.mariodeath.entity.EntityFireball;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 8/17/2017
 */
public class RenderFireball extends Render
{
    private static final ResourceLocation texture = new ResourceLocation("mario2:textures/models/fireball.png");
    protected ModelBase modelFlowerFireball;

    public RenderFireball(RenderManager manager)
    {
        super(manager);
        this.shadowSize = 0.3F;
        this.modelFlowerFireball = new ModelFireball();
    }

    public void doRender(EntityFireball entityFireball, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindEntityTexture(entityFireball);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelFlowerFireball.render(entityFireball, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityFireball entityFireball)
    {
        return texture;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityFireball) par1Entity);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityFireball) par1Entity, par2, par4, par6, par8, par9);
    }
}
