package rsquest.kr.tpsw.api.executable;

import java.util.List;
import java.util.Map;

import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.PrefixPlayer;
import kr.tpsw.rsprefix.api.RanPreAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ExRsPrefix extends Executable {

	public static boolean hookedPlugin;
	private transient String msg;

	static {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("RsPrefix");
		hookedPlugin = plugin != null;
	}

	public ExRsPrefix() {
		this(0, null);
	}

	public ExRsPrefix(int value, List<String> args) {
		this.value = value;
		this.args = args;
	}

	public void updateValue() {
		if (value == 0) {
			msg = "Book";
		} else if (value == 1) {
			msg = "Instant";
		} else if (value == 2) {
			if (args == null || args.size() == 0) {
				msg = "@null";
			} else {
				msg = args.get(0);
			}
		} else {
			msg = "#null";
		}
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		if (hookedPlugin) {
			if (value == 0) {
				RanPreAPI.giveRanPreBook(player);
			} else if (value == 1) {
				RanPreAPI.runRandomPrefix(player);
			} else if (value == 2) {
				if (args == null || args.size() == 0) {
					player.sendMessage("§cExcutable의 정보가 잘못 입력되어있습니다.");
					return;
				}
				PrefixPlayer pp = FileAPI.getPrefixPlayer(player.getName());
				List<String> list = pp.getList();
				if (!list.contains(args.get(0))) {
					list.add(args.get(0));
					pp.needUpdateInv = true;
				}
			} else {
				player.sendMessage("§cExcutable의 정보가 잘못 입력되어있습니다.");
			}
		}
	}

	@Override
	public String getDisplayMsg() {
		return "RsPrefix +" + msg;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "rsprefix");
		map.put("value", value);
		map.put("args", args);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		if (args == null || args.size() == 0) {
			return "Class=ExRsPrefix, IsHooked=" + hookedPlugin + ", Value=" + value + ", arg0=null" + ", allplayer" + isAllPlayer;
		} else {
			return "Class=ExRsPrefix, IsHooked=" + hookedPlugin + ", Value=" + value + ", arg0=" + args.get(0) + ", allplayer" + isAllPlayer;
		}

	}
}
