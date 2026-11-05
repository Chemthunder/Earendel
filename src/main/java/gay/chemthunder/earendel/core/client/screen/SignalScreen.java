package gay.chemthunder.earendel.core.client.screen;

import gay.chemthunder.earendel.core.index.ERegistries;
import gay.chemthunder.earendel.core.signal.Signal;
import gay.chemthunder.earendel.core.signal.SignalFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class SignalScreen extends Screen {
    private final Minecraft minecraft;

    private final Signal signal;

    private float textOpacity = 0.0F;

    public SignalScreen(Signal signal) {
        super(Component.empty());
        this.minecraft = Minecraft.getInstance();
        this.signal = signal;
    }

    public void tick() {
        super.tick();

        textOpacity = Math.clamp(textOpacity, 0.0F, 1F);

        if (minecraft.hasAltDown()) {
            if (textOpacity < 0.9F) {
                textOpacity += 0.1F;
                return;
            }
        }

        if (textOpacity > 0.0F) {
            textOpacity -= 0.05F;
        }
    }

    protected void init() {
        super.init();
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial) {
        Font font = minecraft.font;

        int startingX = (width / 2 - 40) - signal.bits().size();
        int lastX = startingX;

        List<Integer> heights = new ArrayList<>();

        for (int bit : signal) {
            Random random = new Random();
            final int bound = 5;

            int index = signal.bits().indexOf(bit);
            int drawnX = (lastX + (index * 15)) - signal.bits().size();
            int drawnY = height / 2 + (bit * 2);

            graphics.horizontalLine(drawnX, drawnX, drawnY, 0xFFffffff);
            graphics.verticalLine(drawnX, drawnY, height / 2, 0xFFffffff);

            if (textOpacity > 0.0F) {
                graphics.text(font, bit + "", (drawnX + 6) + random.nextInt(-bound, bound), (drawnY - 6) + random.nextInt(-bound, bound), ARGB.color(textOpacity / 2, 0xFFffffff));
                graphics.text(font, bit + "", (drawnX + 6), (drawnY - 6), ARGB.color(textOpacity, 0xFFffffff));
            }

            heights.add(drawnY);
            lastX = drawnX;
        }

        int sortedHeight = 0;

        for (int height : heights) {
            if (height > sortedHeight) {
                sortedHeight = height;
            }
        }

        graphics.horizontalLine(startingX - 5, lastX + 5, height / 2, 0xFFffffff);
        graphics.horizontalLine(startingX - 25, lastX + 25, sortedHeight + 10, 0xFFffffff);

        for (SignalFlag flag : signal.getAcceptingFlags()) {
            int index = signal.getAcceptingFlags().indexOf(flag);
            Identifier path = ERegistries.FLAG.getKey(flag);

            if (path != null) {
                String id = path.getPath();

                graphics.text(
                        font,
                        id,
                        graphics.guiWidth() - 60,
                        20 + (index * 15),
                        0xFFffffff
                );
            }
        }

        // run base last
        super.extractRenderState(graphics, mouseX, mouseY, partial);
    }
}