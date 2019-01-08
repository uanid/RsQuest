package rsquest.kr.tpsw.api.bukkit;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {
	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;
	public static boolean isVaulthooked = false;
	public static boolean isEconHook = false;
	public static boolean isPermHook = false;
	public static boolean isChatHook = false;

	public static boolean hookSetup() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
			isVaulthooked = true;
			isEconHook = setUpEconomy();
			isPermHook = setUpPermissions();
			isChatHook = setUpChat();
		}
		return isVaulthooked;
	}

	private static boolean setUpEconomy() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = ((Economy) rsp.getProvider());
		return (econ != null);
	}

	private static boolean setUpPermissions() {
		RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		if (rsp == null) {
			return false;
		}
		perms = ((Permission) rsp.getProvider());
		return (perms != null);
	}

	private static boolean setUpChat() {
		RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (rsp == null) {
			return false;
		}
		chat = ((Chat) rsp.getProvider());
		return (chat != null);
	}
}