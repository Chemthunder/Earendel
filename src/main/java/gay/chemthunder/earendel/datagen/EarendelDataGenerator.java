package gay.chemthunder.earendel.datagen;

import gay.chemthunder.earendel.datagen.providers.client.EarendelLanguageProvider;
import gay.chemthunder.earendel.datagen.providers.client.EarendelModelProvider;
import gay.chemthunder.earendel.datagen.providers.client.EarendelSoundsProvider;
import gay.chemthunder.earendel.datagen.providers.server.EarendelAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * @author Chemthunder
 */
public class EarendelDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();

		pack.addProvider(EarendelLanguageProvider::new);
		pack.addProvider(EarendelModelProvider::new);
		pack.addProvider(EarendelSoundsProvider::new);

		pack.addProvider(EarendelAdvancementProvider::new);
	}
}
