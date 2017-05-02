package io.github.Theray070696.mariodeath.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Created by Theray on 4/15/2016.
 */
public class MarioTeleporter extends Teleporter
{

    public MarioTeleporter(WorldServer worldServer)
    {
        super(worldServer);
    }

    /**
     * Place an entity in a nearby portal, creating one if necessary.
     */
    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {}
}