package gay.chemthunder.earendel.datagen.providers.client;

import gay.chemthunder.earendel.core.index.EItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class EarendelLanguageProvider extends FabricLanguageProvider {
    public EarendelLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(EItems.DRIVE, "Drive");
    }
}
