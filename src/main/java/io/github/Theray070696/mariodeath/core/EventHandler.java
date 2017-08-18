package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.capability.CoinCountProvider;
import io.github.Theray070696.mariodeath.entity.EntityGoomba;
import io.github.Theray070696.mariodeath.entity.EntityKoopa;
import io.github.Theray070696.mariodeath.item.ModItems;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import io.github.Theray070696.mariodeath.potion.PotionEffectsMario;
import io.github.Theray070696.raycore.api.RayCoreAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2015.
 */
@SuppressWarnings("unused")
public class EventHandler
{
    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event)
    {
        if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayerMP) // If the entity that was killed was a player...
        {
            EntityPlayerMP entityPlayer = (EntityPlayerMP) event.getEntityLiving(); // Get the player entity.

            String playerName = entityPlayer.getName(); // Get player display name.
            Random rand = new Random(); // Initialize random number generator.
            int rare = rand.nextInt(100); // Generate a number from 0 to 99 inclusive. Used for rare sounds.

            int soundID; // Create sound ID variable.

            if(playerName.equalsIgnoreCase("JasterMK3")) // If it was a good friend of mine...
            {
                if(rare < 25) // 25% chance of playing.
                {
                    soundID = rand.nextInt(2) + 1; // Get a random number for a sound.

                    SoundHandler.playSoundName("mario2:player.jasterDeath" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer
                            .getPosition());
                    return; // Exit out this function, as we don't want multiple sounds playing at once.
                }
            } else if(playerName.equalsIgnoreCase("Theray070696")) // If it was me...
            {
                if(rare < 25) // 25% chance of playing.
                {
                    soundID = rand.nextInt(5) + 1; // Get a random number for a sound.

                    SoundHandler.playSoundName("mario2:player.rayDeath" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer
                            .getPosition());
                    return; // Exit out this function, as we don't want multiple sounds playing at once.
                }
            }

            soundID = rand.nextInt(4) + 1; // Get a random number for a sound.

            SoundHandler.playSoundName("mario2:player.death" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer.getPosition());
        }
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayerMP player = (EntityPlayerMP) event.player; // Get the player that joined.

        if(player.getName().equalsIgnoreCase("Theray070696")) // If it was me...
        {
            RayCoreAPI.playSoundToAll("mario2:player.rayJoin"); // Play Terry Crews "IT'S ME!" sound to everyone.
        } else if(player.getName().equalsIgnoreCase("JasterMK3")) // If it was a good friend of mine...
        {
            int randInt = new Random().nextInt(5) + 1; // Get a random number for a sound.

            RayCoreAPI.playSoundToAll("mario2:player.jasterJoin" + randInt); // Use that random number to pick a sound.
        } else // If it was a normal user...
        {
            RayCoreAPI.playSoundToAll("mario2:player.join"); // Play Mario "It's a me!" sound to everyone.
        }

        player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(player); // Resync coin counter.
    }

    @SubscribeEvent
    public void cloneEvent(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        event.getEntityPlayer().getCapability(CoinCountProvider.COIN_COUNT, null).setCoinCount(event.getOriginal().getCapability(CoinCountProvider
                .COIN_COUNT, null).getCoinCount());
        event.getEntityPlayer().getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.getEntityPlayer()); // Resync coin counter.
    }

    @SubscribeEvent
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event)
    {
        event.player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.player); // Resync coin counter.
    }

    @SubscribeEvent
    public void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        event.player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.player); // Resync coin counter.
    }

    @SubscribeEvent
    public void playerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if(event.player.getName().equalsIgnoreCase("Theray070696")) // If it was me...
        {
            RayCoreAPI.playSoundToAll("mario2:player.rayLeave"); // Play Terry Crews "GOODBYE!" sound to everyone.
        } else if(event.player.getName().equalsIgnoreCase("JasterMK3")) // If it was a good friend of mine...
        {
            int randInt = new Random().nextInt(2) + 1; // Get a random number for a sound.

            RayCoreAPI.playSoundToAll("mario2:player.jasterLeave" + randInt); // Use that random number to pick a sound.
        } else // If it was a normal user...
        {
            RayCoreAPI.playSoundToAll("mario2:player.leave"); // Play Mario "See you next time!" sound to everyone.
        }
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event)
    {
        if(event.getEntityLiving().isPotionActive(PotionEffectsMario.potionStarman)) // If the entity that was hurt has the Starman potion effect
            // active...
        {
            event.setCanceled(true); // Starman makes you literally immortal. Not even the Chaos Dragon from Draconic Evolution can hurt you.
        }
    }

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent event)
    {
        if(event.getName().toString().equals("minecraft:chests/simple_dungeon"))
        {
            // To test this, use the command on the next line.
            // /setblock ~ ~1 ~ minecraft:chest 2 replace {LootTable:"chests/simple_dungeon"}
            // It will place a chest above the command block with randomly generated dungeon chest loot in it.

            LootEntry entry = new LootEntryTable(new ResourceLocation(ModInfo.MOD_ID, "inject/disks"), 15, 1, new LootCondition[0],
                    "mario2_inject_entry");

            LootPool pool = new LootPool(new LootEntry[]{entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1),
                    "mario2_inject_entry");

            event.getTable().addPool(pool);
        }
    }

    @SubscribeEvent
    public void onLivingDropsEvent(LivingDropsEvent event)
    {
        if(event.getSource().getSourceOfDamage() instanceof EntityPlayer && !(event.getSource().getSourceOfDamage() instanceof FakePlayer)) // If
            // the cause of damage was a player, but NOT a fake player...
        {
            Entity entity = event.getEntity(); // Get entity that is dropping item(s).
            World world = entity.getEntityWorld(); // Get the world the entity is in.
            Random rand = new Random(); // Initialize random number generator.

            if(entity instanceof EntityWither) // If the entity was a Wither...
            {
                // Drop 0-2 Wither Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 4), 0.0f); // Wither Coin
            } else if(entity instanceof EntityDragon) // If the entity was an Ender Dragon...
            {
                // Drop 4+ Dragon Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3) + 4, 3), 0.0f); // Dragon Coin
            } else if(entity instanceof EntityEnderman) // If the entity was an Enderman..
            {
                if(rand.nextInt(500) == 0) // Drop 1 Dragon Coin as a very rare drop.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 3), 0.0f); // Dragon Coin
                }
            } else if(entity instanceof EntitySkeleton && ((EntitySkeleton) entity).getSkeletonType() == SkeletonType.WITHER) // If the entity was
                // a Wither Skeleton...
            {
                if(rand.nextInt(500) == 0) // Drop 1 Wither Coin as a very rare drop.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 4), 0.0f); // Wither Coin
                }
            } else if(entity instanceof EntityMob && !(entity instanceof EntityGoomba || entity instanceof EntityKoopa)) // If the entity is any
                // other mob besides a Goomba...
            {
                int randInt = rand.nextInt(100); // Generate a number from 0 to 99 inclusive to determine what will be dropped.

                if(randInt < 5) // 5% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(2)), 0.0f); // Green Coin
                } else if(randInt >= 5 && randInt < 20) // 15% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 1), 0.0f); // Blue Coin
                } else if(randInt >= 20 && randInt < 45) // 25% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(4), 2), 0.0f); // Red Coin
                } else if(randInt >= 45 && randInt < 90) // 45% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemMarioCoin, rand.nextInt(4) + 1, rand.nextInt(3)), 0.0f); // Normal Coin
                }
            }
        }
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event)
    {
        // If the player would get the DIAMONDS! achievement, and the can unlock it, and they don't already have it...
        if(event.getAchievement().equals(AchievementList.DIAMONDS) && !event.getEntityPlayer().hasAchievement(event.getAchievement()) && (
                (EntityPlayerMP) event.getEntityPlayer()).getStatFile().canUnlockAchievement(AchievementList.DIAMONDS))
        {
            RayCoreAPI.playSoundToAll("mario2:player.diamonds"); // Play a sound to everybody on the server.
        }
    }
}