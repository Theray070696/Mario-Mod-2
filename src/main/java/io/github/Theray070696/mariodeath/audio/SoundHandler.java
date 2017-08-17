package io.github.Theray070696.mariodeath.audio;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    // Join sounds

    public static SoundEvent rayJoin;

    public static SoundEvent jasterJoin1;
    public static SoundEvent jasterJoin2;
    public static SoundEvent jasterJoin3;
    public static SoundEvent jasterJoin4;
    public static SoundEvent jasterJoin5;

    public static SoundEvent join;

    // Leave sounds

    public static SoundEvent rayLeave;

    public static SoundEvent jasterLeave1;
    public static SoundEvent jasterLeave2;

    public static SoundEvent leave;

    // Death sounds

    public static SoundEvent rayDeath1;
    public static SoundEvent rayDeath2;
    public static SoundEvent rayDeath3;
    public static SoundEvent rayDeath4;
    public static SoundEvent rayDeath5;

    public static SoundEvent jasterDeath1;
    public static SoundEvent jasterDeath2;

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

        rayJoin = register("player.rayJoin");

        jasterJoin1 = register("player.jasterJoin1");
        jasterJoin2 = register("player.jasterJoin2");
        jasterJoin3 = register("player.jasterJoin3");
        jasterJoin4 = register("player.jasterJoin4");
        jasterJoin5 = register("player.jasterJoin5");

        join = register("player.join");

        // Leave Sound

        rayLeave = register("player.rayLeave");

        jasterLeave1 = register("player.jasterLeave1");
        jasterLeave2 = register("player.jasterLeave2");

        leave = register("player.leave");

        // Death Sounds

        rayDeath1 = register("player.rayDeath1");
        rayDeath2 = register("player.rayDeath2");
        rayDeath3 = register("player.rayDeath3");
        rayDeath4 = register("player.rayDeath4");
        rayDeath5 = register("player.rayDeath5");

        jasterDeath1 = register("player.jasterDeath1");
        jasterDeath2 = register("player.jasterDeath2");

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
        SoundEvent e = new SoundEvent(loc);
        GameRegistry.register(e, loc);

        return e;
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos)
    {
        playSoundName(soundName, world, category, pos, 1.0F, 1.0F);
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos, float volume, float pitch)
    {
        SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundName));

        if(sound != null)
        {
            world.playSound(null, pos, sound, category, volume, pitch);
        }
    }
}
