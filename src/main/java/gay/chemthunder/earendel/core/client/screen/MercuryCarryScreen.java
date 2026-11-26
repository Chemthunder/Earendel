package gay.chemthunder.earendel.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * @author Chemthunder
 */
// Open to a list of all signal origins in the dimension
// user picks from one, closing the menu, and after 4s the item they're currently holding is teleported to that origin
@Environment(EnvType.CLIENT)
public class MercuryCarryScreen extends Screen {
    public MercuryCarryScreen() {
        super(Component.empty());
    }
}
