package io.github.Theray070696.mario2.block;

/**
 * Created by Theray070696 on 9/13/2017.
 */
public class BlockMarioSnow extends BlockMario
{
    public BlockMarioSnow()
    {
        super();
        this.setTranslationKey("marioBlockGroundSnow");
        this.setHardness(4.0f);
        this.setDefaultSlipperiness(0.9f);
    }
}
