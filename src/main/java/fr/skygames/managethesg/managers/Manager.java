package fr.skygames.managethesg.managers;

import fr.skygames.managethesg.Main;
import fr.skygames.managethesg.events.PlayerJoin;
import fr.skygames.managethesg.events.PluginMessage;
import fr.skygames.managethesg.events.ProxyPing;
import fr.skygames.managethesg.utils.FileManager;
import fr.skygames.managethesg.utils.HttpUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Manager {

    public Manager instance;
    private final Plugin plugin;

    public Manager(Main plugin) {
        instance = this;
        this.plugin = plugin;
    }

    public void init() {
        FileManager fileManager = new FileManager(plugin);
        fileManager.createFile("config");

        HttpUtils.init(fileManager);

        plugin.getProxy().registerChannel("BungeeCord");

        PluginManager pm = plugin.getProxy().getPluginManager();
        pm.registerListener(plugin, new ProxyPing(plugin));
        pm.registerListener(plugin, new PluginMessage());
        pm.registerListener(plugin, new PlayerJoin());
    }

    public Manager getInstance() {
        return instance;
    }
}
