package net.platzhaltergaming.securus.bungee.commandblocker;

import java.util.Locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.platzhaltergaming.securus.common.commandblocker.IPlayerWrapper;

@Getter
@RequiredArgsConstructor
public class PlayerWrapper implements IPlayerWrapper {

    private final ProxiedPlayer player;

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public void sendMessage(Component message) {
        player.sendMessage(BungeeComponentSerializer.get().serialize(message));
    }

    public Locale getLocale() {
        return player.getLocale() == null ? Locale.ENGLISH : player.getLocale();
    }

}
