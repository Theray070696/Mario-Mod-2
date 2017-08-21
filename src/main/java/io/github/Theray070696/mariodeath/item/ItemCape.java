package io.github.Theray070696.mariodeath.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.core.EventHandler;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class ItemCape extends ItemMario implements IBauble
{
    public ItemCape()
    {
        super();

        this.setUnlocalizedName("itemCape");
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
                    ItemStack itemStack = player.inventory.mainInventory[i]; // Get the item in this hotbar slot.

                    if(itemStack != null && itemStack.isItemEqual(stack)) // If it's not null and is a cape...
                    {
                        player.fallDistance = 0.0F; // Cancel fall damage.
                        return; // Exit out of the function.
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nullable ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && itemStack != null && player != null && !(player instanceof FakePlayer)) // If we're on the server side, the ItemStack
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
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced)
    {
        super.addInformation(itemStack, player, list, advanced);

        list.add("Negates fall damage"); // Add helpful tooltip.
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player)
    {
        if(itemStack != null && player != null && !(player instanceof FakePlayer) && player.motionY < 0.0f) // If the ItemStack is not null, the
        // player is not null, and the player is not a fake player...
        {
            player.fallDistance = 0.0F; // Cancel fall damage.
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
}