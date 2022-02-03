package net.platzhaltergaming.securus.bungee.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import lombok.Data;
import net.platzhaltergaming.securus.common.settings.CommandBlockerProps;
import net.platzhaltergaming.securus.common.settings.F3NameProps;

@ConfigSerializable
@Data
public class SettingsHolder {

    @Required
    @Setting
    private CommandBlockerProps commandBlocker;

    @Required
    @Setting
    private F3NameProps f3Name;

    @Setting
    private List<Locale> locales = Arrays.asList(Locale.ENGLISH, Locale.GERMAN);

}
