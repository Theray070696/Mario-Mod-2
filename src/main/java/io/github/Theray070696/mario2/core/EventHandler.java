package io.github.Theray070696.mario2.core;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.entity.EntityGoomba;
import io.github.Theray070696.mario2.entity.EntityKoopa;
import io.github.Theray070696.mario2.event.PlayMarioSoundEvent;
import io.github.Theray070696.mario2.item.ModItems;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.potion.PotionEffectsMario;
import io.github.Theray070696.raycore.api.RayCoreAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2015.
 */
@SuppressWarnings("unused")
public class EventHandler
{
    private static Map<Integer, Integer> soundCooldown = new HashMap<>();

    public static int getSoundCooldown(EntityLivingBase entity)
    {
        if(soundCooldown.containsKey(entity.getEntityId()))
        {
            return soundCooldown.get(entity.getEntityId());
        } else
        {
            soundCooldown.put(entity.getEntityId(), 0);
            return 0;
        }
    }

    public static void setSoundCooldown(EntityLivingBase entity, int cooldown)
    {
        soundCooldown.put(entity.getEntityId(), cooldown);
    }

    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event)
    {
        if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) // If the entity that was killed was a player...
        {
            if(!MinecraftForge.EVENT_BUS.post(new PlayMarioSoundEvent((EntityPlayer) event.getEntityLiving(), PlayMarioSoundEvent.SoundType
                    .DEATH_SOUND)) && ConfigHandler.enableDeathSounds)
            {
                Random rand = new Random();

                SoundHandler.playSoundName("mario2:player.death" + (rand.nextInt(4) + 1), event.getEntityLiving().world, SoundCategory.PLAYERS,
                        event.getEntityLiving().getPosition());
            }
        }
    }

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        if(!MinecraftForge.EVENT_BUS.post(new PlayMarioSoundEvent(player, PlayMarioSoundEvent.SoundType.JOIN_SOUND)) && ConfigHandler
                .enableJoinLeaveSounds)
        {
            RayCoreAPI.playSoundToAll("mario2:player.join");
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
        if(!MinecraftForge.EVENT_BUS.post(new PlayMarioSoundEvent(event.player, PlayMarioSoundEvent.SoundType.LEAVE_SOUND)) && ConfigHandler
                .enableJoinLeaveSounds)
        {
            RayCoreAPI.playSoundToAll("mario2:player.leave");
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
    public void onEntityDamaged(LivingHurtEvent event)
    {
        if(event.getEntityLiving().isPotionActive(PotionEffectsMario.potionStarman)) // If the entity that was hurt has the Starman potion effect
        // active...
        {
            event.setCanceled(true); // Starman makes you literally immortal. Not even the Chaos Dragon from Draconic Evolution can hurt you.
        }
    }

    @SubscribeEvent
    public void onLivingDropsEvent(LivingDropsEvent event)
    {
        if(!event.getEntity().world.getGameRules().getBoolean("doMobLoot"))
        {
            return;
        }

        if((event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer)) || (event
                .getSource().getTrueSource() instanceof EntityTameable && ((EntityTameable) event.getSource().getTrueSource()).isTamed())) // If
            // the cause of damage was a player, but NOT a fake player, OR the cause was a tameable entity AND that entity was tamed...
        {
            Entity entity = event.getEntity(); // Get entity that is dropping item(s).
            World world = entity.getEntityWorld(); // Get the world the entity is in.
            Random rand = new Random(); // Initialize random number generator.

            if(entity instanceof EntityWither && ConfigHandler.enableCoinDrops) // If the entity was a Wither...
            {
                // Drop 0-2 Wither Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 4), 0.0f); // Wither Coin
            } else if(entity instanceof EntityDragon && ConfigHandler.enableCoinDrops) // If the entity was an Ender Dragon...
            {
                // Drop 4+ Dragon Coins.
                entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3) + 4, 3), 0.0f); // Dragon Coin
            } else if(entity instanceof EntityEnderman && ConfigHandler.enableCoinDrops) // If the entity was an Enderman..
            {
                if(rand.nextInt(500) == 0) // Drop 1 Dragon Coin as a very rare drop.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 3), 0.0f); // Dragon Coin
                }
            } else if(entity instanceof EntityWitherSkeleton && ConfigHandler.enableCoinDrops) // If the entity was a Wither Skeleton...
            {
                if(rand.nextInt(500) == 0) // Drop 1 Wither Coin as a very rare drop.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, 1, 4), 0.0f); // Wither Coin
                }
            } else if(entity instanceof EntityMob && !(entity instanceof EntityGoomba || entity instanceof EntityKoopa)) // If the entity is any
            // other mob besides a Goomba...
            {
                int randInt = rand.nextInt(100); // Generate a number from 0 to 99 inclusive to determine what will be dropped.

                if(randInt < 5 && ConfigHandler.enableCoinDrops) // 5% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(2)), 0.0f); // Green Coin
                } else if(randInt >= 5 && randInt < 20 && ConfigHandler.enableCoinDrops) // 15% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(3), 1), 0.0f); // Blue Coin
                } else if(randInt >= 20 && randInt < 45 && ConfigHandler.enableCoinDrops) // 25% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoinCurrency, rand.nextInt(4), 2), 0.0f); // Red Coin
                } else if(randInt >= 45 && randInt < 90) // 45% chance.
                {
                    entity.entityDropItem(new ItemStack(ModItems.itemCoin, rand.nextInt(4) + 1, rand.nextInt(3)), 0.0f); // Normal Coin
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if(!entity.world.isRemote)
        {
            if(getSoundCooldown(entity) > 0)
            {
                setSoundCooldown(entity, getSoundCooldown(entity) - 1);
            }
        }

        if(entity.world.isRemote)
        {
            if(entity.isPotionActive(PotionEffectsMario.potionStarman))
            {
                Random rand = new Random();
                entity.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, entity.posX - 0.1D + rand.nextGaussian() * 0.2D, entity.posY + 0.5D -
                        rand.nextGaussian() * 0.2D, entity.posZ - 0.1D + rand.nextGaussian() * 0.2D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}