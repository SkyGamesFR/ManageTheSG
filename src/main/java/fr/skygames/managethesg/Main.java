package fr.skygames.managethesg;

import fr.skygames.managethesg.managers.Manager;
import net.md_5.bungee.api.plugin.Plugin;

public final class Main extends Plugin {

    private Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Manager manager = new Manager(this);
        manager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Main getInstance() {
        return instance;
    }
}
