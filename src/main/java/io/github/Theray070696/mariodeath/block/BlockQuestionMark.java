package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class BlockQuestionMark extends BlockQuestionMarkBase
{
    public BlockQuestionMark(String name, EnumBlockType blockType)
    {
        super(false, blockType);

        //this.setUnlocalizedName("marioBlockQuestionMark");
        this.setUnlocalizedName(name);
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


}