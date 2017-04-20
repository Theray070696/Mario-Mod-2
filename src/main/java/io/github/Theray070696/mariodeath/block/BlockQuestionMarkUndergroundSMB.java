package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray070696 on 1/5/2017.
 */
public class BlockQuestionMarkUndergroundSMB extends BlockQuestionMarkBase implements SMBQBlock, IFilledQBlock
{
    public BlockQuestionMarkUndergroundSMB()
    {
        super(false);

        this.setUnlocalizedName("marioBlockQuestionMarkUndergroundSMB");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
