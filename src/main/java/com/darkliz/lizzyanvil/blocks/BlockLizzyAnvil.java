package com.darkliz.lizzyanvil.blocks;

import com.darkliz.lizzyanvil.LizzyAnvil;
import com.darkliz.lizzyanvil.Reference;
import com.darkliz.lizzyanvil.init.LizzyCreativeTab;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLizzyAnvil extends BlockFalling {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);

	public BlockLizzyAnvil() {
		super(Material.ANVIL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DAMAGE,
				Integer.valueOf(0)));
		this.setLightOpacity(0);
		setRegistryName("lizzy_anvil");
		this.setCreativeTab(LizzyCreativeTab.LizzyTab);
		this.setHardness(5.0F);
		this.setSoundType(SoundType.ANVIL);
		this.setResistance(2000.0F);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (!worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false)) {
				playerIn.openGui(LizzyAnvil.instance, LizzyAnvil.guiIDLizzyAnvil, worldIn, pos.getX(), pos.getY(),
						pos.getZ());
			}
		}

		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();

		try {
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
					.withProperty(FACING, enumfacing).withProperty(DAMAGE, Integer.valueOf(meta >> 2));
		} catch (IllegalArgumentException var11) {
			if (!worldIn.isRemote) {
				// LOGGER.warn(String.format("Invalid damage property for anvil
				// at %s. Found %d, must be in [0, 1, 2]", new Object[] {pos,
				// Integer.valueOf(meta >> 2)}));

				if (placer instanceof EntityPlayer) {
					placer.sendMessage(new TextComponentTranslation("Invalid damage property. Please pick in [0, 1, 2]",
							new Object[0]));
				}
			}

			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer)
					.withProperty(FACING, enumfacing).withProperty(DAMAGE, Integer.valueOf(0));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((Integer) state.getValue(DAMAGE)).intValue();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
		return enumfacing.getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(itemIn));
		list.add(new ItemStack(itemIn, 1, 1));
		list.add(new ItemStack(itemIn, 1, 2));
	}

	@Override
	protected void onStartFalling(EntityFallingBlock fallingEntity) {
		fallingEntity.setHurtEntities(true);
	}

	@Override
	public void onEndFalling(World worldIn, BlockPos pos) {
		worldIn.playEvent(1031, pos, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(DAMAGE,
				Integer.valueOf((meta & 15) >> 2));
	}

	// Convert the BlockState into the correct metadata value
	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		i |= ((Integer) state.getValue(DAMAGE)).intValue() << 2;
		return i;

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, DAMAGE });
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		Item item = Item.getItemFromBlock(this);

		String variantName = "lizzy_anvil_intact";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelLoader.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));

		variantName = "lizzy_anvil_slightly_damaged";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 1,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelLoader.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));

		variantName = "lizzy_anvil_very_damaged";
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 2,
				new ModelResourceLocation(Reference.MOD_ID + ":" + variantName, "inventory"));
		ModelLoader.registerItemVariants(item, new ResourceLocation(Reference.MOD_ID, variantName));
	}

}