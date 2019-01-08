package rsquest.kr.tpsw.api.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class API {

	// 1.0

	public static int[] getItemCode(String str) {
		String[] ids = str.split(":");
		int[] ii = { 0, 0 };
		try {
			if (API.isIntegerPositive(ids[0]) && Material.getMaterial(Integer.valueOf(ids[0])) != null) {
				ii[0] = Integer.valueOf(ids[0]);
				if (ids.length == 2 && API.isIntegerPositive(ids[1])) {
					ii[1] = Short.valueOf(ids[1]);
				}
			}
		} catch (Exception e) {
			int[] i = { 0, 0 };
			return i;
		}
		return ii;
	}

	public static float getDotSecond(float value) {
		return (float) getDotSecond(value);
	}

	public static double getDotSecond(double value) {
		return Double.valueOf(Math.round(value * 100D)) / 100D;
	}// 소수 두번째 자리까지 반환

	public static boolean isInteger(String string) {
		return string.matches("[-]?[0-9]+");
	}

	public static boolean isIntegerPositive(String string) {
		return string.matches("[0-9]+");
	}

	public static boolean isDouble(String string) {
		return string.matches("[-]?([0-9]+|[0-9]+[.][0-9]+)");
	}

	public static boolean isDoublePositive(String string) {
		return string.matches("([0-9]+|[0-9]+[.][0-9]+)");
	}

	public static boolean isEnglish(String string) {
		return string.matches("^[a-zA-Z]+");
	}

	public static boolean isHangle(String string) {
		return string.matches("^[ㄱ-하-ㅣ가-힣]+");
	}

	public static String replaceColorCodeToEmpthy(String string) {
		return string.replaceAll("(&)[a-zA-Z0-9]", "");
	}

	public static String replaceChatColorToEmpthy(String string) {
		return string.replaceAll("(§)[a-zA-Z0-9]", "");
	}

	public static void runCommand(String cmd, String type, Player target) {
		switch (type) {
		case "cmd":
			Bukkit.dispatchCommand(target, cmd);
			break;
		case "cmdop":
			if (target.isOp()) {
				Bukkit.dispatchCommand(target, cmd);
			} else {
				target.setOp(true);
				Bukkit.dispatchCommand(target, cmd);
				target.setOp(false);
			}
			break;
		case "cmdcon":
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
			break;
		case "chat":
			target.chat("/" + cmd);
			break;
		case "chatop":
			if (target.isOp()) {
				target.chat("/" + cmd);
			} else {
				target.setOp(true);
				target.chat("/" + cmd);
				target.setOp(false);
			}
			break;
		}
	}
}
