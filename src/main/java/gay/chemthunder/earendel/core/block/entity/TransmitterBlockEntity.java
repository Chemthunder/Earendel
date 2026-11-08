package gay.chemthunder.earendel.core.block.entity;

import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.block.TransmitterBlock;
import gay.chemthunder.earendel.core.cca.level.MonadManagerComponent;
import gay.chemthunder.earendel.core.index.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.chemthunder.arboreal.api.util.LevelUtil;

import java.util.Objects;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class TransmitterBlockEntity extends BlockEntity {
    public static final int BROADCAST_TIME = (5 * 20);

    private boolean fired = false;
    private int broadcastTime = 0;
    private int lastUserId = 0;

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

                            ItemStack stack = this.getAttached(EAttachmentTypes.STORED_STACK);

                            if (stack != null) {
                                stack.remove(EDataComponentTypes.SIGNAL);

                                Containers.dropItemStack(
                                        level,
                                        pos.getX() + 0.5,
                                        pos.getY() + 0.5,
                                        pos.getZ() + 0.5,
                                        Objects.requireNonNull(stack)
                                );
                            }
                            this.removeAttached(EAttachmentTypes.STORED_STACK);

                            Entity user = level.getEntity(lastUserId);

                            if (user != null) {
                                LevelUtil.grantCriterion(ETriggerTypes.BROADCAST_SIGNAL, user);
                            }

                            if (user instanceof LivingEntity living) {
                                if (new Random().nextInt(1, 2) == 1) { // 50/50 chance
                                    MonadManagerComponent manager = MonadManagerComponent.get(level);

                                    for (Monad monad : ERegistries.MONAD) {
                                        if (monad.canBeCalledUpon(level, living, this)) {
                                            manager.upload(monad, living);
                                            break;
                                        }
                                    }
                                }
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
        output.putInt("LastUserId", lastUserId);
    }

    protected void loadAdditional(ValueInput input) {
        fired = input.getBooleanOr("Fired", false);

        broadcastTime = input.getInt("BroadcastTime").orElse(0);
        lastUserId = input.getInt("LastUserId").orElse(0);
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        this.setChanged();
    }

    public void keyTo(Entity entity) {
        this.lastUserId = entity.getId();
        this.setChanged();
    }
}
