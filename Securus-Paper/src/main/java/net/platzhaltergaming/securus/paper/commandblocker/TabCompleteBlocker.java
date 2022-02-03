package net.platzhaltergaming.securus.paper.commandblocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import lombok.RequiredArgsConstructor;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

@RequiredArgsConstructor
public class TabCompleteBlocker implements Listener {

    private final CommandChecker commandChecker;

    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) {
        commandChecker.cleanTabComplete(new PlayerWrapper(event.getPlayer()), event.getCommands());
    }

}
