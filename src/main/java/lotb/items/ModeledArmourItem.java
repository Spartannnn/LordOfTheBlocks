package lotb.items;

import lotb.client.armour.ModeledArmour;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("rawtypes")
public class ModeledArmourItem extends ArmorItem {
	public ModeledArmour model;

	public ModeledArmourItem(IArmorMaterial _material, EquipmentSlotType _slot , ModeledArmour _model, Properties _builder) {
		super(_material, _slot, _builder);
		model=_model;
		model.setSlot(_slot);
	}
	@OnlyIn(Dist.CLIENT) @SuppressWarnings("unchecked")
	@Override public  BipedModel getArmorModel(LivingEntity _entityLiving, ItemStack _stack, EquipmentSlotType _slot, BipedModel _default) {
		return model;
	}

}
