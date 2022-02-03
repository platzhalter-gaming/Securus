package net.platzhaltergaming.securus.bungee.modules;

import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.platzhaltergaming.commonlib.messages.Messages;
import net.platzhaltergaming.securus.bungee.Main;
import net.platzhaltergaming.securus.bungee.commandblocker.CommandBlocker;
import net.platzhaltergaming.securus.bungee.commandblocker.TabCompleteBlocker;
import net.platzhaltergaming.securus.bungee.commandblocker.packets.DeclareCommandsListener;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

@Getter
@RequiredArgsConstructor
public class CommandBlockerModule {

    private final Logger logger;
    private final Main plugin;
    private final Messages messages;

    private CommandChecker commandChecker;

    private CommandBlocker commandBlocker;
    private TabCompleteBlocker tabCompleteBlocker;
    private DeclareCommandsListener declareCommandsListener;

    public void onEnable() {
        this.commandChecker = new CommandChecker(getPlugin().getSettings().getCommandBlocker().getGroups(), getMessages());

        this.commandBlocker = new CommandBlocker(getLogger(), this.commandChecker);
        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this.commandBlocker);

        this.tabCompleteBlocker = new TabCompleteBlocker(this.commandChecker);
        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this.tabCompleteBlocker);

        this.declareCommandsListener = new DeclareCommandsListener(this.commandChecker);
        this.declareCommandsListener.register();
    }

    public void onDisable() {
        if (this.declareCommandsListener != null) {
            this.declareCommandsListener.unregister();
            this.declareCommandsListener = null;
        }

        if (this.tabCompleteBlocker != null) {
            getPlugin().getProxy().getPluginManager().unregisterListener(this.tabCompleteBlocker);
            this.tabCompleteBlocker = null;
        }
        if (this.commandBlocker != null) {
            getPlugin().getProxy().getPluginManager().unregisterListener(this.commandBlocker);
            this.commandBlocker = null;
        }

        this.commandChecker = null;
    }

    public boolean onReload() {
        return true;
    }

}
