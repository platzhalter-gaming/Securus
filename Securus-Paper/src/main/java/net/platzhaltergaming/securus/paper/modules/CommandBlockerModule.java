package net.platzhaltergaming.securus.paper.modules;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.platzhaltergaming.commonlib.messages.Messages;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;
import net.platzhaltergaming.securus.paper.Main;
import net.platzhaltergaming.securus.paper.commandblocker.CommandBlocker;
import net.platzhaltergaming.securus.paper.commandblocker.TabCompleteBlocker;

@Getter
@RequiredArgsConstructor
public class CommandBlockerModule {

    private final Logger logger;
    private final Main plugin;
    private final Messages messages;

    private CommandChecker commandChecker;

    private CommandBlocker commandBlocker;
    private TabCompleteBlocker tabCompleteBlocker;

    public void onEnable() {
        this.commandChecker = new CommandChecker(getPlugin().getSettings().getCommandBlocker().getGroups(), getMessages());

        this.commandBlocker = new CommandBlocker(getLogger(), this.commandChecker);
        getPlugin().getServer().getPluginManager().registerEvents(this.commandBlocker, (Plugin) getPlugin());

        this.tabCompleteBlocker = new TabCompleteBlocker(getCommandChecker());
        getPlugin().getServer().getPluginManager().registerEvents(this.tabCompleteBlocker, (Plugin) getPlugin());
    }

    public void onDisable() {
    }

    public boolean onReload() {
        return true;
    }

}
