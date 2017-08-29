package io.github.Theray070696.mariodeath.world.gen;

import io.github.Theray070696.mariodeath.block.BlockInvisibleBlock;
import io.github.Theray070696.mariodeath.block.BlockQuestionMarkBase;
import io.github.Theray070696.mariodeath.block.EnumBlockType;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import io.github.Theray070696.mariodeath.dev.MarioDevStats;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Random;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class WorldGenQuestionMark extends WorldGenerator
{
    private Block block;
    private boolean rare;

    public WorldGenQuestionMark(Block block, boolean rare)
    {
        this.block = block;
        this.rare = rare;
    }

    public static void onQuestionMarkGenerated(World world, int x, int y, int z, Random rand)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        if(block instanceof BlockQuestionMarkBase)
        {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
            if(tileEntity instanceof TileQuestionMark && (((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMW) || (
                    (BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMW_INVISIBLE)))
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item;
                int chance = rand.nextInt(100);

                if(chance < 5)
                {
                    item = ItemsInQuestionMarks.ITEM_CAPE;
                } else if(chance >= 5 && chance < 15)
                {
                    item = ItemsInQuestionMarks.ITEM_STAR_MAN;
                } else if(chance >= 15 && chance < 25)
                {
                    item = ItemsInQuestionMarks.ITEM_1UP;
                } else if(chance >= 25 && chance < 40)
                {
                    item = ItemsInQuestionMarks.ITEM_FIRE_FLOWER;
                } else if(chance >= 40 && chance < 60)
                {
                    item = ItemsInQuestionMarks.ITEM_MUSHROOM;
                } else
                {
                    item = ItemsInQuestionMarks.ITEM_COIN;
                }

                questionMark.setItemInBlock(item);
            } else if(tileEntity instanceof TileQuestionMark && (((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB) || (
                    (BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB_INVISIBLE) || ((BlockQuestionMarkBase) block)
                    .getBlockType().equals(EnumBlockType.SMB_UNDERGROUND) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType
                    .SMB_CASTLE) || ((BlockQuestionMarkBase) block).getBlockType().equals(EnumBlockType.SMB3) || ((BlockQuestionMarkBase) block)
                    .getBlockType().equals(EnumBlockType.SMB3_INVISIBLE)))
            {
                TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
                int item;
                int chance = rand.nextInt(100);

                if(chance < 10)
                {
                    item = ItemsInQuestionMarks.ITEM_STAR_MAN;
                } else if(chance >= 10 && chance < 20)
                {
                    item = ItemsInQuestionMarks.ITEM_1UP;
                } else if(chance >= 20 && chance < 35)
                {
                    item = ItemsInQuestionMarks.ITEM_FIRE_FLOWER;
                } else if(chance >= 35 && chance < 55)
                {
                    item = ItemsInQuestionMarks.ITEM_MUSHROOM;
                } else
                {
                    item = ItemsInQuestionMarks.ITEM_COIN;
                }

                questionMark.setItemInBlock(item);
            }

            if(ConfigHandler.developerModeEnabled)
            {
                if(block instanceof BlockInvisibleBlock)
                {
                    MarioDevStats.invisibleBlocksGenerated++;
                }

                MarioDevStats.questionMarksGenerated++;
            }
        }
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        int y = pos.getY();

        for(int m = y; m <= 200; ++m)
        {
            if(!world.isAirBlock(new BlockPos(pos.getX(), m, pos.getZ())))
            {
                y = m;
            }
        }

        if(!(world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getBlock() instanceof BlockLiquid) && !(world.getBlockState(new BlockPos
                (pos.getX(), y, pos.getZ())).getBlock() instanceof BlockFluidBase))
        {
            if(!rare)
            {
                y = y + 3 + random.nextInt(3);

                world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), block.getDefaultState(), 2);
                onQuestionMarkGenerated(world, pos.getX(), y, pos.getZ(), random);
                return true;
            } else if(random.nextInt(50) == 0)
            {
                y = y + 3 + random.nextInt(3);

                world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), block.getDefaultState(), 2);
                onQuestionMarkGenerated(world, pos.getX(), y, pos.getZ(), random);
                return true;
            }
        }

        return false;
    }
}
