package net.platzhaltergaming.securus.paper.commandblocker;

import java.util.Locale;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.platzhaltergaming.securus.common.commandblocker.IPlayerWrapper;

@Getter
@RequiredArgsConstructor
public class PlayerWrapper implements IPlayerWrapper {

    private final Player player;

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public void sendMessage(Component message) {
        player.sendMessage(message);
    }

    public Locale getLocale() {
        return player.locale() == null ? Locale.ENGLISH : player.locale();
    }

}
