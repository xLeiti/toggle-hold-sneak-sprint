package drawthatredstone.altsneaksprint;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;


public class AlternateSneakSprint implements ModInitializer {

    public static KeyBinding keySneakToggle = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.alt-sneaksprint.sneak", GLFW.GLFW_KEY_UNKNOWN, "key.categories.movement", () -> true));
    public static KeyBinding keySprintToggle = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.alt-sneaksprint.sprint", GLFW.GLFW_KEY_UNKNOWN, "key.categories.movement", () -> true));
    public boolean toggleSprint = false;

    @Override
    public void onInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register(this::onTickStart);
    }

    public void onTickStart(MinecraftClient client) {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        boolean normalSprint = InputUtil.isKeyPressed(handle, KeyBindingHelper.getBoundKeyOf(client.options.sprintKey).getCode());

        if (client.player != null) {
            if (keySprintToggle.wasPressed()) {
                toggleSprint = !toggleSprint;
            }

            if (toggleSprint && !client.options.sprintKey.isPressed()) {
                client.options.sprintKey.setPressed(true);
            } else if (!toggleSprint) {
                client.options.sprintKey.setPressed(normalSprint);
            }
        }
    }


}
