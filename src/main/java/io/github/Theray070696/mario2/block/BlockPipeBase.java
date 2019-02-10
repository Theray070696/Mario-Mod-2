package io.github.Theray070696.mario2.block;

import net.minecraft.block.material.Material;

/**
 * Created by Theray070696 on 2/9/2017.
 */
public class BlockPipeBase extends BlockMario
{
    public BlockPipeBase()
    {
        super(Material.IRON);

        this.setTranslationKey("marioBlockPipeBase");
        this.setHardness(1.5f);
    }
}
