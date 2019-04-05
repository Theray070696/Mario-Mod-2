package io.github.Theray070696.mario2.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.core.EventHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Theray070696 on 9/15/2015.
 */
@Optional.InterfaceList(value =
        {
                @Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
        })
public class ItemCape extends ItemMario implements IBauble
{
    public ItemCape()
    {
        super();

        this.setTranslationKey("itemCape");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(!world.isRemote) // If we're on the server side...
        {
            if(entity != null && entity instanceof EntityPlayer && !(entity instanceof FakePlayer) && entity.motionY < 0.0f) // If the entity is
            // not null, is a player, is not a fake player, and is falling...
            {
                EntityPlayer player = (EntityPlayer) entity; // Save the player entity.

                for(int i = 0; i < 9; i++) // Loop from zero to nine.
                {
                    ItemStack itemStack = player.inventory.mainInventory.get(i); // Get the item in this hotbar slot.

                    if(!itemStack.isEmpty() && itemStack.isItemEqual(stack)) // If it's not null and is a cape...
                    {
                        player.fallDistance = 0.0F; // Cancel fall damage.
                        return; // Exit out of the function.
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if(!world.isRemote && !itemStack.isEmpty() && !(player instanceof FakePlayer)) // If we're on the server side, the ItemStack
        // is not null, the player is not null, and the player is not a fake player...
        {
            player.fallDistance = 0.0F; // Cancel fall damage.

            if(EventHandler.getSoundCooldown(player) == 0)
            {
                world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundHandler.cape,
                        SoundCategory.PLAYERS, 1.0F, 1.0F); // Play a sound.
                EventHandler.setSoundCooldown(player, 21); // Don't spam sounds.
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(itemStack, player, tooltip, advanced);

        tooltip.add("Negates fall damage"); // Add helpful tooltip.
    }

    @Override
    @Optional.Method(modid = "baubles")
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return BaubleType.BELT;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack itemStack, EntityLivingBase player)
    {
        if(!itemStack.isEmpty() && player != null && !(player instanceof FakePlayer) && player.motionY < 0.0f) // If the ItemStack is not
            // empty, the player is not null, and the player is not a fake player...
        {
            player.fallDistance = 0.0F; // Cancel fall damage.
        }
    }
}