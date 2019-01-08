package rsquest.kr.tpsw.api.executable;

import java.util.Map;

import kr.tpsw.rsstats.api.StatsAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ExRsStats extends Executable {

	public static boolean hookedPlugin;

	static {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("RsStats");
		hookedPlugin = plugin != null;
	}

	public ExRsStats() {
		this(0);
	}

	public ExRsStats(int value) {
		this.value = value;
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		if (hookedPlugin) {
			StatsAPI.getStatsPlayer(player.getName()).addAvailablePoint(value);
		}
	}

	@Override
	public String getDisplayMsg() {
		return "RsStats +" + value;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "rsstats");
		map.put("value", value);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		return "Class=ExRsStats, IsHooked=" + hookedPlugin + ", Value=" + value + ", allplayer" + isAllPlayer;
	}
}
