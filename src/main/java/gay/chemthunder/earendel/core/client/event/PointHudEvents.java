package gay.chemthunder.earendel.core.client.event;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.networking.serverbound.GrantLocateCasterPayload;
import gay.chemthunder.earendel.core.util.SignalPos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PointHudEvents {
    public static float pointScale = 0.0F;

    public static @Nullable SignalPos recordedSignal = null;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Earendel.id("point_hud"), new Hud());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(Minecraft client) {
            Player player = client.player;
            if (player == null) return;

            Level local = client.level;
            if (local == null) return;

            pointScale = Mth.clamp(pointScale, 0.0F, 0.95F);

            if (player.getActiveItem().is(Items.SPYGLASS) && player.isUsingItem()) {
                if (pointScale < 1.0F) {
                    pointScale += 0.05F;
                }
            } else {
                pointScale = 0.0F;
            }

            if (player.getActiveItem().is(Items.SPYGLASS) && player.isUsingItem()) {
                HitResult viewCast = player.pick(512, 0.0F, true);
                Vec3 tail = viewCast.getLocation();

                final float inflation = 5;

                for (SignalPos origin : local.getAttachedOrCreate(EAttachmentTypes.CASTERS)) {
                    BlockPos.MutableBlockPos spawnPos = new BlockPos.MutableBlockPos(
                            origin.pos().pos().getX() - (inflation / 2),
                            origin.pos().pos().getY() - (inflation / 2),
                            origin.pos().pos().getZ() - (inflation / 2)
                    );

                    AABB detectionRange = new AABB(spawnPos).inflate(inflation);

                    if (detectionRange.clip(player.position(), tail).isPresent()) {
                        recordedSignal = origin;
                        ClientPlayNetworking.send(new GrantLocateCasterPayload());
                        return;
                    }
                }
            }
            recordedSignal = null;
        }
    }

    public static class Hud implements HudElement {
        public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;

            if (recordedSignal != null) {
                graphics.centeredText(
                        font,
                        Component.literal(recordedSignal.signal().toString()),
                        graphics.guiWidth() / 2,
                        graphics.guiHeight() / 2 - 60,
                        0xFFffffff
                );
            }
        }
    }
}
