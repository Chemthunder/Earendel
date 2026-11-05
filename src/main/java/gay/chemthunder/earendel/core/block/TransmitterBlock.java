package gay.chemthunder.earendel.core.block;

import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class TransmitterBlock extends BaseEntityBlock {
    public static final BooleanProperty LOADED = BooleanProperty.create("loaded");

    public TransmitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LOADED, false)
        );
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LOADED);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new TransmitterBlockEntity(worldPosition, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level ignoredLevel, BlockState ignoredState, BlockEntityType<T> type) {
        return ((level, pos, state, entity) -> {
            if (entity instanceof TransmitterBlockEntity transmitterBlock) {
                transmitterBlock.tick(level, pos, state);
            }
        });
    }
}
