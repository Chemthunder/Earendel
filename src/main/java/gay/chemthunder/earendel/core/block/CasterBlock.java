package gay.chemthunder.earendel.core.block;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.block.entity.CasterBlockEntity;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.util.EUtil;
import gay.chemthunder.earendel.core.util.SignalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof CasterBlockEntity entity) {
            if (entity.hasAttached(EAttachmentTypes.CASTERS)) {
                ItemStack held = entity.getAttached(EAttachmentTypes.STORED_STACK);

                if (held != null) {
                    if (held.has(EDataComponentTypes.SIGNAL)) {
                        Signal signal = held.get(EDataComponentTypes.SIGNAL);

                        if (signal != null) {
                            List<SignalPos> casters = new ArrayList<>(level.getAttachedOrCreate(EAttachmentTypes.CASTERS));
                            casters.add(new SignalPos(GlobalPos.of(level.dimension(), pos), signal));
                            level.setAttached(EAttachmentTypes.CASTERS, casters);
                        }
                    }
                }
            }
        }
    }

    public void destroy(LevelAccessor iLevel, BlockPos pos, BlockState state) {
        if (iLevel instanceof Level level) {
            EUtil.popPoint(level, pos);
        }
    }
}
