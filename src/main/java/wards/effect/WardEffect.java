package wards.effect;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.potion.Potion;

public class WardEffect extends Potion
{
	private Enchantment enchant;
	
	public WardEffect(Enchantment enchant)
	{
		super(false, 8171462);
		this.setBeneficial();
		this.setIconIndex(6, 1);
		this.enchant = enchant;
	}

	public static WardEffect byEnchant(Enchantment enchant)
	{
		for(Potion effect : WardEffectManager.EFFECTS)
		{
			if(((WardEffect)effect).getEnchant() == enchant)
				return (WardEffect)effect;
		}
		
		return byEnchant(Enchantments.SHARPNESS); //fall back on sharpness so it doesn't crash modded enchants that aren't support yet
	}
	
	public Enchantment getEnchant()
	{
		return this.enchant;
	}
}
