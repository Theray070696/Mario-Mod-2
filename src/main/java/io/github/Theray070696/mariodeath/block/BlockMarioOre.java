package io.github.Theray070696.mariodeath.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Theray070696 on 8/28/2017
 */
public class BlockMarioOre extends BlockMario
{
    public BlockMarioOre()
    {
        super(Material.ROCK);

        this.setSoundType(SoundType.STONE);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this == ModBlocks.blockCoalOreSMW ? Items.COAL : (this == ModBlocks.blockDiamondOreSMW ? Items.DIAMOND : (this == ModBlocks
                .blockLapisOreSMW ? Items.DYE : Item.getItemFromBlock(this)));
    }

    public int quantityDropped(Random rand)
    {
        return this == ModBlocks.blockLapisOreSMW ? 4 + rand.nextInt(5) : 1;
    }

    public int quantityDroppedWithBonus(int fortune, Random rand)
    {
        if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), rand, fortune))
        {
            int i = rand.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(rand) * (i + 1);
        } else
        {
            return this.quantityDropped(rand);
        }
    }

    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if(this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = 0;

            if(this == ModBlocks.blockCoalOreSMW)
            {
                i = MathHelper.getRandomIntegerInRange(rand, 0, 2);
            } else if(this == ModBlocks.blockDiamondOreSMW)
            {
                i = MathHelper.getRandomIntegerInRange(rand, 3, 7);
            } else if(this == ModBlocks.blockLapisOreSMW)
            {
                i = MathHelper.getRandomIntegerInRange(rand, 2, 5);
            }

            return i;
        }
        return 0;
    }

    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }

    public int damageDropped(IBlockState state)
    {
        return this == ModBlocks.blockLapisOreSMW ? EnumDyeColor.BLUE.getDyeDamage() : 0;
    }

    @Override
    public int getHarvestLevel(IBlockState state)
    {
        if(state.getBlock() == ModBlocks.blockCoalOreSMW)
        {
            return 0;
        } else if(state.getBlock() == ModBlocks.blockIronOreSMW || state.getBlock() == ModBlocks.blockLapisOreSMW)
        {
            return 1;
        } else if(state.getBlock() == ModBlocks.blockGoldOreSMW || state.getBlock() == ModBlocks.blockDiamondOreSMW)
        {
            return 2;
        }

        return super.getHarvestLevel(state);
    }
}
