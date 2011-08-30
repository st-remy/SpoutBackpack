package me.neatmonster.spoutbackpack;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SBPlayerListener extends PlayerListener {

	private SpoutBackpack plugin;

	public SBPlayerListener(SpoutBackpack plugin) {
		this.plugin = plugin;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		try {
			Player player = event.getPlayer();
			plugin.loadInventory(player, player.getWorld());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (((event.getFrom().getWorld().getName() != event.getTo().getWorld()
				.getName()) || plugin.portals.contains(event.getPlayer())
				&& plugin.config.getBoolean("Backpack."
						+ event.getTo().getWorld().getName()
						+ ".InventoriesShare?", true) == false)) {
			try {
				Player player = event.getPlayer();
				SBInventorySaveTask.saveInventory(player, event.getFrom()
						.getWorld());
				plugin.inventories.remove(player.getName());
				plugin.inventoriesSize.remove(player.getName());
				plugin.inventoriesSize.put(player.getName(),
						plugin.allowedSize(event.getTo().getWorld(), player));
				plugin.loadInventory(player, event.getTo().getWorld());
			} catch (Exception e) {
				e.printStackTrace();
			}
			plugin.portals.remove(event.getPlayer());
		}
	}

	public void onPlayerPortal(PlayerPortalEvent event) {
		plugin.portals.add(event.getPlayer());
	}

	public void onPlayerKick(PlayerQuitEvent event) {
		try {
			Player player = event.getPlayer();
			SBInventorySaveTask.saveInventory(player, player.getWorld());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPlayerQuit(PlayerQuitEvent event) {
		try {
			Player player = event.getPlayer();
			SBInventorySaveTask.saveInventory(player, player.getWorld());
			if (plugin.inventories.containsKey(player)) {
				plugin.inventories.remove(player);
			}
			if (plugin.inventoriesSize.containsKey(player)) {
				plugin.inventoriesSize.remove(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
