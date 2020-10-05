package lotb.registries.materials;

import lotb.LotbMod;
import lotb.registries.ModItems;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmourTiers implements IArmorMaterial{
	FOX		("fox_fur"	, 5,new int[] {1,2,3,1},15,SoundEvents.BLOCK_CHEST_LOCKED,0.5f,ModItems.MITHRIL_INGOT.get()),
	WOLF	("wolf_fur"	, 5,new int[] {1,2,3,1},15,SoundEvents.BLOCK_CHEST_LOCKED,0.5f,ModItems.MITHRIL_INGOT.get()),
	BADGER	("badger_fur",5,new int[] {1,2,3,1},15,SoundEvents.BLOCK_CHEST_LOCKED,0.5f,ModItems.MITHRIL_INGOT.get()),
	MITHRIL		("mithril"		,52,new int[] {4,7,9,4},25,SoundEvents.BLOCK_CHEST_LOCKED,3.0f,ModItems.MITHRIL_INGOT.get()),
	MITHRILCHAIN("mithril_chain",50,new int[] {4,7,8,3},29,SoundEvents.BLOCK_CHEST_LOCKED,1.0f,ModItems.MITHRIL_INGOT.get()),
	//faction
	GONDOR("gondor"	,18,new int[] {2,5,6,3},8,SoundEvents.BLOCK_CHEST_LOCKED,1f,Items.IRON_INGOT),
	ROHAN("rohan"	,18,new int[] {2,5,6,3},8,SoundEvents.BLOCK_CHEST_LOCKED,1f,Items.IRON_INGOT),
	MORDOR("mordor"	,18,new int[] {2,5,6,3},8,SoundEvents.BLOCK_CHEST_LOCKED,1f,Items.IRON_INGOT),
	URUK("uruk"		,18,new int[] {2,5,6,3},8,SoundEvents.BLOCK_CHEST_LOCKED,1f,Items.IRON_INGOT);
	
	private static final int[] PIECE_DURABILITIES = new int[] {13, 15, 16, 11};
	private final String name;
	private final int durability;
	private final int[] damageReduction;
	private final int enchantability;
	private final SoundEvent armourOnSound;
	private final float toughness;
	private final LazyValue<Ingredient> repairItem;
	
	private ModArmourTiers(String _name, int _maxDamage, int[] _dam , int _enchant, SoundEvent _sound, float _tough, Item _repair) {
		name=LotbMod.MODID+':'+_name;
		durability=_maxDamage;
		damageReduction=_dam;
		enchantability=_enchant;
		armourOnSound=_sound;
		toughness=_tough;
		repairItem= new LazyValue<>(() -> { return Ingredient.fromItems(_repair); });
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return PIECE_DURABILITIES[slotIn.getIndex()]*durability;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return damageReduction[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return armourOnSound;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairItem.getValue();
	}

	@OnlyIn(Dist.CLIENT) @Override
	public String getName() {
		return name;
	}

	@Override
	public float getToughness() {
		return toughness;
	}
	
}
