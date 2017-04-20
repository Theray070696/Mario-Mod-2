package io.github.Theray070696.mariodeath.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Created by Theray on 4/15/2016.
 */
public class MarioTeleporter extends Teleporter
{

    public MarioTeleporter(WorldServer p_i1963_1_)
    {
        super(p_i1963_1_);
    }

    /**
     * Place an entity in a nearby portal, creating one if necessary.
     */
    @Override
    public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_) {}
}