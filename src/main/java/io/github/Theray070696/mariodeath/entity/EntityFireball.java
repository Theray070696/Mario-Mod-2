package io.github.Theray070696.mariodeath.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Theray070696 on 8/17/2017
 */
public class EntityFireball extends Entity
{
    int fuse;

    public EntityFireball(World world)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.fuse = 0;
    }

    public EntityFireball(World world, double x, double y, double z, float yaw, float pitch, double force, int fuseLength)
    {
        this(world);
        this.setRotation(yaw, 0.0F);
        double xHeading = (double) (-MathHelper.sin(yaw * (float) Math.PI / 180.0F));
        double zHeading = (double) MathHelper.cos(yaw * (float) Math.PI / 180.0F);
        this.motionX = force * xHeading * MathHelper.cos(pitch / 180.0F * (float) Math.PI);
        this.motionY = -force * (double) MathHelper.sin(pitch / 180.0F * (float) Math.PI);
        this.motionZ = force * zHeading * MathHelper.cos(pitch / 180.0F * (float) Math.PI);
        this.setPosition(x + xHeading, y, z + zHeading);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.fuse = fuseLength;
    }

    public EntityFireball(World world, Entity entity)
    {
        this(world, entity.posX, entity.posY + 1.5D, entity.posZ, entity.rotationYaw, 1.0F, 0.5D, 80);
    }

    public void onUpdate()
    {
        if(!this.worldObj.isRemote)
        {
            Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult movingobjectposition = this.worldObj.rayTraceBlocks(vec3d, vec3d1);
            vec3d = new Vec3d(this.posX, this.posY, this.posZ);
            vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if(movingobjectposition != null)
            {
                vec3d1 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY,
                    this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d = 0.0D;

            for(int l = 0; l < list.size(); ++l)
            {
                Entity entity1 = (Entity) list.get(l);
                if(entity1.canBeCollidedWith())
                {
                    float f5 = 1.0F;
                    AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double) f5, (double) f5, (double) f5);
                    RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);
                    if(movingobjectposition1 != null)
                    {
                        double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
                        if(d1 < d || d == 0.0D)
                        {
                            entity = entity1;
                            d = d1;
                        }
                    }
                }
            }

            if(entity != null)
            {
                movingobjectposition = new RayTraceResult(entity);
            }

            if(movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityLiving &&
                    !(movingobjectposition.entityHit instanceof EntityPlayer))
            {
                movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), 5.0F);
                movingobjectposition.entityHit.setFire(20);
                this.setDead();
            } else
            {
                if(this.fuse-- <= 0)
                {
                    this.setDead();
                }

                double prevVelX = this.motionX;
                double prevVelY = this.motionY;
                double prevVelZ = this.motionZ;
                this.prevPosX = this.posX;
                this.prevPosY = this.posY;
                this.prevPosZ = this.posZ;
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                boolean collided = false;
                if(this.motionX != prevVelX)
                {
                    this.setDead();
                }

                if(this.motionZ != prevVelZ)
                {
                    this.setDead();
                }

                if(this.motionY != prevVelY)
                {
                    this.motionY = -prevVelY;
                    collided = true;
                } else
                {
                    this.motionY -= 0.1D;
                }

                if(collided)
                {
                    this.motionY = 0.5D;
                }
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Fuse", (byte) this.fuse);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.fuse = nbttagcompound.getByte("Fuse");
    }

    protected void entityInit()
    {
    }
}
