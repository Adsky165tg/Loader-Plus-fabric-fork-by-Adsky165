package dev.loaderplus.mixin;

import dev.loaderplus.core.LoaderPlusMain;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void loaderplus_addButton(CallbackInfo ci) {
        TitleScreen screen = (TitleScreen)(Object)this;
        try {
            screen.addDrawableChild(
                ButtonWidget.builder(
                    Text.literal("§6Loader+ §7v" + LoaderPlusMain.VERSION),
                    button -> net.minecraft.client.MinecraftClient.getInstance()
                        .setScreen(new LoaderPlusInfoScreen(screen))
                ).dimensions(screen.width / 2 - 100, screen.height / 4 + 120, 200, 20).build()
            );
        } catch (Exception e) {
            LoaderPlusMain.LOGGER.error("[Loader+] Button failed", e);
        }
    }
}
