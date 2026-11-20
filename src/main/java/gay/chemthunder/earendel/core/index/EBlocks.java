package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.block.CasterBlock;
import gay.chemthunder.earendel.core.block.InterceptorBlock;
import gay.chemthunder.earendel.core.block.TransmitterBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface EBlocks {
    RegisterTask<Block> task = Earendel.MAIN.createTask(RegisterTask::new, BuiltInRegistries.BLOCK);

    Block TRANSMITTER = task.register("transmitter", new TransmitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_BULB)
            .setId(task.keyOf("transmitter"))
    ));

    Block INTERCEPTOR = task.register("interceptor", new InterceptorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_BULB)
            .setId(task.keyOf("interceptor"))
    ));

    Block CASTER = task.register("caster", new CasterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_GRATE)
            .setId(task.keyOf("caster"))
    ));
}
