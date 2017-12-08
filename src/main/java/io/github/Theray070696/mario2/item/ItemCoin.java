package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.capability.ICoinCount;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class ItemCoin extends ItemMario
{
    public ItemCoin()
    {
        super();

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemCoin");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(int i = 0; i < 3; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer))
        {
            int meta = itemStack.getItemDamage();
            if(meta == 0 || meta == 2)
            {
                world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.smbCoin,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else if(meta == 1)
            {
                world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.smwCoin,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            }

            ICoinCount provider = player.getCapability(CoinCountProvider.COIN_COUNT, null);

            provider.addToCoinCount(1);
            provider.sync(player);

            itemStack.setCount(itemStack.getCount() - 1);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void registerItemModel(Item item)
    {
        RayCore.proxy.registerItemRenderer(this, 0, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_0");
        RayCore.proxy.registerItemRenderer(this, 1, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_1");
        RayCore.proxy.registerItemRenderer(this, 2, ModInfo.MOD_ID, getUnwrappedUnlocalizedName() + "_2");
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        super.addInformation(itemStack, player, tooltip, advanced);

        tooltip.add("Right click to add to your coin counter");
        tooltip.add("You can get coins out of your counter in the Mario Maker");
    }
}