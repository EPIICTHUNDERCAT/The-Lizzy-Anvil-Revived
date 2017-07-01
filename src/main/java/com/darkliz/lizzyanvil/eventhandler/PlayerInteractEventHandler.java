package com.darkliz.lizzyanvil.eventhandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerInteractEventHandler {

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {

		EntityPlayer player = event.getEntityPlayer();
	//	Action action = event.action;
		 EnumActionResult cancellationResult = EnumActionResult.PASS;
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		// EnumFacing face = event.face; // Can be null if unknown

		if (cancellationResult == EnumActionResult.PASS) {
			Block blockClicked = world.getBlockState(pos).getBlock();

			if (blockClicked != null && blockClicked == Blocks.ANVIL) {
				if (!player.isSneaking()) {
					event.setCanceled(true);
				} else if (player.getHeldItemMainhand() == null) {
					event.setCanceled(true);
				}
			}
		}
	}
}
