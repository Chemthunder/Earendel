package gay.chemthunder.earendel.core.block.entity;

import gay.chemthunder.earendel.core.index.EBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Chemthunder
 */
public class CasterBlockEntity extends BlockEntity {
    public CasterBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(EBlockEntityTypes.CASTER, worldPosition, blockState);
    }
}
