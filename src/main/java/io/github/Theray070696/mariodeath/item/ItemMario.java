package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.raycore.item.ItemRay;

/**
 * Created by Theray on 1/22/2017.
 */
public class ItemMario extends ItemRay
{
    public ItemMario()
    {
        this(true);
    }
    
    public ItemMario(boolean addToCreativeTab)
    {
        super(addToCreativeTab, ModInfo.MOD_ID);
    }
}
