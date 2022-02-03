package net.platzhaltergaming.securus.bungee.commandblocker;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

@Getter
@RequiredArgsConstructor
public class CommandBlocker implements Listener {

    private final Logger logger;
    private final CommandChecker commandChecker;

    @EventHandler
    public void onChatEvent(ChatEvent event) {
        if (!event.isProxyCommand()) {
            return;
        }

        String[] commandSplit = event.getMessage().replaceFirst("/", "").split(" ");
        if (commandSplit.length == 0) {
            event.setCancelled(true);
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        // Get the command
        String command = commandSplit[0].toLowerCase();
        // Check the command
        if (commandChecker.checkCommand(new PlayerWrapper(player), command)) {
            event.setCancelled(true);
            this.logCommand(player, command);
            return;
        }
    }

    protected void logCommand(ProxiedPlayer player, String command) {
        getLogger().info(String.format("%s (%s) tried to run a blocked command: %s", player.getName(),
                player.getUniqueId(), command));
    }

}
