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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced)
    {
        super.addInformation(itemStack, player, list, advanced);

        list.add("Gives 55 seconds if invincibility and speed boost");
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
            list.add("POOOOOOWWWWWWWEEERRRRR");
        }
    }
}
