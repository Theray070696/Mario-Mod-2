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

    public static SoundEvent jasterStarMan;

    public static SoundEvent starMan;
    public static SoundEvent spiceMan;

    public static SoundEvent recordSpiceman;
    public static SoundEvent recordUnderwaterSMB;

    public static SoundEvent noteBlock;

    public static SoundEvent smbEmptyBlockHit;
    public static SoundEvent smbBlockHitPowerup;

    public static SoundEvent smwEmptyBlockHit;
    public static SoundEvent smwBlockHitPowerup;

    public static SoundEvent diamonds;

    // Join sounds... fuck this is tedious

    public static SoundEvent rayJoin;

    public static SoundEvent jasterJoin1;
    public static SoundEvent jasterJoin2;
    public static SoundEvent jasterJoin3;
    public static SoundEvent jasterJoin4;
    public static SoundEvent jasterJoin5;

    public static SoundEvent join;

    // Leave sounds... not getting any less tedious

    public static SoundEvent rayLeave;

    public static SoundEvent jasterLeave1;
    public static SoundEvent jasterLeave2;

    public static SoundEvent leave;

    // Death sounds... fuuuuuuuuuuu

    public static SoundEvent rayDeath1;
    public static SoundEvent rayDeath2;

    public static SoundEvent jasterDeath1;
    public static SoundEvent jasterDeath2;

    public static SoundEvent ghostDeath1;

    public static SoundEvent rareDeath1;

    public static SoundEvent death1;
    public static SoundEvent death2;
    public static SoundEvent death3;
    public static SoundEvent death4;
    public static SoundEvent death5;

    public static SoundEvent fiftyDeaths;

    // BLOCK!

    public static SoundEvent spicemanBlock1;
    public static SoundEvent spicemanBlock2;
    public static SoundEvent spicemanBlock3;
    public static SoundEvent spicemanBlock4;
    public static SoundEvent spicemanBlock5;
    public static SoundEvent spicemanBlock6;
    public static SoundEvent spicemanBlock7;
    public static SoundEvent spicemanBlock8;
    public static SoundEvent spicemanBlock9;
    public static SoundEvent spicemanBlock10;
    public static SoundEvent spicemanBlock11;
    public static SoundEvent spicemanBlock12;

    // Sounds for the Terry Block

    public static SoundEvent terry1;
    public static SoundEvent terry2;
    public static SoundEvent terry3;
    public static SoundEvent terry4;
    public static SoundEvent terry5;
    public static SoundEvent terry6;
    public static SoundEvent terry7;
    public static SoundEvent terry8;
    public static SoundEvent terry9;

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

        jasterStarMan = register("item.jasterStarman");

        starMan = register("item.starman");
        spiceMan = register("item.spiceman");

        recordSpiceman = register("records.spiceman");
        recordUnderwaterSMB = register("records.smbUnderwater");

        noteBlock = register("block.note");

        smbEmptyBlockHit = register("block.smbEmptyBlockHit");
        smbBlockHitPowerup = register("block.smbBlockHitPowerup");

        smwEmptyBlockHit = register("block.smwEmptyBlockHit");
        smwBlockHitPowerup = register("block.smwBlockHitPowerup");

        diamonds = register("player.diamonds");

        // Join Sounds

        rayJoin = register("player.rayJoin");

        jasterJoin1 = register("player.jasterJoin1");
        jasterJoin2 = register("player.jasterJoin2");
        jasterJoin3 = register("player.jasterJoin3");
        jasterJoin4 = register("player.jasterJoin4");
        jasterJoin5 = register("player.jasterJoin5");

        join = register("player.join");

        // Leave Sounds

        rayLeave = register("player.rayLeave");

        jasterLeave1 = register("player.jasterLeave1");
        jasterLeave2 = register("player.jasterLeave2");

        leave = register("player.leave");

        // Death Sounds

        rayDeath1 = register("player.rayDeath1");
        rayDeath2 = register("player.rayDeath2");

        jasterDeath1 = register("player.jasterDeath1");
        jasterDeath2 = register("player.jasterDeath2");

        ghostDeath1 = register("player.ghostDeath1");

        rareDeath1 = register("player.rareDeath1");

        death1 = register("player.death1");
        death2 = register("player.death2");
        death3 = register("player.death3");
        death4 = register("player.death4");
        death5 = register("player.death5");

        fiftyDeaths = register("player.fiftyDeaths");

        // BLOCK!

        spicemanBlock1 = register("player.spicemanBlock1");
        spicemanBlock2 = register("player.spicemanBlock2");
        spicemanBlock3 = register("player.spicemanBlock3");
        spicemanBlock4 = register("player.spicemanBlock4");
        spicemanBlock5 = register("player.spicemanBlock5");
        spicemanBlock6 = register("player.spicemanBlock6");
        spicemanBlock7 = register("player.spicemanBlock7");
        spicemanBlock8 = register("player.spicemanBlock8");
        spicemanBlock9 = register("player.spicemanBlock9");
        spicemanBlock10 = register("player.spicemanBlock10");
        spicemanBlock11 = register("player.spicemanBlock11");
        spicemanBlock12 = register("player.spicemanBlock12");

        terry1 = register("block.terry1");
        terry2 = register("block.terry2");
        terry3 = register("block.terry3");
        terry4 = register("block.terry4");
        terry5 = register("block.terry5");
        terry6 = register("block.terry6");
        terry7 = register("block.terry7");
        terry8 = register("block.terry8");
        terry9 = register("block.terry9");
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
        SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundName));

        if(sound != null)
        {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), sound, category, 1.0F, 1.0F);
        }
    }
}
