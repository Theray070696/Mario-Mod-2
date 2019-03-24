package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.core.EventHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 12/19/2016.
 */
public class BlockNoteBlock extends BlockMario
{
    public BlockNoteBlock()
    {
        super(Material.WOOD);

        this.setSoundType(SoundType.WOOD);

        this.setTranslationKey("marioBlockNoteBlock");
    }

    @Override
    public void onLanded(World world, Entity entity)
    {
        if(entity instanceof EntityXPOrb || entity instanceof EntityItem)
        {
            super.onLanded(world, entity);
            return;
        }

        entity.fallDistance = 0f;

        if(entity.motionY < 0f && !entity.isSneaking())
        {
            if(entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLiving = (EntityLivingBase) entity;
                if(EventHandler.getSoundCooldown(entityLiving) == 0)
                {
                    world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    EventHandler.setSoundCooldown(entityLiving, 2);
                }
            } else
            {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

            entity.motionY *= -2.0d;
        } else if(entity.isSneaking())
        {
            super.onLanded(world, entity);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
    {
        fallDistance = 0f;
        entity.fallDistance = 0f;

        if(entity instanceof EntityXPOrb)
        {
            super.onFallenUpon(world, pos, entity, fallDistance);
            return;
        }

        if(entity.motionY < 0f && !entity.isSneaking())
        {
            if(entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLiving = (EntityLivingBase) entity;
                if(EventHandler.getSoundCooldown(entityLiving) == 0)
                {
                    world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    EventHandler.setSoundCooldown(entityLiving, 2);
                }
            } else
            {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

            entity.motionY *= -2.0d;
        } else if(entity.isSneaking())
        {
            super.onFallenUpon(world, pos, entity, fallDistance);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos blockPos)
    {
        return new AxisAlignedBB(0d, 0d, 0d, 1.0d, 0.625d, 1.0d);
    }
}
