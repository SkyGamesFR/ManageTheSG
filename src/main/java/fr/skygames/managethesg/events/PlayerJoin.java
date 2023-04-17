package fr.skygames.managethesg.events;

import fr.skygames.managethesg.utils.HttpUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        boolean playerExist = HttpUtils.checkIfPlayerExist(player);

        if (!playerExist) {
            HttpUtils.createPlayer(player);
        } else {
            player.sendMessage(new TextComponent("§l[§bSkyGames ACCOUNT§r§l] §aWelcome back " + player.getName() + " !"));

            HttpUtils.updatePlayer(player);
        }
    }
}
