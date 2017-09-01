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

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
    }
}