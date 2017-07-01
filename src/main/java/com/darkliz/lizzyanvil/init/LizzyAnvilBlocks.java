package com.darkliz.lizzyanvil.init;

import com.darkliz.lizzyanvil.Reference;
import com.darkliz.lizzyanvil.blocks.BlockLizzyAnvil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LizzyAnvilBlocks {

	//* Block Declaration
	public static BlockLizzyAnvil lizzy_anvil;

	// Block Registers
	public static void init() {
		lizzy_anvil = new BlockLizzyAnvil();
		
	}

	// Render Registers
	public static void registerRenders() {

		Item item = Item.getItemFromBlock(lizzy_anvil);

		String variantName = "lizzy_anvil_intact";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));

		variantName = "lizzy_anvil_slightly_damaged";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 1,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));

		variantName = "lizzy_anvil_very_damaged";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 2,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));
	}

	// Registry Functions
	public static void register(Block block) {
		GameRegistry.register(block);
	}

	
	 @SideOnly(Side.CLIENT)
	    public static void initModels(){
		 lizzy_anvil.initModel();
	    }
	
}