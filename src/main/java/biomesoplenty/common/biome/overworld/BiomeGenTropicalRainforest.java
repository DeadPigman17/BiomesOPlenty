package biomesoplenty.common.biome.overworld;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.api.biome.generation.GeneratorStage;
import biomesoplenty.api.biome.generation.GeneratorWeighted;
import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.common.block.BlockBOPCoral;
import biomesoplenty.common.block.BlockBOPDoublePlant;
import biomesoplenty.common.block.BlockBOPLilypad;
import biomesoplenty.common.block.BlockBOPMushroom;
import biomesoplenty.common.block.BlockBOPSand;
import biomesoplenty.common.enums.BOPClimates;
import biomesoplenty.common.enums.BOPFlowers;
import biomesoplenty.common.enums.BOPGems;
import biomesoplenty.common.enums.BOPPlants;
import biomesoplenty.common.enums.BOPTrees;
import biomesoplenty.common.enums.BOPWoods;
import biomesoplenty.common.util.biome.GeneratorUtils.ScatterYMethod;
import biomesoplenty.common.world.BOPWorldSettings;
import biomesoplenty.common.world.feature.GeneratorDoubleFlora;
import biomesoplenty.common.world.feature.GeneratorFlora;
import biomesoplenty.common.world.feature.GeneratorGrass;
import biomesoplenty.common.world.feature.GeneratorLakes;
import biomesoplenty.common.world.feature.GeneratorOreSingle;
import biomesoplenty.common.world.feature.GeneratorSplatter;
import biomesoplenty.common.world.feature.GeneratorSplotches;
import biomesoplenty.common.world.feature.GeneratorWaterside;
import biomesoplenty.common.world.feature.tree.GeneratorBasicTree;

public class BiomeGenTropicalRainforest extends BOPBiome
{
    
    public BiomeGenTropicalRainforest()
    {
        
        // terrain
        this.terrainSettings.avgHeight(67).heightVariation(10, 25);
        
        this.setColor(0x88E140);
        this.setTemperatureRainfall(1.2F, 1.0F);
        this.skyColor = 0xC8CC5F;
        this.waterColorMultiplier = 0x5DFF00;
        
        this.canSpawnInBiome = false;
        this.canGenerateVillages = false;
    
        this.addWeight(BOPClimates.TROPICAL, 7);
        
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        
        // sand
        this.addGenerator("sand", GeneratorStage.SAND_PASS2, (new GeneratorWaterside.Builder()).amountPerChunk(3).maxRadius(7).with(Blocks.sand.getDefaultState()).create());
        
        // quicksand
        this.addGenerator("quicksand", GeneratorStage.SAND, (new GeneratorLakes.Builder()).amountPerChunk(0.2F).waterLakeForBiome(this).liquid(BOPBlocks.sand.getDefaultState().withProperty(BlockBOPSand.VARIANT, BlockBOPSand.SandType.QUICKSAND)).frozenLiquid((IBlockState)null).create());
        
        // trees
        GeneratorWeighted treeGenerator = new GeneratorWeighted(15.0F);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("mahogany", 6, (new GeneratorBasicTree.Builder()).log(BOPWoods.MAHOGANY).leaves(BOPTrees.MAHOGANY).minLeavesRadius(2).minHeight(10).maxHeight(15).create());
        treeGenerator.add("jungle", 2, (new GeneratorBasicTree.Builder()).log(BlockPlanks.EnumType.JUNGLE).leaves(BlockPlanks.EnumType.JUNGLE).minHeight(8).maxHeight(12).vine(Blocks.vine.getDefaultState()).create());
        
        // flowers
        GeneratorWeighted flowerGenerator = new GeneratorWeighted(1.5F);
        this.addGenerator("flowers", GeneratorStage.GRASS, flowerGenerator);
        flowerGenerator.add("orange_cosmos", 4, (new GeneratorFlora.Builder().with(BOPFlowers.ORANGE_COSMOS).create()));
        flowerGenerator.add("dandelion", 1, (new GeneratorFlora.Builder().with(BlockFlower.EnumFlowerType.DANDELION).create()));
        flowerGenerator.add("poppy", 1, (new GeneratorFlora.Builder().with(BlockFlower.EnumFlowerType.POPPY).create()));
        
        // other plants
        this.addGenerator("shrubs", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(BOPPlants.SHRUB).create());
        this.addGenerator("leaf_piles", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.2F).with(BOPPlants.LEAFPILE).generationAttempts(64).create());
        this.addGenerator("poison_ivy", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(BOPPlants.POISONIVY).create());
        this.addGenerator("double_fern", GeneratorStage.FLOWERS, (new GeneratorDoubleFlora.Builder()).amountPerChunk(0.2F).with(BlockDoublePlant.EnumPlantType.FERN).create());
        this.addGenerator("sprouts", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(1.0F).with(BOPPlants.SPROUT).create());
        this.addGenerator("rafflesia", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.3F).with(BOPPlants.RAFFLESIA).create());
        this.addGenerator("melons", GeneratorStage.FLOWERS, (new GeneratorFlora.Builder()).amountPerChunk(0.015625F).placeOn(this.topBlock).with(Blocks.melon_block.getDefaultState()).create());
        
        // shrooms
        this.addGenerator("brown_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.2F).with(Blocks.brown_mushroom.getDefaultState()).create());
        this.addGenerator("red_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.1F).with(Blocks.red_mushroom.getDefaultState()).create());
        
        // water plants
        this.addGenerator("duckweed", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(1.0F).with(BlockBOPLilypad.LilypadType.DUCKWEED).generationAttempts(32).create());
        this.addGenerator("lily", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(Blocks.waterlily.getDefaultState()).create());
        this.addGenerator("medium_lily", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.3F).with(BlockBOPLilypad.LilypadType.MEDIUM).create());
        this.addGenerator("small_lily", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(BlockBOPLilypad.LilypadType.SMALL).create());
        this.addGenerator("tiny_lily", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.5F).with(BlockBOPLilypad.LilypadType.TINY).create());
        this.addGenerator("algae", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(3.0F).replace(Blocks.water).with(BOPBlocks.coral.getDefaultState().withProperty(BlockBOPCoral.VARIANT, BlockBOPCoral.CoralType.ALGAE)).scatterYMethod(ScatterYMethod.AT_GROUND).create());

        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(10.0F);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("tallgrass", 5, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).generationAttempts(128).create());
        grassGenerator.add("mediumgrass", 5, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).generationAttempts(128).create());
        grassGenerator.add("dampgrass", 5, (new GeneratorGrass.Builder()).with(BOPPlants.DAMPGRASS).generationAttempts(128).create());
        grassGenerator.add("fern", 3, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.FERN).create());
        
        // gem
        this.addGenerator("topaz", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.TOPAZ).create());

    }
    
    @Override
    public void applySettings(BOPWorldSettings settings)
    {
        if (!settings.generateBopGems) {this.removeGenerator("topaz");}
        if (!settings.generatePoisonIvy) {this.removeGenerator("poison_ivy");}
        if (!settings.generateQuicksand) {this.removeGenerator("quicksand");}
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        double noise = GRASS_COLOR_NOISE.func_151601_a((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return noise < -0.1D ? 11002176 : 12836929;
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        double noise = GRASS_COLOR_NOISE.func_151601_a((double)pos.getX() * 0.0225D, (double)pos.getZ() * 0.0225D);
        return noise < -0.1D ? 8970560 : 10870849;
    }
    
    // TODO: These 2 are copied from 1.7 - but are they used ever?
    public int getFogColour(BlockPos pos)
    {
        return 0x4AD670;
    }
    
    public float getFogDensity(BlockPos pos)
    {
        return 0.145F;
    }

}
