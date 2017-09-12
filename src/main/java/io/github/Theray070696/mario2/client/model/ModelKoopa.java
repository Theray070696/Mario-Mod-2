package io.github.Theray070696.mario2.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelKoopa extends ModelBase
{
    ModelRenderer aa;
    ModelRenderer ab;
    ModelRenderer ac;
    ModelRenderer ad;
    ModelRenderer af;
    ModelRenderer ae;
    ModelRenderer ag;
    ModelRenderer ah;
    ModelRenderer aj;
    ModelRenderer ak;
    ModelRenderer FootFR;
    ModelRenderer FootBR;
    ModelRenderer FootFL;
    ModelRenderer FootBL;

    public ModelKoopa()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.aa = new ModelRenderer(this, 0, 15);
        this.aa.addBox(0.0F, 0.0F, 0.0F, 6, 1, 4);
        this.aa.setRotationPoint(-3.0F, 12.0F, 2.220446E-16F);
        this.aa.setTextureSize(128, 128);
        this.aa.mirror = true;
        this.setRotation(this.aa, 0.0F, 0.0F, 0.0F);
        this.ab = new ModelRenderer(this, 0, 21);
        this.ab.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
        this.ab.setRotationPoint(-2.0F, 11.0F, 1.0F);
        this.ab.setTextureSize(128, 128);
        this.ab.mirror = true;
        this.setRotation(this.ab, 0.0F, 0.0F, 0.0F);
        this.ac = new ModelRenderer(this, 0, 26);
        this.ac.addBox(0.0F, 0.0F, 0.0F, 8, 2, 6);
        this.ac.setRotationPoint(-4.0F, 13.0F, -1.0F);
        this.ac.setTextureSize(128, 128);
        this.ac.mirror = true;
        this.setRotation(this.ac, 0.0F, 0.0F, 0.0F);
        this.ad = new ModelRenderer(this, 0, 36);
        this.ad.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8);
        this.ad.setRotationPoint(-4.0F, 15.0F, -2.0F);
        this.ad.setTextureSize(128, 128);
        this.ad.mirror = true;
        this.setRotation(this.ad, 0.0F, 0.0F, 0.0F);
        this.af = new ModelRenderer(this, 0, 47);
        this.af.addBox(0.0F, 0.0F, 0.0F, 10, 4, 10);
        this.af.setRotationPoint(-5.0F, 16.0F, -3.0F);
        this.af.setTextureSize(128, 128);
        this.af.mirror = true;
        this.setRotation(this.af, 0.0F, 0.0F, 0.0F);
        this.ae = new ModelRenderer(this, 0, 62);
        this.ae.addBox(0.0F, 0.0F, 0.0F, 8, 2, 10);
        this.ae.setRotationPoint(-4.0F, 20.0F, -3.0F);
        this.ae.setTextureSize(128, 128);
        this.ae.mirror = true;
        this.setRotation(this.ae, 0.0F, 0.0F, 0.0F);
        this.ag = new ModelRenderer(this, 0, 75);
        this.ag.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2);
        this.ag.setRotationPoint(-2.0F, 15.0F, -5.0F);
        this.ag.setTextureSize(128, 128);
        this.ag.mirror = true;
        this.setRotation(this.ag, 0.0F, 0.0F, 0.0F);
        this.ah = new ModelRenderer(this, 0, 82);
        this.ah.addBox(0.0F, 0.0F, 0.0F, 4, 10, 2);
        this.ah.setRotationPoint(-2.0F, 5.0F, -4.0F);
        this.ah.setTextureSize(128, 128);
        this.ah.mirror = true;
        this.setRotation(this.ah, 0.0F, 0.0F, 0.0F);
        this.aj = new ModelRenderer(this, 15, 0);
        this.aj.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
        this.aj.setRotationPoint(-1.0F, 8.0F, -8.0F);
        this.aj.setTextureSize(128, 128);
        this.aj.mirror = true;
        this.setRotation(this.aj, 0.0F, 0.0F, 0.0F);
        this.ak = new ModelRenderer(this, 25, 0);
        this.ak.addBox(0.0F, 0.0F, 0.0F, 4, 6, 2);
        this.ak.setRotationPoint(-2.0F, 5.0F, -6.0F);
        this.ak.setTextureSize(128, 128);
        this.ak.mirror = true;
        this.setRotation(this.ak, 0.0F, 0.0F, 0.0F);
        this.FootFR = new ModelRenderer(this, 39, 0);
        this.FootFR.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        this.FootFR.setRotationPoint(-4.0F, 22.0F, -5.0F);
        this.FootFR.setTextureSize(128, 128);
        this.FootFR.mirror = true;
        this.setRotation(this.FootFR, 0.0F, 0.0F, 0.0F);
        this.FootBR = new ModelRenderer(this, 53, 0);
        this.FootBR.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        this.FootBR.setRotationPoint(-4.0F, 22.0F, 3.0F);
        this.FootBR.setTextureSize(128, 128);
        this.FootBR.mirror = true;
        this.setRotation(this.FootBR, 0.0F, 0.0F, 0.0F);
        this.FootFL = new ModelRenderer(this, 39, 6);
        this.FootFL.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        this.FootFL.setRotationPoint(1.0F, 22.0F, -5.0F);
        this.FootFL.setTextureSize(128, 128);
        this.FootFL.mirror = true;
        this.setRotation(this.FootFL, 0.0F, 0.0F, 0.0F);
        this.FootBL = new ModelRenderer(this, 53, 6);
        this.FootBL.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        this.FootBL.setRotationPoint(1.0F, 22.0F, 3.0F);
        this.FootBL.setTextureSize(128, 128);
        this.FootBL.mirror = true;
        this.setRotation(this.FootBL, 0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.aa.render(f5);
        this.ab.render(f5);
        this.ac.render(f5);
        this.ad.render(f5);
        this.af.render(f5);
        this.ae.render(f5);
        this.ag.render(f5);
        this.ah.render(f5);
        this.aj.render(f5);
        this.ak.render(f5);
        this.FootFR.render(f5);
        this.FootBR.render(f5);
        this.FootFL.render(f5);
        this.FootBL.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void setLivingAnimations(EntityLivingBase entityLiving, float f1, float f2, float f3)
    {
        this.FootBR.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
        this.FootBL.rotateAngleX = MathHelper.cos(f1 * 0.6662F + 3.1415927F) * 1.4F * f2;
        this.FootFR.rotateAngleX = MathHelper.cos(f1 * 0.6662F + 3.1415927F) * 1.4F * f2;
        this.FootFL.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
    }
}
