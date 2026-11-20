package gay.chemthunder.earendel.core;

import gay.chemthunder.earendel.core.command.ECommand;
import gay.chemthunder.earendel.core.event.CasterInteractionEvents;
import gay.chemthunder.earendel.core.event.InterceptorInteractionEvents;
import gay.chemthunder.earendel.core.event.SpyglassTooltipEvent;
import gay.chemthunder.earendel.core.event.TransmitterInteractionEvents;
import gay.chemthunder.earendel.core.index.*;
import gay.chemthunder.earendel.core.networking.EPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.chemthunder.arboreal.api.core.Arboreal;
import org.chemthunder.arboreal.api.event.server.ItemCallbacks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Earendel implements ModInitializer {
	public static final String MOD_ID = "earendel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Arboreal MAIN = new Arboreal(MOD_ID);

	public static final FabricLoader LOADER = FabricLoader.getInstance();

	public void onInitialize() {
		MAIN.initMain(() -> {
			MAIN.addHandler(EPackets::new);

			MAIN.addHandler(EItems.task);
			MAIN.addHandler(EBlocks.task);

			MAIN.addHandler(EBlockEntityTypes.task);
			MAIN.addHandler(EDataComponentTypes.task);
			MAIN.addHandler(ETriggerTypes.task);

			MAIN.addHandler(EAttachmentTypes.task);

			MAIN.addHandler(ESignalFlags.task);
			MAIN.addHandler(EMonads.task);

			MAIN.addHandler(ESoundEvents.task);
			MAIN.addHandler(ECreativeModeTabs.task);
		});

		ERegistries.init();

		TransmitterInteractionEvents.init();
		InterceptorInteractionEvents.init();
		CasterInteractionEvents.init();

		CreativeModeTabEvents.modifyOutputEvent(ECreativeModeTabs.KEY).register(ECreativeModeTabs::modifyOutput);

		CommandRegistrationCallback.EVENT.register(ECommand::register);

		ItemCallbacks.INJECT_TOOLTIP.register(new SpyglassTooltipEvent());

		LOGGER.info("Hello [1,-4,2,2,-6,-8,3]");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
