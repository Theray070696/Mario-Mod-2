package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.raycore.block.BlockRay;

/**
 * Created by Theray on 1/22/2017.
 */
public class BlockMario extends BlockRay
{
    public BlockMario()
    {
        this(true);
    }
    
    public BlockMario(boolean addToCreativeTab)
    {
        super(addToCreativeTab, ModInfo.MOD_ID);
    }
}
