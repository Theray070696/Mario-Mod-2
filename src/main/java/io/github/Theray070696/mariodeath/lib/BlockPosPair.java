package io.github.Theray070696.mariodeath.lib;

import net.minecraft.util.math.BlockPos;

/**
 * Created by Theray070696 on 4/27/2017.
 */
public class BlockPosPair
{
    private BlockPos pos1;
    private BlockPos pos2;

    private int dim1;
    private int dim2;

    public BlockPosPair(BlockPos pos1, int dim1, BlockPos pos2, int dim2)
    {
        this.pos1 = pos1;
        this.pos2 = pos2;

        this.dim1 = dim1;
        this.dim2 = dim2;
    }

    public BlockPos getPos1()
    {
        return this.pos1;
    }

    public int getDim1()
    {
        return this.dim1;
    }

    public BlockPos getPos2()
    {
        return this.pos2;
    }

    public int getDim2()
    {
        return this.dim2;
    }
}
