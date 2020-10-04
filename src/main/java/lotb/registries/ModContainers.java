package lotb.registries;

import lotb.LotbMod;
import lotb.inventory.RohanWorkBenchContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS,LotbMod.MODID);
	
	public static final ContainerType<RohanWorkBenchContainer> ROHAN_WORKBENCH = register(new ContainerType<>(RohanWorkBenchContainer::new),"rohan_workbench");

	public static <C extends Container> ContainerType<C> register(ContainerType<C> type,String name) {
		CONTAINERS.register(name,()->type);
		return type;
	}
}
