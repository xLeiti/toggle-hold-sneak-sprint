package drawthatredstone.altsneaksprint.mixin;

import drawthatredstone.altsneaksprint.AlternateSneakSprint;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin {

    /**
     * Overrides the PlayerInput constructor -- true if the user is pressing the sneak key OR has toggle sneaked, false if not
     *
     * @param original the status of the static sneak (the non-toggle one)
     * @return whether the user should be sneaking
     */
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/PlayerInput;<init>(ZZZZZZZ)V"), index = 5)
    private boolean modifySneakToggle(boolean original) {
        return AlternateSneakSprint.keySneakToggle.isPressed() || original;
    }
}

