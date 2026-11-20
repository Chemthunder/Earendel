package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.block.entity.CasterBlockEntity;
import gay.chemthunder.earendel.core.block.entity.InterceptorBlockEntity;
import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface EBlockEntityTypes {
    RegisterTask<BlockEntityType<?>> task = Earendel.MAIN.createTask(RegisterTask::new, BuiltInRegistries.BLOCK_ENTITY_TYPE);

    BlockEntityType<TransmitterBlockEntity> TRANSMITTER = task.register("transmitter", FabricBlockEntityTypeBuilder.create(
            TransmitterBlockEntity::new,
            EBlocks.TRANSMITTER
    ).build());

    BlockEntityType<InterceptorBlockEntity> INTERCEPTOR = task.register("interceptor", FabricBlockEntityTypeBuilder.create(
            InterceptorBlockEntity::new,
            EBlocks.INTERCEPTOR
    ).build());

    BlockEntityType<CasterBlockEntity> CASTER = task.register("caster", FabricBlockEntityTypeBuilder.create(
            CasterBlockEntity::new,
            EBlocks.CASTER
    ).build());
}
