package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.api.event.ExtractItemEvent;
import gay.chemthunder.earendel.core.client.event.DrawArtifactBackgroundEvent;
import gay.chemthunder.earendel.core.client.event.TestPointHudEvent;
import gay.chemthunder.earendel.core.client.event.TestPointRenderingEvent;
import gay.chemthunder.earendel.core.client.item.DriveTintSource;
import gay.chemthunder.earendel.core.index.client.EModelLayerLocations;
import gay.chemthunder.earendel.core.index.EParticleTypes;
import gay.chemthunder.earendel.core.networking.EPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.color.item.ItemTintSources;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class EarendelClient implements ClientModInitializer {
    private static boolean lerpingWaveState = false;
    public static float lerpingWave = 0.0F;

    public static final double INTERCEPTOR_DISTANCE = 965.43;

    public void onInitializeClient() {
        EParticleTypes.clinit();
        EModelLayerLocations.clinit();

        EPackets.receiveClientbound();

        ItemTintSources.ID_MAPPER.put(Earendel.id("signal_tinter"), DriveTintSource.MAP_CODEC);

        ExtractItemEvent.EVENT.register(new DrawArtifactBackgroundEvent());
        LevelRenderEvents.END_MAIN.register(new TestPointRenderingEvent());

        HudElementRegistry.addFirst(Earendel.id("test_point"), new TestPointHudEvent());

        ClientTickEvents.START_CLIENT_TICK.register((_ -> {
            if (!lerpingWaveState) {
                if (lerpingWave < 1.0F) {
                    lerpingWave += 0.01F;
                    if (lerpingWave >= 1.0F) {
                        lerpingWaveState = true;
                    }
                }
            } else {
                if (lerpingWave > 0.0F) {
                    lerpingWave -= 0.01F;
                    if (lerpingWave <= 0.0F) {
                        lerpingWaveState = false;
                    }
                }
            }
        }));
    }
}
