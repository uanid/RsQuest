package rsquest.kr.tpsw.api.executable;

import java.util.Map;

import kr.tpsw.rsmoney2.MoneyMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ExRsMoney2 extends Executable {

	public static boolean hookedPlugin;

	static {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("RsMoney2");
		hookedPlugin = plugin != null;
	}

	public ExRsMoney2() {
		this(0);
	}

	public ExRsMoney2(int value) {
		this.value = value;
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		if (hookedPlugin) {
			MoneyMap.addMoney(player.getName(), this.value);
		}
	}

	@Override
	public String getDisplayMsg() {
		return "RsMoney2 +" + value;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "rsmoney2");
		map.put("value", value);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		return "Class=ExRsMoney2, IsHooked=" + hookedPlugin + ", Value=" + value+ ", allplayer" + isAllPlayer;
	}
}
