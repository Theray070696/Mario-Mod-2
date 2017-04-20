package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray070696 on 1/5/2017.
 */
public class BlockQuestionMarkSMB extends BlockQuestionMarkBase implements SMBQBlock, IFilledQBlock
{
    public BlockQuestionMarkSMB()
    {
        super(false);

        this.setUnlocalizedName("marioBlockQuestionMarkSMB");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
