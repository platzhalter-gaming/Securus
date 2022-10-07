package net.platzhaltergaming.securus.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.platzhaltergaming.commonlib.messages.Messages;
import net.platzhaltergaming.securus.bungee.modules.CommandBlockerModule;
import net.platzhaltergaming.securus.bungee.modules.F3NameModule;
import net.platzhaltergaming.securus.bungee.settings.SettingsHolder;
import net.platzhaltergaming.securus.common.configurate.LocaleSerializer;

@Getter
public class Main extends Plugin implements Listener {

    // Config
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private SettingsHolder settings;
    private Messages messages;

    // Modules
    private CommandBlockerModule commandBlockerModule;
    private F3NameModule f3NameModule;

    @Override
    public void onEnable() {
        // Config
        this.loadConfig();
        this.messages = new Messages(getDataFolder().toPath(), "messages", getSettings().getLocales(),
                getSettings().getLocales().get(0));

        if (settings.getCommandBlocker().isEnabled()) {
            commandBlockerModule = new CommandBlockerModule(getLogger(), this, getMessages());
            commandBlockerModule.onEnable();
        }
        if (settings.getF3Name().isEnabled()) {
            f3NameModule = new F3NameModule(getLogger(), this, settings.getF3Name());
            f3NameModule.onEnable();
        }
    }

    @Override
    public void onDisable() {
        if (commandBlockerModule != null) {
            commandBlockerModule.onDisable();
        }
        if (f3NameModule != null) {
            f3NameModule.onDisable();
        }
    }

    public void loadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loader = YamlConfigurationLoader.builder()
                .defaultOptions(
                        opts -> opts.serializers(build -> build.register(Locale.class, LocaleSerializer.INSTANCE)))
                .file(file).build();

        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (IOException e) {
            getLogger().severe("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }

        try {
            settings = root.get(SettingsHolder.class);
        } catch (SerializationException | ClassCastException e) {
            getLogger().severe("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }
    }

}
