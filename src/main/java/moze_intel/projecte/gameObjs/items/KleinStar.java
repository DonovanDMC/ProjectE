package moze_intel.projecte.gameObjs.items;

import moze_intel.projecte.PECore;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class KleinStar extends ItemPE implements IItemEmc
{
	public final EnumKleinTier tier;
	public KleinStar(Builder builder, EnumKleinTier tier)
	{
		super(builder);
		this.tier = tier;
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return stack.hasTag();
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		double starEmc = getEmc(stack);
		
		if (starEmc == 0)
		{
			return 1.0D;
		}
		
		return 1.0D - starEmc / (double) EMCHelper.getKleinStarMaxEmc(stack);
	}

	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && PECore.DEV_ENVIRONMENT)
		{
			setEmc(stack, EMCHelper.getKleinStarMaxEmc(stack));
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	public enum EnumKleinTier
	{
		EIN("ein"),
		ZWEI("zwei"),
		DREI("drei"),
		VIER("vier"),
		SPHERE("sphere"),
		OMEGA("omega");

		public final String name;
		EnumKleinTier(String name)
		{
			this.name = name;
		}
	}

	// -- IItemEmc -- //

	@Override
	public double addEmc(@Nonnull ItemStack stack, double toAdd)
	{
		double add = Math.min(getMaximumEmc(stack) - getStoredEmc(stack), toAdd);
		ItemPE.addEmcToStack(stack, add);
		return add;
	}

	@Override
	public double extractEmc(@Nonnull ItemStack stack, double toRemove)
	{
		double sub = Math.min(getStoredEmc(stack), toRemove);
		ItemPE.removeEmc(stack, sub);
		return sub;
	}

	@Override
	public double getStoredEmc(@Nonnull ItemStack stack)
	{
		return ItemPE.getEmc(stack);
	}

	@Override
	public double getMaximumEmc(@Nonnull ItemStack stack)
	{
		return EMCHelper.getKleinStarMaxEmc(stack);
	}
}
