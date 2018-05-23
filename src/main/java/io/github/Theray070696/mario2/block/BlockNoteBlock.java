package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.core.EventHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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

        this.setUnlocalizedName("marioBlockNoteBlock");
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        if(entity.motionY < 0 && !entity.isSneaking())
        {
            if(entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLiving = (EntityLivingBase) entity;
                if(EventHandler.getSoundCooldown(entityLiving) == 0)
                {
                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0F,
                            1.0F);
                    EventHandler.setSoundCooldown(entityLiving, 2);
                }
            } else
            {
                world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            entity.motionY *= -2.0D;
        }
        entity.fallDistance = 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos blockPos)
    {
        return new AxisAlignedBB(0, 0, 0, 1.0D, 0.625D, 1.0D);
    }
}
