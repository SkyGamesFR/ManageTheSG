package fr.skygames.managethesg.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PluginMessage implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if(event.getTag().equalsIgnoreCase("BungeeCord")) {
            try {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
                String c= in.readUTF();
                System.out.println(c);
                if(!c.equals("BungeeCommand")) {
                    System.out.println("Not a BungeeCommand");
                    return;
                }
                String command = in.readUTF();
                ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
                System.out.println("Command executed: " + command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
