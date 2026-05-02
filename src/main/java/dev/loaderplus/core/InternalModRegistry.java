package dev.loaderplus.core;

import java.util.*;

public class InternalModRegistry {
    public static final Map<String, String> FEATURE_NAMES = new LinkedHashMap<>();
    public static final Map<String, String> FEATURE_STATUS = new LinkedHashMap<>();
    public static final Set<String> CORE_MOD_IDS = new HashSet<>(Arrays.asList(
        "sodium","iris","lithium","krypton","connector","loaderplus","fabric-api"
    ));

    public static void init() {
        FEATURE_NAMES.put("sodium",    "Rendering Engine");
        FEATURE_NAMES.put("iris",      "Shader Support");
        FEATURE_NAMES.put("lithium",   "Server Optimization");
        FEATURE_NAMES.put("krypton",   "Network Optimization");
        FEATURE_NAMES.put("connector", "NeoForge Compatibility");
        FEATURE_STATUS.put("sodium",    "Enhanced");
        FEATURE_STATUS.put("iris",      "Built in");
        FEATURE_STATUS.put("lithium",   "Active");
        FEATURE_STATUS.put("krypton",   "Active");
        FEATURE_STATUS.put("connector", "Active");
        LoaderPlusMain.LOGGER.info("[Loader+] " + FEATURE_NAMES.size() + " features registered.");
    }

    public static boolean isCoreMod(String modId) {
        return CORE_MOD_IDS.contains(modId.toLowerCase());
    }

    public static String getFeatureName(String modId) {
        return FEATURE_NAMES.getOrDefault(modId.toLowerCase(), null);
    }

    public static String getFeatureStatus(String modId) {
        return FEATURE_STATUS.getOrDefault(modId.toLowerCase(), "Active");
    }
}
