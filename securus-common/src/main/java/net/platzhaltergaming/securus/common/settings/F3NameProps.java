package net.platzhaltergaming.securus.common.settings;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import lombok.Data;

@ConfigSerializable
@Data
public class F3NameProps implements EnabledInterface {

    @Required
    @Setting
    private boolean enabled = true;

    @Setting
    private String brand = "§b..§cconnecting§b..";

}
