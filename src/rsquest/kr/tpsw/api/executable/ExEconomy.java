package rsquest.kr.tpsw.api.executable;

import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

import rsquest.kr.tpsw.api.bukkit.VaultHook;

public class ExEconomy extends Executable {

	public static boolean hookedPlugin;
	private static Economy eco;

	static {
		if (!VaultHook.isVaulthooked)
			VaultHook.hookSetup();
		hookedPlugin = VaultHook.isEconHook;
		eco = VaultHook.econ;
	}

	public ExEconomy() {
		this(0);
	}

	public ExEconomy(int value) {
		this.value = value;
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		if (hookedPlugin) {
			eco.depositPlayer(player.getName(), value);
		}
	}

	@Override
	public String getDisplayMsg() {
		return "Economy +" + value;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "economy");
		map.put("value", value);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		return "Class=ExEconomy, IsHooked=" + hookedPlugin + ", Value=" + value+ ", allplayer" + isAllPlayer;
	}
}
