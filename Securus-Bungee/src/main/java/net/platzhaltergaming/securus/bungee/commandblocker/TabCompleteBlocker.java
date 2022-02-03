package net.platzhaltergaming.securus.bungee.commandblocker;

//import org.bukkit.event.player.PlayerCommandSendEvent;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

@RequiredArgsConstructor
public class TabCompleteBlocker implements Listener {

    private final CommandChecker commandChecker;

    @EventHandler
    public void onTabCompleteEvent(TabCompleteEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            commandChecker.cleanTabComplete(new PlayerWrapper((ProxiedPlayer) event.getSender()),
                    event.getSuggestions());
        }
    }

}
