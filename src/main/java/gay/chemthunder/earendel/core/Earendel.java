package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.core.event.InterceptorInteractionEvents;
import gay.chemthunder.earendel.core.event.TransmitterInteractionEvents;
import gay.chemthunder.earendel.core.index.*;
import gay.chemthunder.earendel.core.networking.clientbound.OpenInterceptorPayload;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import gay.chemthunder.earendel.core.networking.serverbound.ExtractSignalFromInterceptorPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import org.chemthunder.arboreal.api.Arboreal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Earendel implements ModInitializer {
	public static final String MOD_ID = "earendel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Arboreal MAIN = new Arboreal(MOD_ID);

	public void onInitialize() {
//		SignalColors.route();

		EItems.init();
		EBlocks.init();

		EBlockEntityTypes.init();
		EDataComponentTypes.init();
		ETriggerTypes.init();

		ERegistries.init();

		ESignalFlags.init();
		EMonads.init();

		ESoundEvents.init();
		ECreativeModeTabs.init();

		PayloadTypeRegistry.clientboundPlay().register(OpenSignalPayload.TYPE, OpenSignalPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(OpenInterceptorPayload.TYPE, OpenInterceptorPayload.CODEC);

		PayloadTypeRegistry.serverboundPlay().register(ExtractSignalFromInterceptorPayload.TYPE, ExtractSignalFromInterceptorPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(ExtractSignalFromInterceptorPayload.TYPE, new ExtractSignalFromInterceptorPayload.Receiver());

		TransmitterInteractionEvents.init();
		InterceptorInteractionEvents.init();

		LOGGER.info("Hello [1,-4,2,2,-6,-8,3]");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
