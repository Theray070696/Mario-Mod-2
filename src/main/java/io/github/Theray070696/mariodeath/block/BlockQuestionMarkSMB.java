package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray on 1/5/2017.
 */
public class BlockQuestionMarkSMB extends BlockQuestionMarkBase implements SMBQBlock
{
    public BlockQuestionMarkSMB()
    {
        super(false);

        this.setBlockName("marioBlockQuestionMarkSMB");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
