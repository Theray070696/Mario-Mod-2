package io.github.Theray070696.mariodeath.entity;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.item.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Random;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class EntityKoopa extends EntityMob
{
    public EntityKoopa(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected SoundEvent getDeathSound()
    {
        return SoundHandler.goombaDeath;
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if(!this.worldObj.isRemote)
        {
            if(!(cause.getEntity() instanceof FakePlayer))
            {
                Random rand = new Random();
                int amount = rand.nextInt(3);
                if(amount > 0)
                {
                    this.entityDropItem(new ItemStack(ModItems.itemMarioCoin, rand.nextInt(3), rand.nextInt(3)), 0.0F);
                }
            } else
            {
                Random rand = new Random();
                if(rand.nextInt(1000) == 0)
                {
                    int amount = rand.nextInt(3);
                    if(amount > 0)
                    {
                        this.entityDropItem(new ItemStack(ModItems.itemMarioCoin, rand.nextInt(3), rand.nextInt(3)), 0.0F);
                    }
                }
            }
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.getBlockPathWeight(new BlockPos(this.posX, this
                .getEntityBoundingBox().minY, this.posZ)) >= 0.0F && this.worldObj.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this);
    }
}
