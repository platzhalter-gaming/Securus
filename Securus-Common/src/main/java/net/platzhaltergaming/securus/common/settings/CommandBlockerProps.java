package net.platzhaltergaming.securus.common.settings;

import java.util.Map;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import lombok.Data;

@ConfigSerializable
@Data
public class CommandBlockerProps implements EnabledInterface {

    @Required
    @Setting
    private boolean enabled = true;

    @Required
    @Setting
    private Map<String, CommandBlockerGroup> groups;

}
