package io.github.Theray070696.mario2.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Theray070696 on 8/23/2017
 */
public class BlockMarioLeaves extends BlockMario implements IShearable
{
    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
    private int[] surroundings;

    public BlockMarioLeaves()
    {
        super(Material.LEAVES);
        this.setTranslationKey("marioBlockLeaves");
        this.setTickRandomly(true);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
    }

    // TODO: Possibly add a Yoshi fruit that can grow on the trees.
    // TODO: Extending the above idea, maybe make them combinable with wheat to make Yoshi Cookies.

    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if(world.isAreaLoaded(new BlockPos(x - 2, y - 2, z - 2), new BlockPos(x + 2, y + 2, z + 2)))
        {
            for(int j1 = -1; j1 <= 1; ++j1)
            {
                for(int k1 = -1; k1 <= 1; ++k1)
                {
                    for(int l1 = -1; l1 <= 1; ++l1)
                    {
                        BlockPos blockPos = pos.add(j1, k1, l1);
                        IBlockState blockState = world.getBlockState(blockPos);

                        if(blockState.getBlock().isLeaves(blockState, world, blockPos))
                        {
                            blockState.getBlock().beginLeavesDecay(blockState, world, blockPos);
                        }
                    }
                }
            }
        }
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            if(((Boolean) state.getValue(CHECK_DECAY)).booleanValue() && ((Boolean) state.getValue(DECAYABLE)).booleanValue())
            {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                if(this.surroundings == null)
                {
                    this.surroundings = new int[32768];
                }

                if(!world.isAreaLoaded(pos, 1))
                {
                    return;
                }

                if(world.isAreaLoaded(pos, 6))
                {
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                    for(int i2 = -4; i2 <= 4; ++i2)
                    {
                        for(int j2 = -4; j2 <= 4; ++j2)
                        {
                            for(int k2 = -4; k2 <= 4; ++k2)
                            {
                                IBlockState iblockstate = world.getBlockState(mutableBlockPos.setPos(x + i2, y + j2, z + k2));
                                Block block = iblockstate.getBlock();

                                if(!block.canSustainLeaves(iblockstate, world, mutableBlockPos.setPos(x + i2, y + j2, z + k2)))
                                {
                                    if(block.isLeaves(iblockstate, world, mutableBlockPos.setPos(x + i2, y + j2, z + k2)))
                                    {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                                    } else
                                    {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                                    }
                                } else
                                {
                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                                }
                            }
                        }
                    }

                    for(int i3 = 1; i3 <= 4; ++i3)
                    {
                        for(int j3 = -4; j3 <= 4; ++j3)
                        {
                            for(int k3 = -4; k3 <= 4; ++k3)
                            {
                                for(int l3 = -4; l3 <= 4; ++l3)
                                {
                                    if(this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1)
                                    {
                                        if(this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if(this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if(this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                                        }

                                        if(this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                                        }

                                        if(this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                                        }

                                        if(this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if(l2 >= 0)
                {
                    world.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
                } else
                {
                    this.destroy(world, pos);
                }
            }
        }
    }

    private void destroy(World world, BlockPos pos)
    {
        this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockToAir(pos);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if(world.isRainingAt(pos.up()) && !world.getBlockState(pos.down()).getBlock().isOpaqueCube(world.getBlockState(pos.down())) && rand.nextInt
                (15) == 1)
        {
            double x = (double) ((float) pos.getX() + rand.nextFloat());
            double y = (double) pos.getY() - 0.05d;
            double z = (double) ((float) pos.getZ() + rand.nextFloat());
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, x, y, z, 0.0d, 0.0d, 0.0d);
        }
    }

    public int quantityDropped(Random rand)
    {
        return rand.nextInt(20) == 0 ? 1 : 0;
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.marioBlockSapling);
    }

    private int getSaplingDropChance()
    {
        return 20;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return Blocks.LEAVES.getRenderLayer();
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
    {
        if(!state.getValue(CHECK_DECAY))
        {
            world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<>();
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        int chance = this.getSaplingDropChance();

        if(fortune > 0)
        {
            chance -= 2 << fortune;
            if(chance < 10)
            {
                chance = 10;
            }
        }

        if(rand.nextInt(chance) == 0)
        {
            ret.add(new ItemStack(getItemDropped(state, rand, fortune)));
        }

        this.captureDrops(true);
        ret.addAll(this.captureDrops(false));
        return ret;
    }


    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;

        if(!state.getValue(DECAYABLE))
        {
            meta |= 4;
        }

        if(state.getValue(CHECK_DECAY))
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(this, 1));
    }
}
