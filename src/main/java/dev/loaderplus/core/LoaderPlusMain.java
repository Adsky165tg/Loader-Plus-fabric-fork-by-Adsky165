package dev.loaderplus.core;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderPlusMain implements ModInitializer {
    public static final String MOD_ID = "loaderplus";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger("Loader+");

    @Override
    public void onInitialize() {
        LOGGER.info("[Loader+] Initializing...");
        InternalModRegistry.init();
        ForgeCompatLayer.init();
        LOGGER.info("[Loader+] Ready.");
    }
}
