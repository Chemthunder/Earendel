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
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EBlocks {
    Index<Block> index = Earendel.MAIN.createIndex(BuiltInRegistries.BLOCK);

    Block TRANSMITTER = index.register("transmitter", new TransmitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_BULB)
            .setId(index.keyOf("transmitter"))
    ));

    Block INTERCEPTOR = index.register("interceptor", new InterceptorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_BULB)
            .setId(index.keyOf("interceptor"))
    ));

    Block CASTER = index.register("caster", new CasterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .sound(SoundType.COPPER_GRATE)
            .setId(index.keyOf("caster"))
    ));

    static void init() {}
}
