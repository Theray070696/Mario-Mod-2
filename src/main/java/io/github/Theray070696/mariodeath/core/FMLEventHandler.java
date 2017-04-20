package io.github.Theray070696.mariodeath.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import io.github.Theray070696.raycore.api.RayCoreAPI;

import java.util.Random;

/**
 * Created by Theray on 12/20/2016.
 */
@SuppressWarnings("unused")
public class FMLEventHandler
{
    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(event.player.getDisplayName().equalsIgnoreCase("Theray070696"))
        {
            // Play Terry Crews "IT'S ME!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.rayJoin");
        } else if(event.player.getDisplayName().equalsIgnoreCase("JasterMK3"))
        {
            int randInt = new Random().nextInt(5);

            if(randInt == 0)
            {
                // POWER!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin");
            } else if(randInt == 1)
            {
                // CAPTAIN PISS WEAK!!!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin2");
            } else if(randInt == 2)
            {
                // POWER!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin3");
            } else if(randInt == 3)
            {
                // POWER!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin4");
            } else if(randInt == 4)
            {
                // POWER!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterJoin5");
            }

        } else
        {
            // Play Mario "It's a me!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.join");
        }
    }

    @SubscribeEvent
    public void playerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if(event.player.getDisplayName().equalsIgnoreCase("Theray070696"))
        {
            // Play Terry Crews "GOODBYE!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.rayLeave");
        } else if(event.player.getDisplayName().equalsIgnoreCase("JasterMK3"))
        {
            int randInt = new Random().nextInt(2);
    
            if(randInt == 0)
            {
                // THIS COMERCIAL END!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterLeave");
            } else if(randInt == 1)
            {
                // AAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHH!
                RayCoreAPI.playSoundToAll("mariodeath:player.jasterLeave2");
            }
        } else
        {
            // Play Mario "See you next time!" sound to everyone.
            RayCoreAPI.playSoundToAll("mariodeath:player.leave");
        }
    }
}
