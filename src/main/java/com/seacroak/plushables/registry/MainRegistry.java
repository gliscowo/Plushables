package com.seacroak.plushables.registry;

import com.seacroak.plushables.PlushablesMod;
import com.seacroak.plushables.block.BuilderBlock;
import com.seacroak.plushables.block.FoxBlock;
import com.seacroak.plushables.block.PenguinBlock;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MainRegistry {
	// public static final HabitatBlock HABITAT_BLOCK =
	// RegistryUtils.register("habitatblock", new HabitatBlock());
	// public static final FertilizerBlock FERTILIZER_BLOCK =
	// RegistryUtils.register("fertilizerblock",
	// new FertilizerBlock());

	// Default item settings
	static final FabricItemSettings defaultItemSettings = new FabricItemSettings().maxCount(8)
			.group(PlushablesMod.PLUSHABLES_GROUP);
	// Builder Block
	public static final BuilderBlock BUILDER_BLOCK = RegistryHelper.register("builder_block", new BuilderBlock(),
			defaultItemSettings);
	// Penguin Plushable
	public static final PenguinBlock PENGUIN_PLUSHABLE = RegistryHelper.register("penguin_plushable",
			new PenguinBlock(), defaultItemSettings);
	// Fox Plushable
	public static final FoxBlock FOX_PLUSHABLE = RegistryHelper.register("fox_plushable",
			new FoxBlock(), defaultItemSettings);

}
