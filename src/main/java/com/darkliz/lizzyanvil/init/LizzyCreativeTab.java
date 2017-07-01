package com.darkliz.lizzyanvil.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LizzyCreativeTab extends CreativeTabs{
	public LizzyCreativeTab(int index, String label) {
		super(index, label);
	}

	public static final LizzyCreativeTab LizzyTab = new LizzyCreativeTab(CreativeTabs.getNextID(), "lizzyTab") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(LizzyAnvilBlocks.lizzy_anvil);
		}
	};
	
	@Override
	public ItemStack getTabIconItem() {
		return null;
	}
}
