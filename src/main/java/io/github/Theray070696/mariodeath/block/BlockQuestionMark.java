package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class BlockQuestionMark extends BlockQuestionMarkBase implements SMWQBlock, IFilledQBlock
{
    public BlockQuestionMark()
    {
        super(false);

        this.setUnlocalizedName("marioBlockQuestionMark");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


}