package kr.tpsw.rsquest.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rsquest.kr.tpsw.api.publica.YamlConfiguration;

public class QsPlayerAPI {

	private static Map<String, QsPlayer> playerMap = new HashMap<String, QsPlayer>();
	private static Map<String, QsPlayer> onlinePlayerMap = new HashMap<String, QsPlayer>();

	public static void moveToOnline(String name) {
		onlinePlayerMap.put(name, playerMap.get(name));
	}

	public static void moveToOffline(String name) {
		onlinePlayerMap.remove(name);
	}// 플레이어 변수 위치 이동, 2개

	public static boolean hasPlayer(String name) {
		return playerMap.containsKey(name);
	}

	public static void loadPlayer(File file) {
		YamlConfiguration data = new YamlConfiguration(file);
		String name = data.getString("name");
		loadPlayer(data, name);
	}

	public static void loadPlayer(String name) {
		YamlConfiguration data = new YamlConfiguration("plugins\\RsQuest\\Users\\" + name + ".txt");
		loadPlayer(data, name);
	}

	private static void loadPlayer(YamlConfiguration data, String name) {
		QsPlayer qp;
		if (data.getKeys(false).size() == 0) {
			qp = new QsPlayer(name);
		} else {
			qp = new QsPlayer(name, data);
		}
		playerMap.put(name, qp);
	}// 플레이어 로드 메서드, 3개

	public static void savePlayer(String name) {
		YamlConfiguration data = new YamlConfiguration("plugins\\RsQuest\\Users\\" + name + ".txt");
		QsPlayer qp = playerMap.get(name);
		data.setAllMap(qp.getDataMap());
		data.saveYaml();
	}// 플레이어 세이브 메서드, 1개

	public static QsPlayer getPlayer(String name) {
		return playerMap.get(name);
	}

	public static Collection<QsPlayer> getPlayers() {
		return playerMap.values();
	}

	public static String searchOfflinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (String player : playerMap.keySet()) {
				if (player.toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player);
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
}
