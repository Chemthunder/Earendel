package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.core.event.TransmitterInteractionEvents;
import gay.chemthunder.earendel.core.index.*;
import gay.chemthunder.earendel.core.networking.EPacketNetwork;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import org.chemthunder.arboreal.api.Arboreal;
import org.chemthunder.arboreal.api.networking.PacketNetwork;
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

		PacketNetwork.registerNetwork(new EPacketNetwork());

		PayloadTypeRegistry.clientboundPlay().register(OpenSignalPayload.TYPE, OpenSignalPayload.CODEC);

		TransmitterInteractionEvents.init();

		LOGGER.info("Hello [1,-4,2,2,-6,-8,3]");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
