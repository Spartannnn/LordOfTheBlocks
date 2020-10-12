package lotb.entities.npc.ai.brain.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class PickUpStuffTask extends Task<LivingEntity>{
	private List<ItemEntity> items = Lists.newArrayList();

	public PickUpStuffTask() {
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
	}

	@Override
	protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
		items = worldIn.getEntitiesWithinAABB(ItemEntity.class, owner.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
		return !items.isEmpty();
	}

	@Override
	protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
		ItemEntity itementity = items.get(worldIn.rand.nextInt(items.size()));
		if (itementity.getItem().getItem() == Items.GOLD_INGOT) {
			Vec3d vec3d = itementity.getPositionVec();
			entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(new BlockPos(vec3d)));
			entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, 0.5F, 0));
		}
	}
}

