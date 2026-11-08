package gay.chemthunder.earendel.core.networking;

import gay.chemthunder.earendel.core.networking.clientbound.OpenInterceptorPayload;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import gay.chemthunder.earendel.core.networking.serverbound.ExtractSignalFromInterceptorPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * @author Chemthunder
 */
public interface EPackets {
    static void typePayloads() {
        PayloadTypeRegistry.clientboundPlay().register(OpenSignalPayload.TYPE, OpenSignalPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(OpenInterceptorPayload.TYPE, OpenInterceptorPayload.CODEC);

        PayloadTypeRegistry.serverboundPlay().register(ExtractSignalFromInterceptorPayload.TYPE, ExtractSignalFromInterceptorPayload.CODEC);
    }

    static void receiveServerbound() {
        ServerPlayNetworking.registerGlobalReceiver(ExtractSignalFromInterceptorPayload.TYPE, new ExtractSignalFromInterceptorPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void receiveClientbound() {
        ClientPlayNetworking.registerGlobalReceiver(OpenSignalPayload.TYPE, new OpenSignalPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenInterceptorPayload.TYPE, new OpenInterceptorPayload.Receiver());
    }
}
