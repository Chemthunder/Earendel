package gay.chemthunder.earendel.core.networking;

import gay.chemthunder.earendel.core.networking.clientbound.OpenInterceptorPayload;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import gay.chemthunder.earendel.core.networking.clientbound.SyncCastersPayload;
import gay.chemthunder.earendel.core.networking.clientbound.TweakSignalPayload;
import gay.chemthunder.earendel.core.networking.serverbound.ApplySignalTweakPayload;
import gay.chemthunder.earendel.core.networking.serverbound.ExtractSignalFromInterceptorPayload;
import gay.chemthunder.earendel.core.networking.serverbound.GrantLocateCasterPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.arboreal.api.core.task.NetworkingTask;

/**
 * @author Chemthunder
 */
public class EPackets extends NetworkingTask {
    public void registerTypes() {
        PayloadTypeRegistry.serverboundPlay().register(ExtractSignalFromInterceptorPayload.TYPE, ExtractSignalFromInterceptorPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(GrantLocateCasterPayload.TYPE, GrantLocateCasterPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ApplySignalTweakPayload.TYPE, ApplySignalTweakPayload.CODEC);

        PayloadTypeRegistry.clientboundPlay().register(OpenSignalPayload.TYPE, OpenSignalPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(OpenInterceptorPayload.TYPE, OpenInterceptorPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncCastersPayload.TYPE, SyncCastersPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(TweakSignalPayload.TYPE, TweakSignalPayload.CODEC);
    }

    public void serverbound() {
        ServerPlayNetworking.registerGlobalReceiver(ExtractSignalFromInterceptorPayload.TYPE, new ExtractSignalFromInterceptorPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(GrantLocateCasterPayload.TYPE, new GrantLocateCasterPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(ApplySignalTweakPayload.TYPE, new ApplySignalTweakPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    public void clientbound() {
        ClientPlayNetworking.registerGlobalReceiver(OpenSignalPayload.TYPE, new OpenSignalPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenInterceptorPayload.TYPE, new OpenInterceptorPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(SyncCastersPayload.TYPE, new SyncCastersPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(TweakSignalPayload.TYPE, new TweakSignalPayload.Receiver());
    }
}
