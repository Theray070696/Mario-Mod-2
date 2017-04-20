package io.github.Theray070696.mariodeath.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Theray on 12/19/2016.
 */
public class BlockNoteBlock extends BlockMario
{
    public BlockNoteBlock()
    {
        super();

        this.setBlockName("marioBlockNoteBlock");
    }

    @Override
    public void onEntityCollidedWithBlock (World world, int x, int y, int z, Entity entity)
    {
        if(entity.motionY < 0)
        {
            world.playSoundAtEntity(entity, "mariodeath:block.note", 1.0F, 1.0F);

            entity.motionY *= -2.0F;
        }
        entity.fallDistance = 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0D, (double) y + 0.625D, (double) z + 1.0D);
    }

    @Override
    public boolean renderAsNormalBlock() { return false; }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 0;
    }
}
