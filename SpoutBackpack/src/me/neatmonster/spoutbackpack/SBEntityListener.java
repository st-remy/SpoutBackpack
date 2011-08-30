package me.neatmonster.spoutbackpack;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SBEntityListener extends EntityListener {

	private SpoutBackpack plugin;

	public SBEntityListener(SpoutBackpack plugin) {
		this.plugin = plugin;
	}

	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (!plugin.userHasPermission(player, "backpack.nodrop")
					&& plugin.canOpenBackpack(player.getWorld(), player)) {
				if (!((SpoutPlayer) player).isSpoutCraftEnabled()) {
					plugin.loadInventory(player, player.getWorld());
				}
				if (plugin.inventories.containsKey(player.getName())) {
					ItemStack[] items = plugin.inventories
							.get(player.getName());
					plugin.inventories.remove(player.getName());
					for (ItemStack item : items) {
						if (item != null && item.getAmount() > 0) {
							player.getWorld().dropItem(player.getLocation(),
									item);
						}
					}
				}
				if (!((SpoutPlayer) player).isSpoutCraftEnabled()) {
					SBInventorySaveTask
							.saveInventory(player, player.getWorld());
					player.sendMessage(plugin.logTag
							+ " Your "
							+ ChatColor.RED
							+ plugin.inventoryName
							+ ChatColor.WHITE
							+ " has broken! You'll need to use Spout again to fix it.");
				}
			}
		}
	}
}