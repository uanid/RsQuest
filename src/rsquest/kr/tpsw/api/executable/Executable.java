package rsquest.kr.tpsw.api.executable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rstimer.kr.tpsw.api.bukkit.API;
import rstimer.kr.tpsw.api.bukkit.PlayersAPI;

public abstract class Executable {

	public static final String VERSION = "1.0.9";

	protected int value = 0;
	protected double dvalue = 0;
	protected String message = null;
	protected List<String> args = null;
	protected boolean isAllPlayer = false;

	// command= type= cmdtype= value= message=
	// 1.0.1 message=추가
	// 1.0.2 yml [load, save]기능 추가, exrsprefix추가, 기존 serialize미작동
	// 1.0.3 exitemstack 추가
	// 1.0.4 색상코드 자동 치환
	// 1.0.5 player에 null도 들어가는 경우 체크함, isallplayer변수 추가
	// 1.0.6 105에서 무한 루프 걸리던 오류 해결, run >> abstractRun으로 변경
	// 1.0.7 getExcutable(Map<String, Object> map)메서드 이름을 getExeCutable로 변경
	// 1.0.8 serialize제거, exitemstack 아이템 코드 잘못 지정되는 오류 해결
	// 1.0.9 1.6.1이상 rsstats와 호환되게 수정

	@SuppressWarnings("unchecked")
	public static Executable getExecutable(Map<String, Object> map) {
		String type = (String) map.get("type");
		Executable ex;
		if (type.equals("command") || type.equals("cmd")) {
			ex = new ExCommand();
		} else if (type.equals("rsmoney2")) {
			ex = new ExRsMoney2();
		} else if (type.equals("rpgexpsystem")) {
			ex = new ExRpgExpSystem();
		} else if (type.equals("rsstats")) {
			ex = new ExRsStats();
		} else if (type.equals("economy")) {
			ex = new ExEconomy();
		} else if (type.equals("rsprefix")) {
			ex = new ExRsPrefix();
		} else if (type.equals("itemstack")) {
			ex = new ExItemStack();
		} else {
			return null;
		}
		if (map.containsKey("value")) {
			ex.value = (int) map.get("value");
			if (ex instanceof ExRsPrefix) {
				((ExRsPrefix) ex).updateValue();
			}
		}
		if (map.containsKey("dvalue"))
			ex.dvalue = (int) map.get("dvalue");
		if (map.containsKey("message"))
			ex.message = (String) map.get("message");
		if (map.containsKey("args"))
			ex.args = (List<String>) map.get("args");
		if(map.containsKey("allplayer"))
			ex.isAllPlayer = (boolean) map.get("allplayer");
		if (map.containsKey("command"))
			((ExCommand) ex).cmd = (String) map.get("command");
		if (ex instanceof ExItemStack) {
			((ExItemStack) ex).updateVariables();
		}
		return ex;
	}

	public static List<String> getInfo() {
		List<String> list = new LinkedList<String>();
		list.add("§aExecutable: §e" + VERSION);
		list.add("§a└ExEconomy: §e" + ExEconomy.hookedPlugin);
		list.add("§a└ExRpgExpSystem: §e" + ExRpgExpSystem.hookedPlugin);
		list.add("§a└ExRsMoney2: §e" + ExRsMoney2.hookedPlugin);
		list.add("§a└ExRsStats: §e" + ExRsStats.hookedPlugin);
		list.add("§a└ExRsPrefix: §e" + ExRsPrefix.hookedPlugin);
		return list;
	}

	public static Executable getExecutable(String[] parm) {
		return getExecutable(Arrays.asList(parm));
	}

	public static Executable getExecutable(List<String> parm) {
		try {
			Executable ex = null;

			List<String> list = new LinkedList<String>();
			boolean isdotdot = false;
			boolean isExcutableParm = false;
			for (int i = 0; i < parm.size(); i++) {
				String s = parm.get(i);
				if (s.startsWith("type=") || s.startsWith("command=") || s.startsWith("cmdtype=") || s.startsWith("value=") || s.startsWith("message=") || s.startsWith("allplayer=")) {
					isExcutableParm = true;
				}
				if (isExcutableParm)
					if (s.startsWith("command='") || s.startsWith("message='") || s.startsWith("arg='")) {
						if (!s.endsWith("\'"))
							isdotdot = true;
						list.add(s);
					} else if (isdotdot) {
						int in = list.size() - 1;
						list.set(in, list.get(in) + " " + s);
						if (s.endsWith("\'")) {
							isdotdot = false;
						}
					} else {
						list.add(parm.get(i).replace("&", "§"));
					}
			}// 쓸모없는 인자 처리

			for (String line : list) {
				if (line.startsWith("type=")) {
					String type = line.substring(5, line.length());
					if (type.equals("command") || type.equals("cmd")) {
						ex = new ExCommand();
					} else if (type.equals("rsmoney2")) {
						ex = new ExRsMoney2();
					} else if (type.equals("rpgexpsystem")) {
						ex = new ExRpgExpSystem();
					} else if (type.equals("rsstats")) {
						ex = new ExRsStats();
					} else if (type.equals("economy")) {
						ex = new ExEconomy();
					} else if (type.equals("rsprefix")) {
						ex = new ExRsPrefix();
					} else if (type.equals("itemstack")) {
						ex = new ExItemStack();
					} else {
						return null;
					}
				} else if (line.startsWith("command=")) {
					String cmd = line.substring(8, line.length());
					if (hasDotDot(cmd)) {
						cmd = cmd.substring(1, cmd.length() - 1);
					}
					if (ex instanceof ExCommand) {
						((ExCommand) ex).cmd = cmd;
					} else {
						return null;
					}
				} else if (line.startsWith("cmdtype=")) {
					String t = line.substring(8, line.length());
					int type = 0;
					switch (t) {
					case "cmd":
						type = 1;
						break;
					case "cmdop":
						type = 2;
						break;
					case "cmdcon":
						type = 3;
						break;
					case "chat":
						type = 4;
						break;
					case "chatop":
						type = 5;
						break;
					}
					if (ex instanceof ExCommand) {
						ex.value = type;
					} else {
						return null;
					}
				} else if (line.startsWith("value=")) {
					String va = line.substring(6, line.length());
					if (API.isDouble(va)) {
						double dd = Double.valueOf(va);
						ex.value = (int) dd;
						ex.dvalue = dd;
						if (ex instanceof ExRsPrefix) {
							((ExRsPrefix) ex).updateValue();
						}
					} else {
						return null;
					}
				} else if (line.startsWith("amount=")) {
					String va = line.substring(7, line.length());
					if (API.isDouble(va)) {
						double dd = Double.valueOf(va);
						ex.value = (int) dd;
						ex.dvalue = dd;
					} else {
						return null;
					}
				} else if (line.startsWith("message=")) {
					String va = line.substring(8, line.length());
					if (hasDotDot(va)) {
						va = va.substring(1, va.length() - 1);
					}
					ex.message = va;
				} else if (line.startsWith("allplayer=")) {
					String va = line.substring(10, line.length());
					if (va.equals("false")) {
						ex.isAllPlayer = false;
					} else if (va.equals("true")) {
						ex.isAllPlayer = true;
					} else {
						return null;
					}
				} else if (line.startsWith("arg=")) {
					String va = line.substring(4, line.length());
					if (hasDotDot(va)) {
						va = va.substring(1, va.length() - 1);
					}
					if (ex.args == null) {
						ex.args = new LinkedList<String>();
					}
					ex.args.add(va);
				} else {
					return null;
				}
			}
			if (ex instanceof ExItemStack) {
				((ExItemStack) ex).updateVariables();
			}
			return ex;
		} catch (Exception e) {

		}
		return null;// 미완성
	}

	private static boolean hasDotDot(String s) {
		return s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'';
	}

	public void run(CommandSender player) throws Exception {
		if (isAllPlayer) {
			for (Player p : PlayersAPI.getOnlinePlayers()) {
				this.abstractRun(p);
			}
		} else {
			this.abstractRun((Player) player);
		}
	}

	abstract protected void abstractRun(Player player) throws Exception;

	// player객체에 null넣을 경우 오류 발생 가능성 있음
	// null아니면 오류 안 남

	abstract public String getDisplayMsg();

	abstract public Map<String, Object> toMapForSave(Map<String, Object> map);
}
