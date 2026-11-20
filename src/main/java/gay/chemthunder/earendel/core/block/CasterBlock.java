package gay.chemthunder.earendel.core.block;

import gay.chemthunder.earendel.core.block.entity.CasterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class CasterBlock extends BaseEntityBlock {
    public CasterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new CasterBlockEntity(worldPosition, blockState);
    }
}
