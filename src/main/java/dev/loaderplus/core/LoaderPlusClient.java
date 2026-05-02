package dev.loaderplus.core;

import net.fabricmc.api.ClientModInitializer;

public class LoaderPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LoaderPlusMain.LOGGER.info("[Loader+] Client initializing...");
        ModrinthBrowser.init();
        LoaderPlusMain.LOGGER.info("[Loader+] Client ready.");
    }
}
