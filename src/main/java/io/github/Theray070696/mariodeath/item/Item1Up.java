package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.raycore.RayCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class Item1Up extends ItemMario
{
    public Item1Up()
    {
        super();

        this.setHasSubtypes(true);
        this.setUnlocalizedName("item1Up");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < 3; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nullable ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            if(player.getHealth() < player.getMaxHealth())
            {
                if(!world.getWorldInfo().isHardcoreModeEnabled())
                {
                    if(player.getHealth() + 10F >= player.getMaxHealth())
                    {
                        player.setHealth(player.getMaxHealth());
                    } else
                    {
                        player.setHealth(player.getHealth() + 10F);
                    }
                } else
                {
                    if(player.getHealth() + 20F >= player.getMaxHealth())
                    {
                        player.setHealth(player.getMaxHealth());
                    } else
                    {
                        player.setHealth(player.getHealth() + 20F);
                    }
                }

                int meta = itemStack.getItemDamage();
                if(meta == 0 || meta == 2)
                {
                    world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.oneUpSMB, SoundCategory.PLAYERS, 1.0F, 1.0F);
                } else if(meta == 1)
                {
                    world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.oneUp, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }

                itemStack.stackSize--;
            }
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced)
    {
        super.addInformation(itemStack, player, list, advanced);

        if(!player.getEntityWorld().getWorldInfo().isHardcoreModeEnabled())
        {
            list.add("Restores 5 hearts"); // Add a helpful tooltip.
        } else
        {
            list.add("Restores 10 hearts"); // Add a helpful tooltip.
        }
    }
}