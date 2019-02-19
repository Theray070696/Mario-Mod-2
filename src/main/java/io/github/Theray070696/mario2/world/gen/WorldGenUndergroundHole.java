package io.github.Theray070696.mario2.world.gen;

import io.github.Theray070696.mario2.block.BlockQuestionMarkBase;
import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.dev.MarioDevStats;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Random;

public class WorldGenUndergroundHole extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(!world.isRemote)
        {
            WorldServer worldServer = (WorldServer) world;
            MinecraftServer minecraftServer = world.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            ResourceLocation loc = new ResourceLocation(ModInfo.MOD_ID, "undergroundhole");
            Template template = templateManager.getTemplate(minecraftServer, loc);
            if(template != null)
            {
                IBlockState blockState = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, blockState, blockState, 3);
                int numFluidBlocks = 0;

                for(int x = 0; x < template.getSize().getX(); x++)
                {
                    for(int y = 0; y < template.getSize().getY(); y++)
                    {
                        for(int z = 0; z < template.getSize().getZ(); z++)
                        {
                            Block b = world.getBlockState(pos.add(x, y, z)).getBlock();
                            if(b instanceof BlockLiquid || b instanceof BlockFluidBase)
                            {
                                numFluidBlocks++;

                                if(numFluidBlocks > 4)
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }

                PlacementSettings placementsettings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities
                        (false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);

                template.addBlocksToWorld(world, pos, placementsettings);

                Block block = world.getBlockState(pos.add(3, 3, 3)).getBlock();
                if(block instanceof BlockQuestionMarkBase)
                {
                    world.setBlockState(pos.add(3, 3, 3), ModBlocks.marioBlockQuestionMarkUndergroundSMB.getDefaultState());
                    WorldGenQuestionMark.onQuestionMarkGenerated(world, pos.add(3, 3, 3), rand);
                }

                if(ConfigHandler.developerModeEnabled)
                {
                    MarioDevStats.undergroundHolesGenerated++;
                }

                return true;
            }
        }

        return false;
    }
}
