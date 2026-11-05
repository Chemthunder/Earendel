package gay.chemthunder.earendel.datagen;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.item.DriveTintSource;
import gay.chemthunder.earendel.core.index.EItems;
import net.fabricmc.fabric.api.advancement.v1.FabricAdvancementBuilder;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

/**
 * @author Chemthunder
 */
public class EarendelDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Earendel.MAIN.createDataPack(fabricDataGenerator)
				.translations((_, translationBuilder) -> {
					translationBuilder.add(EItems.DRIVE, "Drive");
				})
				.models(models -> {
					Identifier id = ModelLocationUtils.getModelLocation(EItems.DRIVE);

					models.itemModelOutput.accept(EItems.DRIVE,
							ItemModelUtils.tintedModel(
									ModelTemplates.TWO_LAYERED_ITEM.create(
											EItems.DRIVE,
											TextureMapping.layered(
													new Material(id.withPath(s -> s + "_overlay")),
													new Material(id)
											),
											models.modelOutput
									),
									new DriveTintSource()
							)
					);
				});
	}
}
