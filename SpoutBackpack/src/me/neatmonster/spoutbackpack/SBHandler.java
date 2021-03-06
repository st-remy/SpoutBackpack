package me.neatmonster.spoutbackpack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class SBHandler {
	SpoutBackpack plugin;
	boolean spoutBackpackEnabled = false;

	/**
	 * Primary constructor. If 'spoutBackpackEnabled' equal true and 'plugin' is
	 * initialized, the server is running SpoutBackpack.
	 */
	public SBHandler() {
		Plugin SpoutBackpackPlugin = (SpoutBackpack) Bukkit.getServer()
				.getPluginManager().getPlugin("SpoutBackpack");
		if (SpoutBackpackPlugin == null) {
			return;
		}
		spoutBackpackEnabled = true;
		plugin = (SpoutBackpack) SpoutBackpackPlugin;
	}
	
	/**
	 * Return if or not the player's Backpack is open.
	 * 
	 * @param player
	 *            The player you want to get the inventory.
	 * @return open Player's Backpack opened or not.
	 */
	public boolean isOpenSpoutBackpack(Player player) {
		return plugin.isOpenBackpack(player);
	}

	/**
	 * Get the opened SpoutBackpack inventory of a player.
	 * 
	 * @param player
	 *            The player you want to get the inventory.
	 * @return inventory The SpoutBackpack inventory of this player.
	 */
	public Inventory getOpenedSpoutBackpack(Player player) {
		return plugin.getOpenedBackpack(player);
	}

	/**
	 * Get the closed SpoutBackpack inventory of a player.
	 * 
	 * @param player
	 *            The player you want to get the inventory.
	 * @return inventory The SpoutBackpack inventory of this player.
	 */
	public Inventory getClosedSpoutBackpack(Player player) {
		return plugin.getClosedBackpack(player);
	}

	/**
	 * Set the closed SpoutBackpack inventory of a player.
	 * 
	 * @param player
	 *            The player you want to set the inventory.
	 * @param inventory
	 *            The SpoutBackpack inventory of this player.
	 */
	public void setClosedSpoutBackpack(Player player, Inventory inventory) {
		plugin.setClosedBackpack(player, inventory);
		return;
	}

	/**
	 * Check if the server is running SpoutBackpack.
	 * 
	 * @return true if SpoutBackpack is running on the server.
	 */
	public boolean hasSpoutBackpack() {
		return spoutBackpackEnabled;
	}
}
