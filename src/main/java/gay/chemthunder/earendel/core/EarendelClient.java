package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.core.client.item.DriveTintSource;
import gay.chemthunder.earendel.core.networking.clientbound.OpenInterceptorPayload;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.color.item.ItemTintSources;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class EarendelClient implements ClientModInitializer {
    private static boolean lerpingWaveState = false;
    public static float lerpingWave = 0.0F;

    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OpenSignalPayload.TYPE, new OpenSignalPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenInterceptorPayload.TYPE, new OpenInterceptorPayload.Receiver());

        ItemTintSources.ID_MAPPER.put(Earendel.id("signal_tinter"), DriveTintSource.MAP_CODEC);

        ClientTickEvents.START_CLIENT_TICK.register((client -> {
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
