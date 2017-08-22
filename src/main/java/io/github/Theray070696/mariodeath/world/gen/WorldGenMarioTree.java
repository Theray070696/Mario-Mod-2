package io.github.Theray070696.mariodeath.world.gen;

import io.github.Theray070696.mariodeath.block.BlockMario;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class WorldGenMarioTree extends WorldGenerator
{

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        int height = rand.nextInt(3) + 3;
        boolean hasSpace = true;

        if(pos.getY() >= 1 && pos.getY() + height + 1 <= 256)
        {
            int radius;

            for(int y = pos.getY(); y <= pos.getY() + 1 + height; y++)
            {
                radius = 1;

                if(y == pos.getY())
                {
                    radius = 0;
                }

                if(y >= pos.getY() + 1 + height - 2)
                {
                    radius = 2;
                }

                for(int x = pos.getX() - radius; x <= pos.getX() + radius && hasSpace; ++x)
                {
                    for(int z = pos.getZ() - radius; z <= pos.getZ() + radius && hasSpace; ++z)
                    {
                        if(y >= 0 && y < 256)
                        {
                            if(!world.getBlockState(new BlockPos(x, y, z)).getMaterial().equals(Material.AIR) || !world.getBlockState(new BlockPos
                                    (x, y, z)).getMaterial().equals(Material.LEAVES))
                            {
                                hasSpace = false;
                            }
                        } else
                        {
                            hasSpace = false;
                        }
                    }
                }
            }

            if(!hasSpace)
            {
                return false;
            } else
            {
                BlockPos groundPos = pos.down();
                Block ground = world.getBlockState(groundPos).getBlock();

                if(ground instanceof BlockMario)
                {
                    boolean isGround = ((BlockMario) ground).isGround();

                    if(isGround && pos.getY() < 256 - height - 1)
                    {
                        ground.onPlantGrow(world.getBlockState(groundPos), world, groundPos, pos);

                        for(int y = pos.getY() + height - 3; y <= pos.getY() + height; y++)
                        {
                            int currentLayer = y - (pos.getY() + height);
                            int leavesRadius = 3 - currentLayer / 2;

                            for(int x = pos.getX() - leavesRadius; x <= pos.getX() + leavesRadius; x++)
                            {
                                int xDiff = x - pos.getX();

                                for(int z = pos.getZ() - leavesRadius; z <= pos.getZ() + leavesRadius; ++z)
                                {
                                    int zDiff = z - pos.getZ();

                                    if(Math.abs(xDiff) != leavesRadius || Math.abs(zDiff) != leavesRadius || rand.nextInt(2) != 0 && currentLayer
                                            != 0)
                                    {
                                        BlockPos leavesPos = new BlockPos(x, y, z);
                                        if(world.getBlockState(leavesPos).getMaterial().equals(Material.AIR) || world.getBlockState(leavesPos)
                                                .getMaterial().equals(Material.LEAVES))
                                        {
                                            this.setBlockAndNotifyAdequately(world, leavesPos, ModBlocks.blockMarioLeaves.getDefaultState());
                                        }
                                    }
                                }
                            }
                        }

                        for(int layer = 0; layer < height; ++layer)
                        {
                            BlockPos pos2 = pos.up(layer);
                            if(world.getBlockState(pos2).getMaterial().equals(Material.AIR) || world.getBlockState(pos2).getMaterial().equals
                                    (Material.LEAVES))
                            {
                                this.setBlockAndNotifyAdequately(world, pos.up(layer), ModBlocks.blockMarioLog.getDefaultState());
                            }
                        }

                        return true;
                    }
                } else
                {
                    return false;
                }
            }
        } else
        {
            return false;
        }

        return false;
    }
}