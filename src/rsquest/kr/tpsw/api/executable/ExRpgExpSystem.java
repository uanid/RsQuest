package rsquest.kr.tpsw.api.executable;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;
import to.oa.tpsw.rpgexpsystem.api.a;

public class ExRpgExpSystem extends Executable {

	public static boolean hookedPlugin;

	static {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("RpgExpSystem");
		hookedPlugin = plugin != null;
	}

	public ExRpgExpSystem() {
		this(0);
	}

	public ExRpgExpSystem(int value) {
		this.value = value;
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		if (hookedPlugin) {
			RpgPlayer rp = a.a(player.getName());
			rp.addRpgExp(value);
		}
	}
	
	@Override
	public String getDisplayMsg() {
		return "RpgExpSystem +" + value;
	}
	
	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "rpgexpsystem");
		map.put("value", value);
		map.put("allplayer", isAllPlayer);
		return map;
	}
	
	public String toString() {
		return "Class=ExRpgExpSystem, IsHooked=" + hookedPlugin + ", Value=" + value+ ", allplayer" + isAllPlayer;
	}
}
