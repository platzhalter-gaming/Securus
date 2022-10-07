package net.platzhaltergaming.securus.bungee.f3name;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.platzhaltergaming.securus.common.settings.F3NameProps;

@Getter
@RequiredArgsConstructor
public class F3Name implements Listener {

    public static final String BRAND_CHANNEL = "minecraft:brand";

    private final F3NameProps settings;

    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent event) {
        if (!event.getTag().equals(F3Name.BRAND_CHANNEL)) {
            return;
        }
        if (!(event.getReceiver() instanceof ProxiedPlayer)) {
            return;
        }
        event.setCancelled(true);

        ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
        this.sendData(player, generateBrand(player));
    }

    protected String generateBrand(ProxiedPlayer player) {
        ServerInfo serverInfo = player.getServer().getInfo();
        String serverName = "N/A";
        if (serverInfo != null) {
            serverName = serverInfo.getName();
        }

        return settings.getBrand().replace("%server_name%", serverName) + ChatColor.RESET;
    }

    protected void sendData(ProxiedPlayer player, String brand) {
        player.sendData(BRAND_CHANNEL, new PacketSerializer(brand).toArray());
    }

}
