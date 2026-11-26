package gay.chemthunder.earendel.core.client.screen;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.networking.serverbound.ApplySignalTweakPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class TweakerScreen extends Screen {
    private final ItemStack stack;

    private final List<SignalTweak> tweaks = new ArrayList<>();

    public TweakerScreen(ItemStack stack) {
        super(Component.empty());
        this.stack = stack;
    }

    protected void init() {
        this.tweaks.clear();

        Signal signal = this.stack.get(EDataComponentTypes.SIGNAL);

        if (signal != null) {
            for (int i = 0; i < signal.bits().size(); i++) {
                SignalTweak cycler = new SignalTweak(
                        (this.width / 2 + (i * 20)) - ((signal.bits().size() * 20) / 2),
                        this.height / 2,
                        signal.bits().get(i)
                );

                this.tweaks.add(cycler);
            }
        }
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        for (SignalTweak c : this.tweaks) {
            c.extractRenderState(graphics, mouseX, mouseY, a);
        }
    }

    public void onClose() {
        super.onClose();

        List<Integer> output = new ArrayList<>();

        for (SignalTweak c : this.tweaks) {
            output.add(c.value);
        }

        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.VAULT_EJECT_ITEM, 0.8F));
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.AMETHYST_BLOCK_BREAK, 0.8F));

        ClientPlayNetworking.send(new ApplySignalTweakPayload(this.stack, output));
    }

    public class SignalTweak implements Renderable {
        public final Button up;
        public final Button down;

        public final int x;
        public final int y;

        public int value;

        public SignalTweak(int x, int y, int initialValue) {
            this.x = x;
            this.y = y;

            this.up = Button.builder(Component.literal("Up"), (_) -> this.cycleUp())
                    .pos(x, y - 32)
                    .size(16, 16)
                    .build();

            this.down = Button.builder(Component.literal("Down"), (_) -> this.cycleDown())
                    .pos(x, y + 32)
                    .size(16, 16)
                    .build();

            TweakerScreen.this.addRenderableWidget(this.up);
            TweakerScreen.this.addRenderableWidget(this.down);

            this.value = initialValue;
        }

        public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            this.up.extractRenderState(graphics, mouseX, mouseY, a);
            this.down.extractRenderState(graphics, mouseX, mouseY, a);

            graphics.text(
                    Minecraft.getInstance().font,
                    Component.literal(this.value + ""),
                    this.x,
                    this.y,
                    0xFFffffff,
                    false
            );
        }

        private void cycleUp() {
            if (this.value < Signal.MAX_BIT_VALUE) {
                this.value++;
            }
        }

        private void cycleDown() {
            if (this.value > -Signal.MAX_BIT_VALUE) {
                this.value--;
            }
        }
    }
}
