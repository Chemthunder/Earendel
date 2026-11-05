package gay.chemthunder.earendel.core.block;

import gay.chemthunder.earendel.core.block.entity.InterceptorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class InterceptorBlock extends BaseEntityBlock {
    public InterceptorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new InterceptorBlockEntity(worldPosition, blockState);
    }
}
