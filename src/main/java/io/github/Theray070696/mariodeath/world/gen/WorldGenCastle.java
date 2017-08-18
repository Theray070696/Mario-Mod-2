package io.github.Theray070696.mariodeath.world.gen;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Random;

/**
 * Created by Theray070696 on 8/18/2017
 */
public class WorldGenCastle extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(!world.isRemote)
        {
            boolean flag;

            WorldServer worldServer = (WorldServer) world;
            MinecraftServer minecraftServer = world.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            ResourceLocation loc = new ResourceLocation(ModInfo.MOD_ID, "castle");
            Template template = templateManager.getTemplate(minecraftServer, loc);
            if(template != null)
            {
                IBlockState blockState = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, blockState, blockState, 3);
                flag = true;

                for(int i = 0; i < template.getSize().getX(); i++)
                {
                    if(!flag)
                    {
                        break;
                    }

                    for(int j = 0; j < template.getSize().getZ(); j++)
                    {
                        if(!flag)
                        {
                            break;
                        }

                        BlockPos down = pos.add(i, -1, j);
                        Block b = world.getBlockState(down).getBlock();
                        if(b instanceof BlockLiquid || b instanceof BlockFluidBase || b instanceof BlockAir || b instanceof BlockSlab || b
                                instanceof BlockSnow)
                        {
                            flag = false;
                        }
                    }
                }

                if(flag)
                {
                    PlacementSettings placementsettings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(Rotation.values()[rand.nextInt
                            (4)]).setIgnoreEntities(false).setChunk((ChunkPos) null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

                    template.addBlocksToWorld(world, pos.down(), placementsettings);
                    return true;
                }
            }
        }

        return false;
    }
}
