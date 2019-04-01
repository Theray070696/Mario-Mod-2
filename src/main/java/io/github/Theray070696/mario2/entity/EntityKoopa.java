package io.github.Theray070696.mario2.entity;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.item.ModItems;
import io.github.Theray070696.mario2.world.provider.WorldProviderMario;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
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
    public EntityKoopa(World world)
    {
        super(world);
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
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
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
        if(!this.world.isRemote)
        {
            if(cause.getTrueSource() instanceof EntityPlayer)
            {
                if(!(cause.getTrueSource() instanceof FakePlayer))
                {
                    Random rand = new Random();
                    int amount = rand.nextInt(3);
                    if(amount > 0)
                    {
                        this.entityDropItem(new ItemStack(ModItems.itemCoin, amount, rand.nextInt(3)), 0.0F);
                    }
                } else
                {
                    Random rand = new Random();
                    if(rand.nextInt(1000) == 0)
                    {
                        int amount = rand.nextInt(3);
                        if(amount > 0)
                        {
                            this.entityDropItem(new ItemStack(ModItems.itemCoin, amount, rand.nextInt(3)), 0.0F);
                        }
                    }
                }
            } else if((cause.getTrueSource() instanceof EntityTameable && ((EntityTameable) cause.getTrueSource()).isTamed()))
            {
                Random rand = new Random();
                if(rand.nextInt(100) == 0)
                {
                    int amount = rand.nextInt(3);
                    if(amount > 0)
                    {
                        this.entityDropItem(new ItemStack(ModItems.itemCoin, amount, rand.nextInt(3)), 0.0F);
                    }
                }
            }
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if(this.world.provider instanceof WorldProviderMario)
        {
            return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.getBlockPathWeight(new BlockPos(this.posX, this
                    .getEntityBoundingBox().minY, this.posZ)) >= 0.0F && this.world.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this);
        } else
        {
            return super.getCanSpawnHere();
        }
    }
}
