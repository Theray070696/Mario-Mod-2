package io.github.Theray070696.mariodeath.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGoomba extends ModelBase
{
    public ModelRenderer a;
    public ModelRenderer b;
    public ModelRenderer Rfoot;
    public ModelRenderer c;
    public ModelRenderer d;
    public ModelRenderer t;
    public ModelRenderer Lfoot;
    public ModelRenderer e;
    public ModelRenderer g;
    public ModelRenderer h;

    public ModelGoomba()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.g = new ModelRenderer(this, 19, 16);
        this.g.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.g.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
        this.Rfoot = new ModelRenderer(this, 0, 16);
        this.Rfoot.setRotationPoint(-5.0F, 21.0F, -2.0F);
        this.Rfoot.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.e = new ModelRenderer(this, 49, 13);
        this.e.setRotationPoint(-4.0F, 13.0F, -4.0F);
        this.e.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
        this.c = new ModelRenderer(this, 0, 42);
        this.c.setRotationPoint(-5.0F, 19.0F, -5.0F);
        this.c.addBox(0.0F, 0.0F, 0.0F, 10, 1, 10, 0.0F);
        this.h = new ModelRenderer(this, 0, 33);
        this.h.setRotationPoint(-3.0F, 20.0F, -3.0F);
        this.h.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
        this.b = new ModelRenderer(this, 35, 32);
        this.b.setRotationPoint(-1.0F, 21.0F, -2.0F);
        this.b.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4, 0.0F);
        this.t = new ModelRenderer(this, 49, 0);
        this.t.setRotationPoint(-5.0F, 14.0F, -5.0F);
        this.t.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
        this.Lfoot = new ModelRenderer(this, 0, 24);
        this.Lfoot.setRotationPoint(1.0F, 21.0F, -2.0F);
        this.Lfoot.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.a = new ModelRenderer(this, 23, 24);
        this.a.setRotationPoint(-2.0F, 11.0F, -2.0F);
        this.a.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
        this.d = new ModelRenderer(this, 0, 0);
        this.d.setRotationPoint(-6.0F, 16.0F, -6.0F);
        this.d.addBox(0.0F, 0.0F, 0.0F, 12, 3, 12, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.g.render(f5);
        this.Rfoot.render(f5);
        this.e.render(f5);
        this.c.render(f5);
        this.h.render(f5);
        this.b.render(f5);
        this.t.render(f5);
        this.Lfoot.render(f5);
        this.a.render(f5);
        this.d.render(f5);
    }
}
