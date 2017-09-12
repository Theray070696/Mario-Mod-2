package io.github.Theray070696.mario2.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * Created by Theray070696 on 8/28/2017
 */
@Cancelable
public class PlayMarioSoundEvent extends PlayerEvent
{
    private SoundType soundType;

    public PlayMarioSoundEvent(EntityPlayer player, SoundType soundType)
    {
        super(player);

        this.soundType = soundType;
    }

    public SoundType getSoundType()
    {
        return soundType;
    }

    public enum SoundType
    {
        JOIN_SOUND,
        LEAVE_SOUND,
        DEATH_SOUND
    }
}
