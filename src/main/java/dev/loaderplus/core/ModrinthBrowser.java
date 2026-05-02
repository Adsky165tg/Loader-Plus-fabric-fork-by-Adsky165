package dev.loaderplus.core;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class ModrinthBrowser {
    private static final String API = "https://api.modrinth.com/v2";
    private static final String UA = "LoaderPlus/1.0.0";
    private static Path MODS_DIR;

    public static void init() {
        MODS_DIR = FabricLoader.getInstance().getGameDir().resolve("mods");
        LoaderPlusMain.LOGGER.info("[Loader+] Modrinth browser ready.");
    }

    public static List<ModResult> search(String query) {
        List<ModResult> results = new ArrayList<>();
        try {
            String enc = URLEncoder.encode(query, "UTF-8");
            String facets = URLEncoder.encode("[[\"categories:fabric\"],[\"versions:1.20.1\"]]", "UTF-8");
            URL url = new URL(API + "/search?query=" + enc + "&facets=" + facets + "&limit=20");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", UA);
            conn.setConnectTimeout(10000);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
            }
            JsonArray hits = JsonParser.parseString(sb.toString()).getAsJsonObject().getAsJsonArray("hits");
            for (JsonElement el : hits) {
                JsonObject o = el.getAsJsonObject();
                results.add(new ModResult(o.get("project_id").getAsString(),
                    o.get("title").getAsString(),
                    o.get("description").getAsString(),
                    o.get("downloads").getAsLong()));
            }
        } catch (Exception e) {
            LoaderPlusMain.LOGGER.error("[Loader+] Search failed", e);
        }
        return results;
    }

    public static boolean deleteMod(String filename) {
        String id = filename.toLowerCase().replace(".jar","").replaceAll("-[0-9].*","");
        if (InternalModRegistry.isCoreMod(id)) return false;
        try { Files.deleteIfExists(MODS_DIR.resolve(filename)); return true; }
        catch (Exception e) { return false; }
    }

    public record ModResult(String id, String title, String description, long downloads) {}
}
