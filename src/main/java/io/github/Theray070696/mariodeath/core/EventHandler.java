package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.capability.CoinCountProvider;
import io.github.Theray070696.mariodeath.entity.EntityGoomba;
import io.github.Theray070696.mariodeath.item.ModItems;
import io.github.Theray070696.mariodeath.potion.PotionEffectsMario;
import io.github.Theray070696.raycore.api.RayCoreAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2015.
 */
@SuppressWarnings("unused")
public class EventHandler
{
    // Yes, this is sloppy. No, I couldn't think of a better way to do it for now
    private List<String> playersToGiveRecordTo = new ArrayList<String>();

    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event)
    {
        if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayer = (EntityPlayerMP) event.getEntityLiving();

            if(entityPlayer.getStatFile().readStat(StatList.DEATHS) != 0 && entityPlayer.getStatFile().readStat(StatList.DEATHS) % 50 == 0)
            {
                entityPlayer.worldObj.playSound((EntityPlayer) null, entityPlayer.getPosition(), SoundHandler.fiftyDeaths, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return;
            }

            String playerName = entityPlayer.getName();
            Random rand = new Random();
            int rare = rand.nextInt(100);

            if(rare < 5)
            {
                entityPlayer.worldObj.playSound((EntityPlayer) null, entityPlayer.getPosition(), SoundHandler.rareDeath1, SoundCategory.PLAYERS, 1.0f, 1.0f);

                // Set the player up to get the record on respawn
                playersToGiveRecordTo.add(playerName);
                return;
            }

            int soundID;

            if(playerName.equalsIgnoreCase("JasterMK3"))
            {
                if(rare < 25)
                {
                    soundID = rand.nextInt(2) + 1;

                    SoundHandler.playSoundName("mariodeath:player.jasterDeath" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer.getPosition());
                    return;
                }
            } else if(playerName.equalsIgnoreCase("Theray070696"))
            {
                if(rare < 25)
                {
                    soundID = rand.nextInt(2) + 1;

                    SoundHandler.playSoundName("mariodeath:player.rayDeath" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer.getPosition());
                    return;
                }
            } else if(playerName.equalsIgnoreCase("Ghostkirby0319"))
            {
                if(rare < 25)
                {
                    soundID = /*rand.nextInt(2) +*/ 1;

                    SoundHandler.playSoundName("mariodeath:player.ghostDeath" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer.getPosition());
                    return;
                }
            }

            soundID = rand.nextInt(5) + 1;

            SoundHandler.playSoundName("mariodeath:player.death" + soundID, entityPlayer.worldObj, SoundCategory.PLAYERS, entityPlayer.getPosition());
        }
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        if(player.getName().equalsIgnoreCase("Theray070696"))
        {
            // Play Terry Crews "IT'S ME!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.rayJoin");
        } else if(player.getName().equalsIgnoreCase("JasterMK3"))
        {
            int randInt = new Random().nextInt(5) + 1;

            RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin" + randInt);
        } else
        {
            // Play Mario "It's a me!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.join");
        }

        player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(player);
    }

    @SubscribeEvent
    public void cloneEvent(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        event.getEntityPlayer().getCapability(CoinCountProvider.COIN_COUNT, null).setCoinCount(event.getOriginal().getCapability(CoinCountProvider.COIN_COUNT, null).getCoinCount());
        event.getEntityPlayer().getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.getEntityPlayer());
    }

    @SubscribeEvent
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event)
    {
        event.player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.player);

        // Check if the player should get the record
        if(playersToGiveRecordTo.contains(event.player.getName()))
        {
            // Remove them from the list
            playersToGiveRecordTo.remove(event.player.getName());

            // Spawn the item
            EntityItem entityItem = event.player.entityDropItem(new ItemStack(ModItems.itemRecordLOLUDied), 0.0f);

            // Pick it up immediately
            entityItem.setNoPickupDelay();
        }
    }

    @SubscribeEvent
    public void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        event.player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(event.player);
    }

    @SubscribeEvent
    public void playerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if(event.player.getName().equalsIgnoreCase("Theray070696"))
        {
            // Play Terry Crews "GOODBYE!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.rayLeave");
        } else if(event.player.getName().equalsIgnoreCase("JasterMK3"))
        {
            int randInt = new Random().nextInt(2) + 1;

            RayCoreAPI.playSoundToAll("mariodeath:player.jasterLeave" + randInt);
        } else
        {
            // Play Mario "See you next time!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.leave");
        }
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer && !(event.getEntityLiving() instanceof FakePlayer))
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            if(player.isPotionActive(PotionEffectsMario.potionStarman) || player.isPotionActive(PotionEffectsMario.potionSpiceman))
            {
                event.setCanceled(true);
                if(player.isPotionActive(PotionEffectsMario.potionSpiceman))
                {
                    // Play "BLOCK!" sound effect
                    int soundID = new Random().nextInt(12) + 1;

                    SoundHandler.playSoundName("mariodeath:player.spicemanBlock" + soundID, player.worldObj, SoundCategory.PLAYERS, player.getPosition());
                }
            }
        }
    }

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent event)
    {
        if(event.getName().getResourceDomain().equals("minecraft"))
        {
            if(event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON))
            {
                LootPool main = event.getTable().getPool("main");
                if(main != null)
                {
                    main.addEntry(new LootEntryItem(ModItems.itemRecordSMBUnderwater, 15, 1, new LootFunction[0], new LootCondition[0], "mariodeath:records.smbUnderwater"));
                    main.addEntry(new LootEntryItem(ModItems.itemRecordSuperSpiceBros, 15, 1, new LootFunction[0], new LootCondition[0], "mariodeath:records.spiceman"));
                    main.addEntry(new LootEntryItem(ModItems.itemRecordLOLUDied, 15, 1, new LootFunction[0], new LootCondition[0], "mariodeath:records.lolUDied"));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDropsEvent(LivingDropsEvent event)
    {
        if(event.getSource().getSourceOfDamage() instanceof EntityPlayer && !(event.getSource().getSourceOfDamage() instanceof FakePlayer))
        {
            Entity entity = event.getEntity();
            World world = entity.getEntityWorld();
            Random rand = new Random();

            if(entity instanceof EntityWither)
            {
                // Drop 0-2 Wither Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 4), 0.0f); // Wither Coin
            } else if(entity instanceof EntityDragon)
            {
                // Drop 4+ Dragon Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3) + 4, 3), 0.0f); // Dragon Coin
            } else if(entity instanceof EntityEnderman)
            {
                // Drop 1 Dragon Coin as a very rare drop.
                if(rand.nextInt(500) == 0)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 3), 0.0f); // Dragon Coin
                }
            } else if(entity instanceof EntitySkeleton && ((EntitySkeleton) entity).getSkeletonType() == SkeletonType.WITHER)
            {
                // Drop 1 Wither Coin as a very rare drop.
                if(rand.nextInt(500) == 0)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 4), 0.0f); // Wither Coin
                }
            } else if(entity instanceof EntityMob && !(entity instanceof EntityGoomba))
            {
                int randInt = rand.nextInt(100);

                if(randInt < 5)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(2)), 0.0f); // Green Coin
                } else if(randInt >= 5 && randInt < 20)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 1), 0.0f); // Blue Coin
                } else if(randInt >= 20 && randInt < 45)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(4), 2), 0.0f); // Red Coin
                } else if(randInt >= 45 && randInt < 90)
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemMarioCoin, rand.nextInt(4) + 1, rand.nextInt(3)), 0.0f); // Normal Coin
                }
            }
        }
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event)
    {
        if(event.getAchievement().equals(AchievementList.DIAMONDS) && !event.getEntityPlayer().hasAchievement(event.getAchievement()) && ((EntityPlayerMP) event.getEntityPlayer()).getStatFile().canUnlockAchievement(AchievementList.DIAMONDS))
        {
            RayCoreAPI.playSoundToAll("mariodeath:player.diamonds");
        }
    }
}