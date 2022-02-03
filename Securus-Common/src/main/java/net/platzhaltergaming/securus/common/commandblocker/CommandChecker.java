package net.platzhaltergaming.securus.common.commandblocker;

import java.util.Collection;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.platzhaltergaming.commonlib.messages.Messages;
import net.platzhaltergaming.securus.common.settings.CommandBlockerGroup;

@RequiredArgsConstructor
public class CommandChecker {

    public final static String DEFAULT_GROUP_KEY = "default";

    // Key is the permission node and the list of commands to be shown
    private final Map<String, CommandBlockerGroup> groups;
    private final Messages messages;

    public boolean checkCommand(IPlayerWrapper player, String command) {
        return checkCommand(player, command, false);
    }

    public boolean checkCommand(IPlayerWrapper player, String command, boolean silent) {
        CommandBlockerGroup group = getGroup(player);
        boolean result = checkCommand(player, group, command);

        if (!silent && result) {
            if (group.getAction() == ACTION.DENY) {
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(messages.get(player.getLocale(), "securus.deny-message")));
            }
        }

        return result;
    }

    public boolean checkCommand(IPlayerWrapper player, CommandBlockerGroup group, String command) {
        return mode(group.getMode(), group.getCommands().contains(command));
    }

    public CommandBlockerGroup getGroup(IPlayerWrapper player) {
        for (String group : groups.keySet()) {
            if (player.hasPermission("securus.commandblocker.group." + group)) {
                return groups.containsKey(group) ? groups.get(group) : groups.get(DEFAULT_GROUP_KEY);
            }
        }

        return groups.get(DEFAULT_GROUP_KEY);
    }

    public void cleanTabComplete(IPlayerWrapper player, Collection<String> commands) {
        CommandBlockerGroup group = getGroup(player);
        if (group == null) {
            return;
        }

        commands.removeIf((command) -> {
            return checkCommand(player, group, command);
        });
    }

    protected boolean mode(MODE mode, boolean in) {
        if (mode == MODE.REMOVE) {
            return in;
        } else if (mode == MODE.ONLY) {
            return !in;
        }

        return false;
    }

    public enum MODE {
        // Do nothing (allow anything)
        NOTHING,
        // Only commands in the group's list are returned (not added)
        ONLY,
        // Remove commands in the group
        REMOVE,
    }

    public enum ACTION {
        DENY,
        QUIET,
    }

}
