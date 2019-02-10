package io.github.Theray070696.mario2.audio;

import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Created by Theray070696 on 3/23/2017.
 */
public class SoundHandler
{
    public static SoundEvent mushroom;
    public static SoundEvent mushroomSMB;

    public static SoundEvent oneUp;
    public static SoundEvent oneUpSMB;

    public static SoundEvent smbCoin;
    public static SoundEvent smwCoin;

    public static SoundEvent fireball;
    public static SoundEvent cape;

    public static SoundEvent starMan;
    public static SoundEvent jasterStarMan;

    public static SoundEvent recordUnderwaterSMB;

    public static SoundEvent noteBlock;

    public static SoundEvent pipe;

    public static SoundEvent beanstalk;

    public static SoundEvent smbEmptyBlockHit;
    public static SoundEvent smbBlockHitPowerup;

    public static SoundEvent smwEmptyBlockHit;
    public static SoundEvent smwBlockHitPowerup;

    public static SoundEvent diamonds;

    // Join sound
    public static SoundEvent join;

    // Leave sound
    public static SoundEvent leave;

    // Death sounds
    public static SoundEvent death1;
    public static SoundEvent death2;
    public static SoundEvent death3;
    public static SoundEvent death4;

    // Entities
    public static SoundEvent goombaDeath;

    public static void init()
    {
        mushroom = register("item.smwMushroom");
        mushroomSMB = register("item.mushroom");
        oneUp = register("item.smw1up");
        oneUpSMB = register("item.1up");

        smbCoin = register("item.smbCoin");
        smwCoin = register("item.smwCoin");

        fireball = register("item.fireball");
        cape = register("item.cape");

        starMan = register("item.starman");
        jasterStarMan = register("item.jasterStarman");

        recordUnderwaterSMB = register("records.smbUnderwater");

        noteBlock = register("block.note");

        pipe = register("block.pipe");

        beanstalk = register("block.beanstalk");

        smbEmptyBlockHit = register("block.smbEmptyBlockHit");
        smbBlockHitPowerup = register("block.smbBlockHitPowerup");

        smwEmptyBlockHit = register("block.smwEmptyBlockHit");
        smwBlockHitPowerup = register("block.smwBlockHitPowerup");

        diamonds = register("player.diamonds");

        // Join Sound
        join = register("player.join");

        // Leave Sound
        leave = register("player.leave");

        // Death Sounds
        death1 = register("player.death1");
        death2 = register("player.death2");
        death3 = register("player.death3");
        death4 = register("player.death4");

        // Entities
        goombaDeath = register("entity.goomba.death");
    }

    public static SoundEvent register(String name)
    {
        ResourceLocation loc = new ResourceLocation(ModInfo.MOD_ID, name);
        SoundEvent event = new SoundEvent(loc);
        event.setRegistryName(loc);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos)
    {
        playSoundName(soundName, world, category, pos, 1.0f, 1.0f);
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos, float volume, float pitch)
    {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundName));

        if(sound != null)
        {
            world.playSound(null, pos, sound, category, volume, pitch);
        }
    }
}
