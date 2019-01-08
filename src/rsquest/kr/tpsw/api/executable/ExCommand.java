package rsquest.kr.tpsw.api.executable;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExCommand extends Executable {

	protected String cmd;

	public ExCommand() {
		cmd = "say a";
		value = 3;
		message = "ExCommand +알 수 없음";
	}

	public ExCommand(String cmd, String cmdtype) {
		message = "ExCommand +알 수 없음";
		this.cmd = cmd;
		switch (cmdtype) {
		case "cmd":
			value = 1;
			break;
		case "cmdop":
			value = 2;
			break;
		case "cmdcon":
			value = 3;
			break;
		case "chat":
			value = 4;
			break;
		case "chatop":
			value = 5;
			break;
		}
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		switch (value) {
		case 1:
			Bukkit.dispatchCommand(player, cmd);
			break;
		case 2:
			if (player.isOp()) {
				Bukkit.dispatchCommand(player, cmd);
			} else {
				player.setOp(true);
				Bukkit.dispatchCommand(player, cmd);
				player.setOp(false);
			}
			break;
		case 3:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
			break;
		case 4:
			player.chat("/" + cmd);
			break;
		case 5:
			if (player.isOp()) {
				player.chat("/" + cmd);
			} else {
				player.setOp(true);
				player.chat("/" + cmd);
				player.setOp(false);
			}
			break;
		}
	}

	@Override
	public String getDisplayMsg() {
		return message;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "command");
		map.put("value", value);
		map.put("command", cmd);
		map.put("message", message);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		return "Class=ExCommand, cmd=" + cmd + ", value=" + value + ", message=" + message + ", allplayer" + isAllPlayer;
	}

}
