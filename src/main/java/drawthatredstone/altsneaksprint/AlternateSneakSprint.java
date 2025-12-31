package drawthatredstone.altsneaksprint;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;


public class AlternateSneakSprint implements ModInitializer  {

    public static KeyBinding keySneakToggle = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.alt-sneaksprint.sneak", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.Category.MOVEMENT, () -> true, false));
    public static KeyBinding keySprintToggle = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.alt-sneaksprint.sprint", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.Category.MOVEMENT, () -> true,false));
    public boolean toggleSprint = false;

    @Override
    public void onInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register(this::onTickStart);
    }

    public void onTickStart(MinecraftClient client) {
        Window window = MinecraftClient.getInstance().getWindow();
        boolean normalSprint = InputUtil.isKeyPressed(window, KeyBindingHelper.getBoundKeyOf(client.options.sprintKey).getCode());

        if (client.player != null) {

            String offString = "§l§f[§coff§f]";
            String onString = "§l§f[§aon§f]";

            if (keySneakToggle.wasPressed()) {
                String toggleSneakStatus = keySneakToggle.isPressed() ? onString : offString;
                client.player.sendMessage(Text.of("§eToggle-Sneak " + toggleSneakStatus) , true);
            }

            if (keySprintToggle.wasPressed()) {
                String toggleSprintStatus = keySprintToggle.isPressed() ? onString : offString;
                client.player.sendMessage(Text.of("§eToggle-Sprint " + toggleSprintStatus) , true);
                toggleSprint = !toggleSprint;
            }

            if(client.options.sneakKey.wasPressed() && keySneakToggle.isPressed()){
                client.player.sendMessage(Text.of("§eToggle-Sneak " + onString) , true);
            }

            if(client.options.sprintKey.wasPressed() && keySprintToggle.isPressed()){
                client.player.sendMessage(Text.of("§eToggle-Sprint " + onString) , true);
            }

            if (toggleSprint && !client.options.sprintKey.isPressed()) {
                client.options.sprintKey.setPressed(true);
            } else if (!toggleSprint) {
                client.options.sprintKey.setPressed(normalSprint);
            }
        }
    }
}
