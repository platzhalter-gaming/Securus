package net.platzhaltergaming.securus.bungee.modules;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Listener;
import net.platzhaltergaming.securus.bungee.Main;
import net.platzhaltergaming.securus.bungee.f3name.F3Name;
import net.platzhaltergaming.securus.common.settings.F3NameProps;

@Getter
@RequiredArgsConstructor
public class F3NameModule implements Listener {

    private final Logger logger;
    private final Main plugin;
    private final F3NameProps settings;

    private F3Name f3Name;

    public void onEnable() {
        this.f3Name = new F3Name(settings);

        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this.f3Name);
    }

    public void onDisable() {
        getPlugin().getProxy().getPluginManager().unregisterListener(this.f3Name);
        this.f3Name = null;
    }

}
