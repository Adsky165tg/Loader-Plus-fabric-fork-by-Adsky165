package dev.loaderplus.mixin;

import dev.loaderplus.core.InternalModRegistry;
import dev.loaderplus.core.LoaderPlusMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.Map;

public class LoaderPlusInfoScreen extends Screen {
    private final Screen parent;

    public LoaderPlusInfoScreen(Screen parent) {
        super(Text.literal("Loader+ " + LoaderPlusMain.VERSION));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(
            ButtonWidget.builder(Text.literal("Back"),
                button -> this.client.setScreen(parent))
            .dimensions(this.width / 2 - 75, this.height - 40, 150, 20).build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§6§lLoader+ §r§7v" + LoaderPlusMain.VERSION),
            this.width / 2, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§8Integrated Core Features"),
            this.width / 2, 40, 0xAAAAAA);
        int y = 60;
        for (Map.Entry<String, String> entry : InternalModRegistry.FEATURE_NAMES.entrySet()) {
            context.drawTextWithShadow(this.textRenderer,
                Text.literal("§a✔ §f" + entry.getValue() + " §7— " +
                    InternalModRegistry.getFeatureStatus(entry.getKey())),
                this.width / 2 - 100, y, 0xFFFFFF);
            y += 14;
        }
    }
                                           }
