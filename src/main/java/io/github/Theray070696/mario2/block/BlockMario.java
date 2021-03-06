package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.block.BlockRay;
import net.minecraft.block.material.Material;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class BlockMario extends BlockRay
{
    private boolean isGround = false;

    public BlockMario()
    {
        this(true);
    }

    public BlockMario(boolean addToCreativeTab)
    {
        this(Material.ROCK, addToCreativeTab, false);
    }

    public BlockMario(Material material)
    {
        this(material, true);
    }

    public BlockMario(Material material, boolean addToCreativeTab)
    {
        this(material, addToCreativeTab, false);
    }

    public BlockMario(Material material, boolean addToCreativeTab, boolean isGround)
    {
        super(material, false, ModInfo.MOD_ID);

        if(addToCreativeTab)
        {
            this.setCreativeTab(MarioMod2.tabMarioBlocks);
        }

        this.isGround = isGround;
    }

    public boolean isGround()
    {
        return this.isGround;
    }
}
