package gay.chemthunder.earendel.datagen.providers.client;

import gay.chemthunder.earendel.core.index.ESoundEvents;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

import static gay.chemthunder.earendel.core.Earendel.id;

/**
 * @author Chemthunder
 */
public class EarendelSoundsProvider extends FabricSoundsProvider {
    public EarendelSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(HolderLookup.Provider registryLookup, SoundExporter exporter) {
        exporter.add(ESoundEvents.INTERCEPTOR_PING, sound(id("block/interceptor_ping")));
    }

    private static SoundTypeBuilder sound(Identifier id) {
        return SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.RegistrationBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id));
    }

    public String getName() {
        return "Sounds";
    }
}
