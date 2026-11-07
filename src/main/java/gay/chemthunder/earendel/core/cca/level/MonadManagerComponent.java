package gay.chemthunder.earendel.core.cca.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.ERegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class MonadManagerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<MonadManagerComponent> KEY = ComponentRegistry.getOrCreate(
            Earendel.id("monad_manager"),
            MonadManagerComponent.class
    );
    private final Level level;

    private List<Handler> handlers = new ArrayList<>();

    public MonadManagerComponent(Level level) {
        this.level = level;
    }

    public void tick() {
        for (Handler handler : handlers) {
            if (handler.delay > 0) {
                handler.delay--;
                if (handler.delay == 0) {
                    Earendel.LOGGER.info("Called upon: {}", handler.serializedTarget.getId());

                    handler.bless(level);

                    List<Handler> export = new ArrayList<>(handlers);
                    export.remove(handler);
                    handlers = export;
                    sync();
                }
            }
        }
    }

    public static MonadManagerComponent get(Level level) {
        return MonadManagerComponent.KEY.get(level);
    }

    public void upload(Monad monad, Entity entity) {
        List<Handler> export = new ArrayList<>(handlers);
        export.add(new Handler(monad, entity.getId()));
        handlers = export;
        sync();

        Earendel.LOGGER.info("Successfully uploaded {} to the Monad Manager.", monad.getId());
    }

    public void sync() {
        KEY.sync(level);
    }

    public void readData(ValueInput readView) {
        handlers = readView.read("Handlers", Handler.CODEC.listOf()).orElse(new ArrayList<>());
    }

    public void writeData(ValueOutput writeView) {
        if (!handlers.isEmpty()) writeView.store("Handlers", Handler.CODEC.listOf(), handlers);
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
        this.sync();
    }

    public static class Handler {
        public static final int[] DELAY_REFERENCES = new int[] {
                (6 * 20),
                (10 * 20),
                (4 * 20)
        };

        public final Monad serializedTarget;
        public final int playerId;

        public int delay;

        public Handler(Monad serializedTarget, int playerId) {
            this.serializedTarget = serializedTarget;
            this.playerId = playerId;
            this.delay = DELAY_REFERENCES[new Random().nextInt(0, DELAY_REFERENCES.length)];
        }

        public void bless(Level level) {
            Entity entity = level.getEntity(this.playerId);

            if (entity instanceof LivingEntity living) {
                this.serializedTarget.createBlessing(level, living);
            }
        }

        public Monad getSerializedTarget() {
            return serializedTarget;
        }

        public int getPlayerId() {
            return playerId;
        }

        public static final Codec<Handler> CODEC = RecordCodecBuilder.create(codec -> codec.group(
                ERegistries.MONAD.byNameCodec()
                        .fieldOf("target")
                        .forGetter(Handler::getSerializedTarget),
                Codec.INT
                        .fieldOf("playerId")
                        .forGetter(Handler::getPlayerId)
        ).apply(codec, Handler::new));
    }
}
