package me.neatmonster.spoutbackpack;

import java.util.logging.Logger;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import com.nijikokun.register.payment.Methods;

public class SBRegister extends ServerListener {
	private SpoutBackpack plugin;
	private Methods Methods = null;
	public static Logger logger = Logger.getLogger("minecraft");
	public static String logTag = "[SpoutBackpack]";

	public SBRegister(SpoutBackpack plugin) {
		this.plugin = plugin;
		Methods = new Methods();
	}

	public void onPluginDisable(PluginDisableEvent event) {
		if (Methods != null && Methods.hasMethod()) {
			Boolean check = this.Methods.checkDisabled(event.getPlugin());
			if (check) {
				plugin.Method = null;
				logger.info(logTag
						+ " Payment method was disabled. No longer accepting payments.");
			}
		}
	}

	public void onPluginEnable(PluginEnableEvent event) {
		if (!Methods.hasMethod()) {
			if (Methods.setMethod(event.getPlugin())) {
				plugin.Method = this.Methods.getMethod();
				logger.info(logTag + " Payment method found ("
						+ plugin.Method.getName() + " v"
						+ plugin.Method.getVersion() + ").");
			}
		}
	}
}
