package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray on 8/27/2015.
 */
public class BlockQuestionMark extends BlockQuestionMarkBase implements SMWQBlock
{
    public BlockQuestionMark()
    {
        super(false);

        this.setBlockName("marioBlockQuestionMark");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


}