package me.neatmonster.spoutbackpack;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.inventory.InventoryClickEvent;
import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.event.inventory.InventorySlotType;

public class SBInventoryListener extends InventoryListener {

	private SpoutBackpack plugin;

	public SBInventoryListener(SpoutBackpack plugin) {
		this.plugin = plugin;
	}

	public void onInventoryOpen(InventoryOpenEvent event) {
		if (plugin.inventoryDisabled) {
			event.setCancelled(true);
		}
	}

	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = event.getPlayer();
		if (!plugin.openedInventoriesOthers.containsKey(player.getName())) {
			if (plugin.openedInventories.containsKey(player.getName())) {
				if (plugin.widgets.containsKey(player.getName())
						&& plugin.useWidget == true) {
					plugin.widgets.get(player.getName()).setVisible(false)
							.setDirty(true);
				}
				plugin.openedInventories.remove(player.getName());
			}
			Inventory inv = event.getInventory();
			if (inv.getName().equals(plugin.inventoryName)
					&& inv.getSize() == plugin.inventoriesSize.get(player
							.getName())) {
				plugin.inventories.put(player.getName(), inv.getContents());
			}
		} else {
			if (plugin.openedInventories
					.containsKey(plugin.openedInventoriesOthers.get(player
							.getName()))) {
				plugin.openedInventories.remove(plugin.openedInventoriesOthers
						.get(player.getName()));
			}
			Inventory inv = event.getInventory();
			if (inv.getName().equals(plugin.inventoryName)
					&& inv.getSize() == plugin.inventoriesSize
							.get(plugin.openedInventoriesOthers.get(player
									.getName()))) {
				plugin.inventories.put(
						plugin.openedInventoriesOthers.get(player.getName()),
						inv.getContents());
				plugin.openedInventoriesOthers.remove(player.getName());
			}
		}
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = event.getPlayer();
		if (plugin.openedInventoriesOthers.containsKey(event.getPlayer()
				.getName())) {
			player = plugin.getServer().getPlayer(
					plugin.openedInventoriesOthers.get(event.getPlayer()));
		}
		InventorySlotType clickedSlotType = event.getSlotType();
		Inventory inv = event.getInventory();
		String invName = inv.getName();
		ItemStack clickedItem = event.getItem();
		int slot = event.getSlot();
		if ((plugin.openedInventories.containsKey(player.getName()) || plugin.openedInventoriesOthers
				.containsKey(player.getName()))
				&& clickedSlotType != InventorySlotType.CONTAINER
				&& invName.equals("Inventory")
				&& (plugin.blackOrWhiteList == 1
						&& plugin.blacklist.contains(clickedItem.getTypeId()) || plugin.blackOrWhiteList == 2
						&& !plugin.whitelist.contains(clickedItem.getTypeId()))) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED
					+ "You aren't allowed to move this into your "
					+ plugin.inventoryName + "!");
			return;
		}
		if (clickedSlotType == InventorySlotType.CONTAINER
				&& invName.equals(plugin.inventoryName) && clickedItem != null) {
			ItemStack is = inv.getItem(slot);
			is.setAmount(is.getAmount() - clickedItem.getAmount());
			plugin.updateInventory(player, inv.getContents());
		}
	}

	public static ItemStack[] removeElements(ItemStack[] input,
			ItemStack deleteMe) {
		List<ItemStack> result = new LinkedList<ItemStack>();

		for (ItemStack item : input)
			if (!deleteMe.equals(item))
				result.add(item);

		return result.toArray(input);
	}
}
