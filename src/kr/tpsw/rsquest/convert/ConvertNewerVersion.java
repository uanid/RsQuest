package kr.tpsw.rsquest.convert;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kr.tpsw.rsquest.RsQuest;
import kr.tpsw.rsquest.WordPressParsing;
import rsquest.kr.tpsw.api.publica.YamlConfiguration;

public class ConvertNewerVersion {

	public static void updateDateFile() {
		double version = getDateFileVersion();
		// System.out.println("버전 " + version);
		if (version == -1) {
			updateDateFile2(version);
			// 1.0-1.0.8 버전의 경우

		} else if (version == -2) {
			// 처음 설치하는 경우
			updateDateFile2(version);
		} else if (version == WordPressParsing.trasferVersion(RsQuest.version)) {
			// 현재 버전과 같은 경우

		} else {
			updateDateFile2(version);
		}
	}

	public static double getDateFileVersion() {
		YamlConfiguration config = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\config.yml");
		// System.out.println(config.getKeys(true));
		if (config.getKeys(true).size() == 3) {
			config.clear();
			return -1;
		} else {
			String v = config.getString("version");
			if (v == null) {
				return -2;
			} else {
				return WordPressParsing.trasferVersion(v);
			}
		}
	}

	private static List<Integer> uuids = new ArrayList<Integer>();
	private static Random ran;

	private static int createRandomUUID() {
		while (true) {
			int uuid = ran.nextInt(2100000000);
			if (uuids.contains(uuid)) {
				continue;
			}
			return uuid;
		}
	}

	@SuppressWarnings("unchecked")
	private static void updateDateFile2(double version) {
		// System.out.println("버전 = " + version);
		YamlConfiguration config = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\config.yml");
		if (version == -2) {
			RsQuestMessage.saveDefaultMessage();
			// System.out.println("초기 셋팅 -2");
			// 버전만 저장함
		} else if (version == -1) {
			System.out.println("[RsQuest] 오래된 플러그인 데이터를 발견했습니다. 새로운 데이터 형식으로 변환합니다...");
			ran = new Random();
			YamlConfiguration quest = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\quests.yml");
			List<Map<String, Object>> qlist = new LinkedList<Map<String, Object>>();
			for (String key : quest.getKeys(false)) {
				Map<String, Object> newMap = new LinkedHashMap<String, Object>();
				Map<String, Object> oldMap = (Map<String, Object>) quest.getMap(key);

				newMap.put("idvalue", oldMap.get("idvalue"));
				newMap.put("description", oldMap.get("description"));
				newMap.put("name", oldMap.get("name"));
				Map<String, Object> exemap = new LinkedHashMap<String, Object>();
				exemap.putAll((Map<? extends String, ? extends Object>) oldMap.get("excutable"));
				newMap.put("executable", exemap);
				newMap.put("type", oldMap.get("type"));
				newMap.put("display", oldMap.get("display"));
				newMap.put("end", oldMap.get("end"));

				newMap.put("isRandom", true);
				newMap.put("requireUUID", new ArrayList<Integer>());
				newMap.put("uuid", createRandomUUID());
				// israndom, requireuuid, uuid

				qlist.add(newMap);
				// quest.set((String) newMap.get("name"), newMap);
			}// 퀘스트 변환
			quest.clear();
			for (Map<String, Object> map : qlist) {
				quest.set((String) map.get("name"), map);
			}

			quest.saveYaml();
			ran = null;

			File users = new File("plugins\\" + RsQuest.pluginName + "\\Users");
			for (File user : users.listFiles()) {
				YamlConfiguration data = new YamlConfiguration(user);
				String quest_name = data.getString("quest_name");
				String name = data.getString("name");
				int quest_count = data.getInt("quest_count");
				int count_clear = data.getInt("count_clear");
				int count_update = data.getInt("count_update");
				data.clear();
				data.set("linkQuest.questUpdateCount", 0);
				data.set("linkQuest.endCount", 0);
				data.set("linkQuest.questClearCount", 0);
				data.set("linkQuest.quest", null);

				data.set("randomQuest.questUpdateCount", count_update);
				data.set("randomQuest.endCount", quest_count);
				data.set("randomQuest.questClearCount", count_clear);
				data.set("randomQuest.quest", quest_name);

				data.set("name", name);
				data.set("achievedQuestUUID", new ArrayList<Integer>());
				data.set("isAchievedRandomQuest", name == null);
				data.set("isRefreshedRandomQuest", name == null);
				data.saveYaml();
			}
			RsQuestMessage.saveDefaultMessage();
		} else if (version == 1.11) {

		} else if (version == 1.12) {

		} else if (version == 1.13) {

		} else if (version == 1.14) {

		} else if (version == 1.2) {
			
		} else {
			System.out.println("[RsQuest] 데이터 파일에 잘못된 버전이 입력되어있습니다.");
			System.out.println("[RsQuest] 플러그인은 종료 없이 그대로 진행합니다. 그러나 오류가 발생할 수 있습니다.");
			return;
		}
		config.set("version", RsQuest.version);
		config.saveYaml();
	}
}
