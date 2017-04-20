package io.github.Theray070696.mariodeath.item;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.potion.PotionEffectsMario;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 12/19/2016.
 */
public class ItemSpiceMan extends ItemMario
{
    public ItemSpiceMan()
    {
        super();

        this.setUnlocalizedName("itemSpiceMan");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nullable ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(itemStack != null && world != null && player != null && !(player instanceof FakePlayer))
        {
            player.addPotionEffect(new PotionEffect(PotionEffectsMario.potionSpiceman, 1120, 10));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1120, 5));
            world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.spiceMan, SoundCategory.PLAYERS, 1.0F, 1.0F);
            itemStack.stackSize--;
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
