package fr.skygames.managethesg.utils;


import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import okhttp3.*;

public class HttpUtils {
    
    private static FileManager fileManager;
    private static final OkHttpClient client = new OkHttpClient();

    public static void init(FileManager fileManager) {
        HttpUtils.fileManager = fileManager;
    }
    
    public static void createPlayer(ProxiedPlayer player) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("name", player.getName());
            json.addProperty("first_login", System.currentTimeMillis());
            json.addProperty("last_login", System.currentTimeMillis());
            json.addProperty("team", "none");

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(fileManager.getConfig("config").getString("url") + "players")
                    .addHeader("Authorization", "Bearer " + fileManager.getConfig("config").getString("api-key"))
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                player.sendMessage(new TextComponent("§l[§bSkyGames ACCOUNT§r§l] " +
                        "§aVotre compte a bien été créé !"));
            } else {
                player.disconnect(new TextComponent("§l[§bSkyGames ACCOUNT§r§l]\n" +
                        "§cUne erreur est survenue lors de la sauvegarde de votre compte.\n" +
                        "§l§cMerci de contacter un membre du staff pour plus d'informations."));
            }

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(ProxiedPlayer player) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("last_login", System.currentTimeMillis());
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("name", player.getName());

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(fileManager.getConfig("config").getString("url") + "players/" + player.getUniqueId().toString())
                    .addHeader("Authorization", "Bearer " + fileManager.getConfig("config").getString("api-key"))
                    .patch(body)
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                System.out.println(response.code());
                player.sendMessage(new TextComponent("§l[§bSkyGames ACCOUNT§r§l] " +
                        "§cUne erreur est survenue lors de la sauvegarde de votre compte.\n" +
                        "§l§cMerci de contacter un membre du staff pour plus d'informations."));
            }

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfPlayerExist(ProxiedPlayer player) {
        try {
            Request request = new Request.Builder()
                    .url(fileManager.getConfig("config").getString("url") + "players/" + player.getUniqueId().toString())
                    .addHeader("Authorization", "Bearer " + fileManager.getConfig("config").getString("api-key"))
                    .get()
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build();

            Response response = client.newCall(request).execute();
            response.close();
            return response.isSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
