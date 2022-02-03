package net.platzhaltergaming.securus.paper.commandblocker;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

@Getter
@RequiredArgsConstructor
public class CommandBlocker implements Listener {

    private final Logger logger;
    private final CommandChecker commandChecker;

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String[] commandSplit = event.getMessage().replaceFirst("/", "").split(" ");
        if (commandSplit.length == 0) {
            return;
        }

        // Get the command
        String command = commandSplit[0].toLowerCase();
        // Check the command
        if (commandChecker.checkCommand(new PlayerWrapper(event.getPlayer()), command)) {
            event.setCancelled(true);
            this.logCommand(event.getPlayer(), command);
            return;
        }
    }

    protected void logCommand(Player player, String command) {
        getLogger().info(String.format("%s (%s) tried to run a blocked command: %s", player.getName(),
                player.getUniqueId(), command));
    }

}
