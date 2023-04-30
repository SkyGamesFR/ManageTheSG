package fr.skygames.managethesg.events;

import fr.skygames.managethesg.managers.Manager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ProxyPing implements Listener {

    private Plugin plugin;

    public ProxyPing(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();

        ping.setDescriptionComponent(new TextComponent(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        " §e§k! §a§lSkyGames§e§k! §c §l1.12-1.18 §eNotre site: §bskygames.fr\n" +
                                "§f* §cLe serveur est en phase de développement... §f*"
                )));

        try {
            ping.setFavicon(Favicon.create(ImageIO.read(new File("favicon.png"))));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ping.setPlayers(new ServerPing.Players(1000, plugin.getProxy().getOnlineCount(), ping.getPlayers().getSample()));

        ping.setVersion(new ServerPing.Protocol("§c1.12.X, 1.16.X, 1.18.X, 1.19.X", ping.getVersion().getProtocol()));

        e.setResponse(ping);
    }
}
