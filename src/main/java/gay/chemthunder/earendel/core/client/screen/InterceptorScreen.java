package gay.chemthunder.earendel.core.client.screen;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.EarendelClient;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.index.ESoundEvents;
import gay.chemthunder.earendel.core.networking.serverbound.ExtractSignalFromInterceptorPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class InterceptorScreen extends Screen {
    private final Minecraft minecraft;
    private final ItemStack stack;
    private final GlobalPos pos;

    private final List<Function<GuiGraphicsExtractor, Renderable>> drawables = new ArrayList<>();

    private Button deploySignal;
    private Button extractSignal;

    private boolean waveActive = false;
    private float waveOpacity = 1.0F;
    private int waveScale = 1;

    private boolean sineState = true;
    private float sineWave = 0.0F;

    private boolean waveIncoming = false;

    private int waveDelay = 20;
    private boolean scalingOut = false;

    public InterceptorScreen(ItemStack stack, GlobalPos pos) {
        super(Component.empty());
        this.pos = pos;
        this.minecraft = Minecraft.getInstance();
        this.stack = stack;
    }

    public void tick() {
        if (waveActive) {
            if (!waveIncoming) {
                waveScale++;
            } else {
                if (waveScale > 0) {
                    waveScale--;
                    if (waveScale <= 0) {
                        ClientLevel level = minecraft.level;

                        if (level != null) {
                            Optional<ResourceKey<Biome>> optional = level.getBiome(this.pos.pos()).unwrapKey();
                            optional.ifPresent(biomeResourceKey ->
                                    stack.set(EDataComponentTypes.SIGNAL, new Signal(biomeResourceKey))
                            );
                        }
                    }
                }
            }

            waveOpacity = Math.clamp(waveOpacity, 0.0F, 1.0F);

            if (waveOpacity > 0.0F) {
                if (!waveIncoming) {
                    waveOpacity -= 0.025F;
                } else {
                    waveOpacity -= 0.010F;
                }
                if (waveOpacity <= 0.0F) {
                    scalingOut = true;
                }
            }

            if (scalingOut) {
                if (waveDelay > 0) {
                    waveDelay--;
                    if (waveDelay == 0) {
                        waveIncoming = true;
                        waveOpacity = 1.0F;
                    }
                }
            }
        }

        if (sineState) {
            if (sineWave < 1.0F) {
                sineWave += 0.05F;
                if (sineWave >= 1.0F) {
                    sineState = false;
                }
            }
        } else {
            if (sineWave > 0.0F) {
                sineWave -= 0.05F;
                if (sineWave <= 0.0F) {
                    sineState = true;
                }
            }
        }
    }

    protected void init() {
        this.drawables.clear();

        deploySignal = Button.builder(Component.literal("Deploy"), (this::deploySignal))
                .size(48, 48)
                .pos((width / 2 - 24) - 32, height / 2 + 60)
                .tooltip(Tooltip.create(Component.literal("Deploys the Interceptor, capturing any nearby signals in the atmosphere.")))
                .build();

        this.drawables.add(_ -> deploySignal);
        this.addRenderableWidget(deploySignal);

        extractSignal = Button.builder(Component.literal("Extract"), (_ -> this.onClose()))
                .size(48, 48)
                .pos((width / 2 - 24) + 32, height / 2 + 60)
                .tooltip(Tooltip.create(Component.literal("Extracts the current Drive from the Interceptor.")))
                .build();

        this.drawables.add(_ -> extractSignal);
        this.addRenderableWidget(extractSignal);
    }

    private void deploySignal(Button button) {
        if (!waveActive) {
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(ESoundEvents.INTERCEPTOR_PING, 1.0F));

            this.waveActive = true;
            this.waveOpacity = 1.0F;
            this.waveScale = 1;
        }
    }

    public void onClose() {
        super.onClose();
        ClientPlayNetworking.send(new ExtractSignalFromInterceptorPayload(stack, this.pos));
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.drawables.forEach(func -> func.apply(graphics));

        Matrix3x2fStack matrix = graphics.pose();

        Font font = minecraft.font;

        if (stack.has(EDataComponentTypes.SIGNAL)) {
            Signal signal = stack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                Component bits = Component.empty()
                        .append(Component.literal(signal.bits().toString()).withStyle(ChatFormatting.LIGHT_PURPLE));

                Component biomeHolder = Component.empty()
                                .append(Component.literal(signal.biomeKey().identifier().getPath()).withStyle(ChatFormatting.LIGHT_PURPLE));

                graphics.text(
                        font,
                        bits,
                        (width / 2) - (font.width(bits) / 2),
                        height / 2 - 70,
                        0xFFffffff
                );

                graphics.text(
                        font,
                        biomeHolder,
                        (width / 2) - (font.width(biomeHolder) / 2),
                        height / 2 - 60,
                        0xFFffffff
                );

                graphics.text(
                        font,
                        Component.literal(EarendelClient.INTERCEPTOR_DISTANCE + "pc").withStyle(ChatFormatting.LIGHT_PURPLE),
                        (width / 2) - (font.width(EarendelClient.INTERCEPTOR_DISTANCE + "pc") / 2),
                        height / 2 + 40,
                        0xFFffffff
                );
            }
        }

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                Earendel.id("screen/interceptor_circle_bg"),
                width / 2 - 32,
                height / 2 - 32,
                64,
                64,
                ARGB.color(0.5F, 0xFFffffff)
        );

        int color = 0xFFffffff;

        if (stack.has(EDataComponentTypes.SIGNAL)) {
            Signal signal = stack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                color = signal.color();
            }
        }

        matrix.pushMatrix();

        matrix.translate(
                width / 2F - 8,
                height / 2F - 8
        );

        matrix.scaleAround(
                1.6F + (EasingType.IN_OUT_SINE.apply(sineWave) * 0.7F),
                8,
                8
        );

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                Earendel.id("screen/drive_outline"),
                0,
                0,
                16,
                16,
                color
        );

        matrix.popMatrix();

        graphics.item(
                stack,
                width / 2 - 8,
                height / 2 - 8
        );

        if (waveActive && waveOpacity > 0.0F) {
            matrix.pushMatrix();

            matrix.translate(
                    width / 2F - 32,
                    height / 2F - 32
            );

            matrix.scaleAround(
                    (float) waveScale / 4,
                    32,
                    32
            );

            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    Earendel.id("screen/boom"),
                    0,
                    0,
                    64,
                    64,
                    ARGB.color(waveOpacity, 0xFFffffff)
            );

            matrix.popMatrix();
        }
    }

    public boolean isPauseScreen() {
        return false;
    }
}
