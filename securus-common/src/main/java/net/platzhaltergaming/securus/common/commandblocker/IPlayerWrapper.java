package net.platzhaltergaming.securus.common.commandblocker;

import java.util.Locale;

import net.kyori.adventure.text.Component;

public interface IPlayerWrapper {

    public boolean hasPermission(String permission);

    public void sendMessage(Component message);

    public Locale getLocale();

}
