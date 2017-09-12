package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.item.ItemRay;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ItemMario extends ItemRay
{
    public ItemMario()
    {
        this(true);
    }

    public ItemMario(boolean addToCreativeTab)
    {
        super(false, ModInfo.MOD_ID);

        if(addToCreativeTab)
        {
            this.setCreativeTab(MarioMod2.tabMarioItems);
        }
    }
}
