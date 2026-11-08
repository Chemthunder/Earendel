package gay.chemthunder.earendel.core.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SightOmenParticle extends SimpleAnimatedParticle {
    public SightOmenParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0.0125F);

        this.xd = xa;
        this.yd = ya;
        this.zd = za;

        this.lifetime = 72000;

        this.setSpriteFromAge(sprites);
    }

    public void move(final double xa, final double ya, final double za) {
        this.setBoundingBox(this.getBoundingBox().move(xa, ya, za));
        this.setLocationFromBoundingbox();
    }

    public void tick() {
        super.tick();

        this.roll += 0.01F;

        if (this.alpha > 0.0F) {
            this.alpha -= 0.01F;
            if (this.alpha <= 0.0F) {
                this.remove();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(final SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(final SimpleParticleType options, final ClientLevel level, final double x, final double y, final double z, final double xAux, final double yAux, final double zAux, final RandomSource random) {
            return new SightOmenParticle(level, x, y, z, xAux, yAux, zAux, this.sprites);
        }
    }
}
