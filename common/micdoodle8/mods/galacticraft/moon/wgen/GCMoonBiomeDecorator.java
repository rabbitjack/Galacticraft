package micdoodle8.mods.galacticraft.moon.wgen;

import java.util.Random;
import micdoodle8.mods.galacticraft.api.event.wgen.GCCoreEventPopulate;
import micdoodle8.mods.galacticraft.core.wgen.GCCoreWorldGenMinableMeta;
import micdoodle8.mods.galacticraft.moon.blocks.GCMoonBlocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 * All rights reserved.
 * 
 */
public class GCMoonBiomeDecorator
{
    protected World worldObj;
    protected Random randomGenerator;

    protected int chunkX;
    protected int chunkZ;

    protected WorldGenerator dirtGen;
    protected WorldGenerator cheeseGen;
    protected WorldGenerator copperGen;
    protected WorldGenerator tinGen;

    public GCMoonBiomeDecorator(BiomeGenBase par1BiomeGenBase)
    {
        this.copperGen = new GCCoreWorldGenMinableMeta(GCMoonBlocks.blockMoon.blockID, 4, 0, true, GCMoonBlocks.blockMoon.blockID, 4);
        this.tinGen = new GCCoreWorldGenMinableMeta(GCMoonBlocks.blockMoon.blockID, 4, 1, true, GCMoonBlocks.blockMoon.blockID, 4);
        this.cheeseGen = new GCCoreWorldGenMinableMeta(GCMoonBlocks.blockMoon.blockID, 3, 2, true, GCMoonBlocks.blockMoon.blockID, 4);
        this.dirtGen = new GCCoreWorldGenMinableMeta(GCMoonBlocks.blockMoon.blockID, 32, 3, true, GCMoonBlocks.blockMoon.blockID, 4);
    }

    public void decorate(World worldObj, Random rand, int chunkX, int chunkZ)
    {
        if (this.worldObj != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.worldObj = worldObj;
            this.randomGenerator = rand;
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.generateMoon();
            this.worldObj = null;
            this.randomGenerator = null;
        }
    }

    protected void genStandardOre1(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY)
    {
        for (int var5 = 0; var5 < amountPerChunk; ++var5)
        {
            final int var6 = this.chunkX + this.randomGenerator.nextInt(16);
            final int var7 = this.randomGenerator.nextInt(maxY - minY) + minY;
            final int var8 = this.chunkZ + this.randomGenerator.nextInt(16);
            worldGenerator.generate(this.worldObj, this.randomGenerator, var6, var7, var8);
        }
    }

    protected void generateMoon()
    {
        MinecraftForge.EVENT_BUS.post(new GCCoreEventPopulate.Pre(this.worldObj, this.randomGenerator, this.chunkX, this.chunkZ));
        this.genStandardOre1(20, this.dirtGen, 0, 200);
        this.genStandardOre1(26, this.copperGen, 0, 60);
        this.genStandardOre1(23, this.tinGen, 0, 60);
        this.genStandardOre1(12, this.cheeseGen, 0, 128);
        MinecraftForge.EVENT_BUS.post(new GCCoreEventPopulate.Post(this.worldObj, this.randomGenerator, this.chunkX, this.chunkZ));
    }
}
