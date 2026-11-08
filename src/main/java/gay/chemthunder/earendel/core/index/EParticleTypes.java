package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EParticleTypes {
    Index<ParticleType<?>> index = Earendel.MAIN.createIndex(BuiltInRegistries.PARTICLE_TYPE);

//    SimpleParticleType SIGHT_OMEN = index.register("sight_omen", FabricParticleTypes.simple(true));

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clinit() {
//        ParticleProviderRegistry.getInstance().register(SIGHT_OMEN, SightOmenParticle.Provider::new);
    }
}
