package net.platzhaltergaming.securus.paper.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import lombok.Data;
import net.platzhaltergaming.securus.common.settings.CommandBlockerProps;

@ConfigSerializable
@Data
public class SettingsHolder {

    @Required
    @Setting
    private CommandBlockerProps commandBlocker;

    @Setting
    private List<Locale> locales = Arrays.asList(Locale.ENGLISH, Locale.GERMAN);

}
