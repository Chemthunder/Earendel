package gay.chemthunder.earendel.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class TweakerScreen extends Screen {
    private final Minecraft minecraft;
    private final ItemStack stack;

    private final List<Function<GuiGraphicsExtractor, Renderable>> drawables = new ArrayList<>();

    public TweakerScreen(ItemStack stack) {
        super(Component.empty());
        this.minecraft = Minecraft.getInstance();
        this.stack = stack;
    }
}
