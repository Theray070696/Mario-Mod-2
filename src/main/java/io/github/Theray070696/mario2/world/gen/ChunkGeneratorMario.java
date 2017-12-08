package io.github.Theray070696.mario2.world.gen;

import io.github.Theray070696.mario2.block.ModBlocks;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class ChunkGeneratorMario implements IChunkGenerator
{
    public static IBlockState UNDERGROUND_GROUND = ModBlocks.blockGroundUndergroundSMW.getDefaultState();
    private final Random rand;
    private final World worldObj;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final double[] heightMap;
    private final float[] biomeWeights;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    public NoiseGeneratorOctaves forestNoise;
    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;
    double[] depthRegion;
    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;
    private ChunkProviderSettings settings;
    private IBlockState oceanBlock = Blocks.WATER.getDefaultState();
    private double[] depthBuffer = new double[256];
    private MapGenBase caveGenerator = new MapGenCaves();
    private MapGenVillage villageGenerator = new MapGenVillage();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private Biome[] biomesForGeneration;

    public ChunkGeneratorMario(World world, long seed, boolean mapFeaturesEnabled, String settingsString)
    {
        {
            caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, InitMapGenEvent.EventType.CAVE);
            villageGenerator = (MapGenVillage) TerrainGen.getModdedMapGen(villageGenerator, InitMapGenEvent.EventType.VILLAGE);
            mineshaftGenerator = (MapGenMineshaft) TerrainGen.getModdedMapGen(mineshaftGenerator, InitMapGenEvent.EventType.MINESHAFT);
            scatteredFeatureGenerator = (MapGenScatteredFeature) TerrainGen.getModdedMapGen(scatteredFeatureGenerator, InitMapGenEvent.EventType
                    .SCATTERED_FEATURE);
            ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, InitMapGenEvent.EventType.RAVINE);
        }
        this.worldObj = world;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.terrainType = world.getWorldInfo().getTerrainType();
        this.rand = new Random(seed);
        this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.heightMap = new double[825];
        this.biomeWeights = new float[25];

        for(int i = -2; i <= 2; ++i)
        {
            for(int j = -2; j <= 2; ++j)
            {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                this.biomeWeights[i + 2 + (j + 2) * 5] = f;
            }
        }

        if(settingsString != null)
        {
            this.settings = ChunkProviderSettings.Factory.jsonToFactory(settingsString).build();
            this.oceanBlock = this.settings.useLavaOceans ? Blocks.LAVA.getDefaultState() : Blocks.WATER.getDefaultState();
            world.setSeaLevel(this.settings.seaLevel);
        }

        InitNoiseGensEvent.ContextOverworld ctx = new InitNoiseGensEvent.ContextOverworld(this.minLimitPerlinNoise, this.maxLimitPerlinNoise, this
                .mainPerlinNoise, this.surfaceNoise, this.scaleNoise, this.depthNoise, this.forestNoise);
        ctx = TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
        this.minLimitPerlinNoise = ctx.getLPerlin1();
        this.maxLimitPerlinNoise = ctx.getLPerlin2();
        this.mainPerlinNoise = ctx.getPerlin();
        this.surfaceNoise = ctx.getHeight();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.forestNoise = ctx.getForest();
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
    {
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.generateHeightmap(x * 4, 0, z * 4);

        for(int heightMapX = 0; heightMapX < 4; ++heightMapX)
        {
            int indexXC = heightMapX * 5;
            int indexXN = (heightMapX + 1) * 5;

            for(int heightMapZ = 0; heightMapZ < 4; ++heightMapZ)
            {
                int indexXCZC = (indexXC + heightMapZ) * 33;
                int indexXCZN = (indexXC + heightMapZ + 1) * 33;
                int indexXNZC = (indexXN + heightMapZ) * 33;
                int indexXNZN = (indexXN + heightMapZ + 1) * 33;

                for(int heightMapY = 0; heightMapY < 32; ++heightMapY)
                {
                    double valXCZCYC = this.heightMap[indexXCZC + heightMapY];
                    double valXCZNYC = this.heightMap[indexXCZN + heightMapY];
                    double valXNZCYC = this.heightMap[indexXNZC + heightMapY];
                    double valXNZNYC = this.heightMap[indexXNZN + heightMapY];
                    double valXCZCYN = (this.heightMap[indexXCZC + heightMapY + 1] - valXCZCYC) * 0.125D;
                    double valXCZNYN = (this.heightMap[indexXCZN + heightMapY + 1] - valXCZNYC) * 0.125D;
                    double valXNZCYN = (this.heightMap[indexXNZC + heightMapY + 1] - valXNZCYC) * 0.125D;
                    double valXNZNYN = (this.heightMap[indexXNZN + heightMapY + 1] - valXNZNYC) * 0.125D;

                    for(int blockY = 0; blockY < 8; ++blockY)
                    {
                        double currentValXCZCYC = valXCZCYC;
                        double currentValXCZNYC = valXCZNYC;
                        double stepXAxisZC = (valXNZCYC - valXCZCYC) * 0.25D;
                        double stepXAxisZN = (valXNZNYC - valXCZNYC) * 0.25D;

                        for(int blockX = 0; blockX < 4; ++blockX)
                        {
                            double stepZAxis = (currentValXCZNYC - currentValXCZCYC) * 0.25D;
                            double currentValZC = currentValXCZCYC - stepZAxis;

                            for(int blockZ = 0; blockZ < 4; ++blockZ)
                            {
                                if((currentValZC += stepZAxis) > 0.0D)
                                {
                                    primer.setBlockState(heightMapX * 4 + blockX, heightMapY * 8 + blockY, heightMapZ * 4 + blockZ,
                                            UNDERGROUND_GROUND);
                                } else if(heightMapY * 8 + blockY < this.settings.seaLevel)
                                {
                                    primer.setBlockState(heightMapX * 4 + blockX, heightMapY * 8 + blockY, heightMapZ * 4 + blockZ, this.oceanBlock);
                                }
                            }

                            currentValXCZCYC += stepXAxisZC;
                            currentValXCZNYC += stepXAxisZN;
                        }

                        valXCZCYC += valXCZCYN;
                        valXCZNYC += valXCZNYN;
                        valXNZCYC += valXNZCYN;
                        valXNZNYC += valXNZNYN;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomes)
    {
        if(!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.worldObj))
        {
            return;
        }

        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for(int xMult = 0; xMult < 16; ++xMult)
        {
            for(int zMult = 0; zMult < 16; ++zMult)
            {
                Biome biome = biomes[zMult + xMult * 16];
                biome.genTerrainBlocks(this.worldObj, this.rand, primer, x * 16 + xMult, z * 16 + zMult, this.depthBuffer[zMult + xMult * 16]);
            }
        }
    }

    public Chunk provideChunk(int x, int z)
    {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(x, z, chunkprimer);
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);

        if(this.settings.useCaves)
        {
            this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);
        }

        if(this.settings.useRavines)
        {
            this.ravineGenerator.generate(this.worldObj, x, z, chunkprimer);
        }

        if(this.mapFeaturesEnabled)
        {
            if(this.settings.useMineShafts)
            {
                this.mineshaftGenerator.generate(this.worldObj, x, z, chunkprimer);
            }

            /*if(this.settings.useVillages)
            {
                this.villageGenerator.generate(this.worldObj, x, z, chunkprimer);
            }

            if(this.settings.useTemples)
            {
                this.scatteredFeatureGenerator.generate(this.worldObj, x, z, chunkprimer);
            }*/
        }

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] biomeArray = chunk.getBiomeArray();

        for(int i = 0; i < biomeArray.length; ++i)
        {
            biomeArray[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateHeightmap(int x, int y, int z)
    {
        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, x, z, 5, 5, (double) this.settings.depthNoiseScaleX, (double)
                this.settings.depthNoiseScaleZ, (double) this.settings.depthNoiseScaleExponent);
        float scaleXZ = this.settings.coordinateScale;
        float scaleY = this.settings.heightScale;
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, x, y, z, 5, 33, 5, (double) (scaleXZ / this.settings
                .mainNoiseScaleX), (double) (scaleY / this.settings.mainNoiseScaleY), (double) (scaleXZ / this.settings.mainNoiseScaleZ));
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, y, z, 5, 33, 5, (double) scaleXZ, (double)
                scaleY, (double) scaleXZ);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, y, z, 5, 33, 5, (double) scaleXZ, (double)
                scaleY, (double) scaleXZ);
        int noiseIndex = 0;
        int heightMapIndex = 0;

        for(int heightMapx = 0; heightMapx < 5; ++heightMapx)
        {
            for(int heightMapZ = 0; heightMapZ < 5; ++heightMapZ)
            {
                float biomeVariation = 0.0F;
                float biomeDepth = 0.0F;
                float totalBiomeWeight = 0.0F;
                Biome centerBiome = this.biomesForGeneration[heightMapx + 2 + (heightMapZ + 2) * 10];

                for(int offsetX = -2; offsetX <= 2; ++offsetX)
                {
                    for(int offsetZ = -2; offsetZ <= 2; ++offsetZ)
                    {
                        Biome nearbyBiome = this.biomesForGeneration[heightMapx + offsetX + 2 + (heightMapZ + offsetZ + 2) * 10];
                        float nearbyBiomeDepth = this.settings.biomeDepthOffSet + nearbyBiome.getBaseHeight() * this.settings.biomeDepthWeight;
                        float nearbyBiomeVariation = this.settings.biomeScaleOffset + nearbyBiome.getHeightVariation() * this.settings
                                .biomeScaleWeight;

                        if(this.terrainType == WorldType.AMPLIFIED && nearbyBiomeDepth > 0.0F)
                        {
                            nearbyBiomeDepth = 1.0F + nearbyBiomeDepth * 2.0F;
                            nearbyBiomeVariation = 1.0F + nearbyBiomeVariation * 4.0F;
                        }

                        float weight = this.biomeWeights[offsetX + 2 + (offsetZ + 2) * 5] / (nearbyBiomeDepth + 2.0F);

                        if(nearbyBiome.getBaseHeight() > centerBiome.getBaseHeight())
                        {
                            weight /= 2.0F;
                        }

                        biomeVariation += nearbyBiomeVariation * weight;
                        biomeDepth += nearbyBiomeDepth * weight;
                        totalBiomeWeight += weight;
                    }
                }

                biomeVariation = biomeVariation / totalBiomeWeight;
                biomeDepth = biomeDepth / totalBiomeWeight;
                biomeVariation = biomeVariation * 0.9F + 0.1F;
                biomeDepth = (biomeDepth * 4.0F - 1.0F) / 8.0F;
                double depthPerturbation = this.depthRegion[heightMapIndex] / 8000.0D;

                if(depthPerturbation < 0.0D)
                {
                    depthPerturbation = -depthPerturbation * 0.3D;
                }

                depthPerturbation = depthPerturbation * 3.0D - 2.0D;

                if(depthPerturbation < 0.0D)
                {
                    depthPerturbation = depthPerturbation / 2.0D;

                    if(depthPerturbation < -1.0D)
                    {
                        depthPerturbation = -1.0D;
                    }

                    depthPerturbation = depthPerturbation / 1.4D;
                    depthPerturbation = depthPerturbation / 2.0D;
                } else
                {
                    if(depthPerturbation > 1.0D)
                    {
                        depthPerturbation = 1.0D;
                    }

                    depthPerturbation = depthPerturbation / 8.0D;
                }

                ++heightMapIndex;
                double d8 = (double) biomeDepth;
                double d9 = (double) biomeVariation;
                d8 = d8 + depthPerturbation * 0.2D;
                d8 = d8 * (double) this.settings.baseSize / 8.0D;
                double d0 = (double) this.settings.baseSize + d8 * 4.0D;

                for(int heightMapY = 0; heightMapY < 33; ++heightMapY)
                {
                    double densityOffset = ((double) heightMapY - d0) * (double) this.settings.stretchY * 128.0D / 256.0D / d9;

                    if(densityOffset < 0.0D)
                    {
                        densityOffset *= 4.0D;
                    }

                    double minDensity = this.minLimitRegion[noiseIndex] / (double) this.settings.lowerLimitScale;
                    double maxDensity = this.maxLimitRegion[noiseIndex] / (double) this.settings.upperLimitScale;
                    double mainDensity = (this.mainNoiseRegion[noiseIndex] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clampedLerp(minDensity, maxDensity, mainDensity) - densityOffset;

                    if(heightMapY > 29)
                    {
                        double d6 = (double) ((float) (heightMapY - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    this.heightMap[noiseIndex] = d5;
                    ++noiseIndex;
                }
            }
        }
    }

    public void populate(int x, int z)
    {
        BlockFalling.fallInstantly = true;
        int bx = x * 16;
        int bz = z * 16;
        BlockPos blockpos = new BlockPos(bx, 0, bz);
        Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.worldObj.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long) x * k + (long) z * l ^ this.worldObj.getSeed());
        boolean villageGenerated = false;
        ChunkPos chunkpos = new ChunkPos(x, z);

        ForgeEventFactory.onChunkPopulate(true, this, this.worldObj, this.rand, x, z, villageGenerated);

        if(this.mapFeaturesEnabled)
        {
            if(this.settings.useMineShafts)
            {
                this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }

            /*if(this.settings.useVillages)
            {
                villageGenerated = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }

            if(this.settings.useTemples)
            {
                this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            }*/
        }

        if(biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes && !villageGenerated && this.rand.nextInt(this
                .settings.waterLakeChance) == 0)
        {
            if(TerrainGen.populate(this, this.worldObj, this.rand, x, z, villageGenerated, PopulateChunkEvent.Populate.EventType.LAKE))
            {
                int finalX = this.rand.nextInt(16) + 8;
                int finalY = this.rand.nextInt(256);
                int finalZ = this.rand.nextInt(16) + 8;
                (new WorldGenLakes(Blocks.WATER)).generate(this.worldObj, this.rand, blockpos.add(finalX, finalY, finalZ));
            }
        }

        if(!villageGenerated && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes)
        {
            if(TerrainGen.populate(this, this.worldObj, this.rand, x, z, villageGenerated, PopulateChunkEvent.Populate.EventType.LAVA))
            {
                int finalX = this.rand.nextInt(16) + 8;
                int finalY = this.rand.nextInt(this.rand.nextInt(248) + 8);
                int finalZ = this.rand.nextInt(16) + 8;

                if(finalY < this.worldObj.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0)
                {
                    (new WorldGenLakes(Blocks.LAVA)).generate(this.worldObj, this.rand, blockpos.add(finalX, finalY, finalZ));
                }
            }
        }

        if(this.settings.useDungeons)
        {
            if(TerrainGen.populate(this, this.worldObj, this.rand, x, z, villageGenerated, PopulateChunkEvent.Populate.EventType.DUNGEON))
            {
                for(int chance = 0; chance < this.settings.dungeonChance; ++chance)
                {
                    int finalX = this.rand.nextInt(16) + 8;
                    int finalY = this.rand.nextInt(256);
                    int finalZ = this.rand.nextInt(16) + 8;
                    (new WorldGenDungeons()).generate(this.worldObj, this.rand, blockpos.add(finalX, finalY, finalZ));
                }
            }
        }

        biome.decorate(this.worldObj, this.rand, new BlockPos(bx, 0, bz));
        if(TerrainGen.populate(this, this.worldObj, this.rand, x, z, villageGenerated, PopulateChunkEvent.Populate.EventType.ANIMALS))
        {
            WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, bx + 8, bz + 8, 16, 16, this.rand);
        }
        blockpos = blockpos.add(8, 0, 8);

        if(TerrainGen.populate(this, this.worldObj, this.rand, x, z, villageGenerated, PopulateChunkEvent.Populate.EventType.ICE))
        {
            for(int xMod = 0; xMod < 16; ++xMod)
            {
                for(int zMod = 0; zMod < 16; ++zMod)
                {
                    BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(xMod, 0, zMod));
                    BlockPos blockpos2 = blockpos1.down();

                    if(this.worldObj.canBlockFreezeWater(blockpos2))
                    {
                        this.worldObj.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
                    }

                    if(this.worldObj.canSnowAt(blockpos1, true))
                    {
                        this.worldObj.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
                    }
                }
            }
        }//Forge: End ICE

        ForgeEventFactory.onChunkPopulate(false, this, this.worldObj, this.rand, x, z, villageGenerated);

        BlockFalling.fallInstantly = false;
    }

    public boolean generateStructures(Chunk chunk, int x, int z)
    {
        return false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = this.worldObj.getBiome(pos);

        if(this.mapFeaturesEnabled)
        {
            if(creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isSwampHut(pos))
            {
                return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
            }
        }

        return biome.getSpawnableList(creatureType);
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        if(!this.mapFeaturesEnabled)
        {
            return false;
        } else
        {
            return "Mineshaft".equals(structureName) && this.mineshaftGenerator != null && this.mineshaftGenerator.isInsideStructure(pos);
        }
    }

    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        if(!this.mapFeaturesEnabled)
        {
            return null;
        } else if("Mineshaft".equals(structureName) && this.mineshaftGenerator != null)
        {
            return this.mineshaftGenerator.getClosestStrongholdPos(worldIn, position, findUnexplored);
        } else
        {
            return null;
        }
    }

    public void recreateStructures(Chunk chunk, int x, int z)
    {
        if(this.mapFeaturesEnabled)
        {
            if(this.settings.useMineShafts)
            {
                this.mineshaftGenerator.generate(this.worldObj, x, z, (ChunkPrimer) null);
            }

            /*if(this.settings.useVillages)
            {
                this.villageGenerator.generate(this.worldObj, x, z, (ChunkPrimer) null);
            }

            if(this.settings.useTemples)
            {
                this.scatteredFeatureGenerator.generate(this.worldObj, x, z, (ChunkPrimer) null);
            }*/
        }
    }
}
