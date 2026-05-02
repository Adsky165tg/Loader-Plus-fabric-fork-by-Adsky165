package dev.loaderplus.core;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.ActionResult;
import java.util.*;
import java.util.function.Consumer;

public class ForgeCompatLayer {
    private static final Map<String, List<Consumer<Object>>> EVENT_BUS = new HashMap<>();

    public static void init() {
        LoaderPlusMain.LOGGER.info("[Loader+] Forge compat initializing...");
        ServerLifecycleEvents.SERVER_STARTED.register(server -> fireEvent("ServerStartedEvent", server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> fireEvent("ServerStoppingEvent", server));
        ServerTickEvents.START_SERVER_TICK.register(server -> fireEvent("ServerTickEvent.PRE", server));
        UseItemCallback.EVENT.register((player, world, hand) -> {
            fireEvent("PlayerInteractEvent", player);
            return ActionResult.PASS;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            fireEvent("AttackEntityEvent", entity);
            return ActionResult.PASS;
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
            fireEvent("PlayerLoggedInEvent", handler.player));
        LoaderPlusMain.LOGGER.info("[Loader+] Forge compat ready.");
    }

    public static void on(String eventName, Consumer<Object> listener) {
        EVENT_BUS.computeIfAbsent(eventName, k -> new ArrayList<>()).add(listener);
    }

    public static void fireEvent(String eventName, Object data) {
        List<Consumer<Object>> listeners = EVENT_BUS.get(eventName);
        if (listeners != null) {
            for (Consumer<Object> listener : listeners) {
                try { listener.accept(data); }
                catch (Exception e) { LoaderPlusMain.LOGGER.error("[Loader+] Event error: " + eventName, e); }
            }
        }
    }
}
