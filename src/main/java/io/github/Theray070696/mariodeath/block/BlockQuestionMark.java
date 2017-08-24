package io.github.Theray070696.mariodeath.block;

import net.minecraft.block.material.Material;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class BlockQuestionMark extends BlockQuestionMarkBase
{
    public BlockQuestionMark(String name, EnumBlockType blockType)
    {
        this(Material.ROCK, name, blockType);
    }

    public BlockQuestionMark(Material material, String name, EnumBlockType blockType)
    {
        super(material, false, blockType);

        this.setUnlocalizedName(name);
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


}