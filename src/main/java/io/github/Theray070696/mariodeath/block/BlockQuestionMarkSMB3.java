package io.github.Theray070696.mariodeath.block;

/**
 * Created by Theray070696 on 1/5/2017.
 */
public class BlockQuestionMarkSMB3 extends BlockQuestionMarkBase implements SMB3QBlock, IFilledQBlock
{
    public BlockQuestionMarkSMB3()
    {
        super(false);

        this.setUnlocalizedName("marioBlockQuestionMarkSMB3");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
