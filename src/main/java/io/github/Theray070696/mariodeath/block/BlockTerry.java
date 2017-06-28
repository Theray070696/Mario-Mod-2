package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class BlockTerry extends BlockMario
{
    public BlockTerry()
    {
        super(Material.IRON);

        this.setUnlocalizedName("marioBlockTerryBlock");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            int randSound = new Random().nextInt(9) + 1;

            SoundHandler.playSoundName("mario2:block.terry" + randSound, world, SoundCategory.BLOCKS, blockPos);
        }

        return true;
    }
}
