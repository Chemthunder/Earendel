package gay.chemthunder.earendel.datagen.providers.client;

import gay.chemthunder.earendel.core.client.item.DriveTintSource;
import gay.chemthunder.earendel.core.index.EBlocks;
import gay.chemthunder.earendel.core.index.EItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

/**
 * @author Chemthunder
 */
public class EarendelModelProvider extends FabricModelProvider {
    public EarendelModelProvider(FabricPackOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.registerSimpleFlatItemModel(EBlocks.TRANSMITTER);
        blockModelGenerators.registerSimpleFlatItemModel(EBlocks.INTERCEPTOR);
        blockModelGenerators.registerSimpleFlatItemModel(EBlocks.CASTER);
    }

    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        createDrive(itemModelGenerators);

        itemModelGenerators.generateFlatItem(EItems.CRYING_RESOLVE, ModelTemplates.FLAT_ITEM);
    }

    private static void createDrive(ItemModelGenerators models) {
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
    }
}
