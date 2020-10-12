package lotb.registries;

import lotb.LotbMod;
import lotb.entities.*;
import lotb.entities.item.KnifeEntity;
import lotb.entities.npc.DwarfNPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, LotbMod.MODID);

    //animals passive
    public static final EntityType<Badger> BADGER = reg("badger", EntityType.Builder.<Badger>create(Badger::new, EntityClassification.CREATURE).size(0.6f, 0.7f), 0xf3f2f1, 0x0f0d0a);
    public static final EntityType<Mouse> MOUSE = reg("mouse", EntityType.Builder.<Mouse>create(Mouse::new, EntityClassification.CREATURE).size(0.4f, 0.4f), 0x6f6865, 0x7f7a7a);
    public static final EntityType<HedgeHog> HEDGEHOG = reg("hedgehog", EntityType.Builder.<HedgeHog>create(HedgeHog::new, EntityClassification.CREATURE).size(0.4f, 0.4f), 0x361f07, 0x4f3113);
    public static final EntityType<Squirrel> SQUIRREL = reg("squirrel", EntityType.Builder.<Squirrel>create(Squirrel::new, EntityClassification.CREATURE).size(0.4f, 0.4f), 0x44433a, 0x685647);
    public static final EntityType<Deer> DEER = reg("deer", EntityType.Builder.<Deer>create(Deer::new, EntityClassification.CREATURE).size(0.9f, 1.4f), 0x2e130a, 0x422b13);
    //animals aggresive
    //public static final EntityType<Eagle> GREAT_EAGLE= reg("great_eagle",EntityType.Builder<Eagle>create(Eagle::new,EntityClassification.CREATURE).size(0.9f,1.3f)		 ,0x44433a,0x44431);
    //public static final EntityType<WildWolf> WILD_WOLF	= reg("wild_wolf",EntityType.Builder.<WildWolf>create(WildWolf::new,EntityClassification.MONSTER).size(0.9f,1.3f),0x44433a,0x4443a);
    //public static final EntityType<Warg> WARG				= reg("warg",EntityType.Builder.<Warg>create(Warg::new,EntityClassification.MONSTER).size(0.9f,1.3f)			 ,0x44433a,0x4443a);
    //public static final EntityType<Wight> WIGHT			= reg("wight",EntityType.Builder.<Wight>create(Wight::new,EntityClassification.MONSTER).size(0.9f,1.3f)			 ,0x44433a,0x4443a);
    //charecters
    public static final EntityType<DwarfNPCEntity> DWARF = reg("dwarf", EntityType.Builder.<DwarfNPCEntity>create(DwarfNPCEntity::new, EntityClassification.MISC).size(0.6f, 1.95F), 0x504e57, 0xdbad7d);
    /*public static final EntityType<HumanNpcEntity> HUMAN = reg("human", EntityType.Builder.<HumanNpcEntity>create(HumanNpcEntity::new, EntityClassification.MISC).size(0.6f, 1.95F), 0x6a5030, 0xdbad7d);
    public static final EntityType<ElfNpcEntity> ELF = reg("elf", EntityType.Builder.<ElfNpcEntity>create(ElfNpcEntity::new, EntityClassification.MISC).size(0.6f, 1.95F), 0xffed9c, 0xdbad7d);
    public static final EntityType<OrcNpcEntity> ORC = reg("orc", EntityType.Builder.<OrcNpcEntity>create(OrcNpcEntity::new, EntityClassification.MISC).size(0.6f, 1.95F), 0x161909, 0xdbad7d);
    public static final EntityType<HobbitNpcEntity> HOBBIT = reg("hobbit", EntityType.Builder.<HobbitNpcEntity>create(HobbitNpcEntity::new, EntityClassification.MISC).size(0.6f, 1.95F), 0x9fff38, 0xdbad7d);
*/
    //projectiles
    public static final RegistryObject<EntityType<KnifeEntity>> KNIFE = registerEntity("throwing_knife", KnifeEntity::new);


    /*=================================register commands=================================*/
    //without spawn egg
    public static <T extends Entity> EntityType<T> reg(String name, EntityType.Builder<T> entityBuilder) {
        EntityType<T> entity = entityBuilder.build(new ResourceLocation(LotbMod.MODID, name).toString());
        ENTITIES.register(name, () -> entity);
        return entity;
    }

    //with spawn egg
    public static <T extends Entity> EntityType<T> reg(String name, EntityType.Builder<T> entityBuilder, int baseColour, int spotColour) {
        EntityType<T> entity = entityBuilder.build(new ResourceLocation(LotbMod.MODID, name).toString());
        ModItems.ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(entity, baseColour, spotColour, new Item.Properties().group(ItemGroup.MISC)));
        ENTITIES.register(name, () -> entity);
        return entity;
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.IFactory<T> factory) {
        return ENTITIES.register(name, () -> EntityType.Builder.create(factory, EntityClassification.MISC).size(0.25F, 0.25F).build(LotbMod.MODID + ":" + name));
    }
}
