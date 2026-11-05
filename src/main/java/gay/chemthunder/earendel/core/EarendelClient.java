package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.core.client.item.DriveTintSource;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.color.item.ItemTintSources;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class EarendelClient implements ClientModInitializer {
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OpenSignalPayload.TYPE, new OpenSignalPayload.Receiver());

        ItemTintSources.ID_MAPPER.put(Earendel.id("signal_tinter"), DriveTintSource.MAP_CODEC);
    }
}
