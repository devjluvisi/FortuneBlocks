package devjluvisi.mlb.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import devjluvisi.mlb.MoreLuckyBlocks;

public class JoinLuckEvent implements Listener {
	private final MoreLuckyBlocks plugin;

	public JoinLuckEvent(MoreLuckyBlocks plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		// Replace the player name if the player has changed their name sicne last
		// login.s
		if (this.plugin.getPlayersYaml().getConfig().getConfigurationSection("players") != null) {
			for (final String playerUUIDs : this.plugin.getPlayersYaml().getConfig().getConfigurationSection("players")
					.getKeys(false)) {
				if (playerUUIDs.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())
						&& !((String) this.plugin.getPlayersYaml().getConfig().get("players." + playerUUIDs + ".name"))
								.equalsIgnoreCase(e.getPlayer().getUniqueId().toString())) {
					this.plugin.getPlayersYaml().getConfig().set("players." + playerUUIDs + ".name",
							e.getPlayer().getName().toString());
				}
			}
		}
		if (this.plugin.getPlayerManager().getPlayer(e.getPlayer().getName()) != null) {
			this.plugin.getPlayerManager().add(e.getPlayer(),
					this.plugin.getPlayerManager().getPlayer(e.getPlayer().getName()).getLuck());
		} else {
			this.plugin.getPlayerManager().add(e.getPlayer());
		}
		this.plugin.getPlayerManager().save();
	}
}
