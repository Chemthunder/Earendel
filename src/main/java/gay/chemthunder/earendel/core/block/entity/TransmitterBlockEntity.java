package gay.chemthunder.earendel.core.block.entity;

import gay.chemthunder.earendel.core.block.TransmitterBlock;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.EBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * @author Chemthunder
 */
public class TransmitterBlockEntity extends BlockEntity {
    public static final int BROADCAST_TIME = (5 * 20);

    private boolean fired = false;
    private int broadcastTime = 0;

    public TransmitterBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(EBlockEntityTypes.TRANSMITTER, worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (fired) {
            if (this.hasAttached(EAttachmentTypes.STORED_STACK) && state.getValue(TransmitterBlock.LOADED)) {
                if (level.isDarkOutside()) {
                    if (broadcastTime < BROADCAST_TIME) {
                        broadcastTime++;
                        if (broadcastTime >= BROADCAST_TIME) {
                            fired = false;
                            broadcastTime = 0;
                            this.setChanged();

                            level.setBlockAndUpdate(pos, state.setValue(TransmitterBlock.LOADED, false));
                            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS);

                            if (level instanceof ServerLevel serverLevel) {
                                serverLevel.sendParticles(
                                        ParticleTypes.END_ROD,
                                        pos.getX() + 0.5F,
                                        pos.getY() + ((((double) BROADCAST_TIME) / 5) - 4),
                                        pos.getZ() + 0.5F,
                                        15,
                                        0,
                                        0,
                                        0,
                                        0.2F
                                );
                            }
                        }

                        level.addParticle(
                                ParticleTypes.END_ROD,
                                pos.getX() + 0.5F,
                                pos.getY() + 1.0F,
                                pos.getZ() + 0.5F,
                                0,
                                ((double) broadcastTime / BROADCAST_TIME) * 2,
                                0
                        );
                    }
                }
            }
        }
    }

    protected void saveAdditional(ValueOutput output) {
        output.putBoolean("Fired", fired);
        output.putInt("BroadcastTime", broadcastTime);
    }

    protected void loadAdditional(ValueInput input) {
        fired = input.getBooleanOr("Fired", false);
        broadcastTime = input.getInt("BroadcastTime").orElse(0);
    }

    public int getBroadcastTime() {
        return broadcastTime;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        this.setChanged();
    }
}
