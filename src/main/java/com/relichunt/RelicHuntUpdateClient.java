package com.relichunt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/** Checks public GitHub Releases only; never downloads or replaces executable files without consent. */
public class RelicHuntUpdateClient implements ClientModInitializer {
    private static final String RELEASE_API = "https://api.github.com/repos/popovartem20150910-ui/relic-hunt/releases/latest";
    private static final String RELEASES_URL = "https://github.com/popovartem20150910-ui/relic-hunt/releases/latest";
    private static volatile String availableVersion;

    @Override
    public void onInitializeClient() {
        CompletableFuture.runAsync(() -> {
            try {
                HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
                HttpRequest request = HttpRequest.newBuilder(URI.create(RELEASE_API))
                        .timeout(Duration.ofSeconds(8))
                        .header("Accept", "application/vnd.github+json")
                        .header("User-Agent", "Relic-Hunt-Update-Checker")
                        .build();
                HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) return;
                JsonObject release = JsonParser.parseString(response.body()).getAsJsonObject();
                if (release.has("draft") && release.get("draft").getAsBoolean()) return;
                if (release.has("prerelease") && release.get("prerelease").getAsBoolean()) return;
                String latest = release.get("tag_name").getAsString().replaceFirst("^v", "");
                String current = FabricLoader.getInstance().getModContainer("relic_hunt")
                        .orElseThrow().getMetadata().getVersion().getFriendlyString();
                if (newer(latest, current)) availableVersion = latest;
            } catch (Exception ignored) {
                // No network, rate limit, or no published release: game starts normally.
            }
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            String version = availableVersion;
            if (version == null) return;
            client.execute(() -> {
                if (client.player == null) return;
                Text link = Text.literal(" [Открыть обновление]")
                        .setStyle(Style.EMPTY.withColor(Formatting.AQUA)
                                .withUnderline(true)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, RELEASES_URL)));
                client.player.sendMessage(Text.literal("Relic Hunt: доступна версия " + version + "!")
                        .formatted(Formatting.GOLD).copy().append(link), false);
            });
        });
    }

    private static boolean newer(String latest, String current) {
        String[] a = latest.split("\\."), b = current.split("\\.");
        try {
            for (int i = 0; i < Math.max(a.length, b.length); i++) {
                int x = i < a.length ? Integer.parseInt(a[i]) : 0;
                int y = i < b.length ? Integer.parseInt(b[i]) : 0;
                if (x != y) return x > y;
            }
        } catch (NumberFormatException ignored) {
            return false;
        }
        return false;
    }
}
