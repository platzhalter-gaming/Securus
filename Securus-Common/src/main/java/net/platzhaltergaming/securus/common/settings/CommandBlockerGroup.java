package net.platzhaltergaming.securus.common.settings;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import lombok.Data;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker.ACTION;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker.MODE;

@ConfigSerializable
@Data
public class CommandBlockerGroup {

    @Required
    @Setting
    private MODE mode;

    @Required
    @Setting
    private ACTION action = ACTION.QUIET;

    @Setting
    private List<String> commands = new ArrayList<>();

    @Setting
    private List<String> tabComplete = new ArrayList<>();

}
