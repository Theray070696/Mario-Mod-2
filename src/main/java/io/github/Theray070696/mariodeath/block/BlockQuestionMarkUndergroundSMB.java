package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray on 1/5/2017.
 */
public class BlockQuestionMarkUndergroundSMB extends BlockQuestionMarkBase implements SMBQBlock
{
    public BlockQuestionMarkUndergroundSMB()
    {
        super(false);

        this.setBlockName("marioBlockQuestionMarkUndergroundSMB");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


}
