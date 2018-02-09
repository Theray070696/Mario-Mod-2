package io.github.Theray070696.mario2.item;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.entity.EntityFireball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class ItemFireFlower extends ItemMario
{
    public ItemFireFlower()
    {
        super();

        this.setUnlocalizedName("itemFireFlower");
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer))
        {
            // Shoot a fireball
            world.spawnEntity(new EntityFireball(world, player.posX, player.posY + 1.5D, player.posZ, player.rotationYaw, 1.0F, 0.5D, 160));

            world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.fireball,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);

            itemStack.damageItem(1, player);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}