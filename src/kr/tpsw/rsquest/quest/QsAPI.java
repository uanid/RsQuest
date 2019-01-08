package kr.tpsw.rsquest.quest;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kr.tpsw.rsquest.RsQuest;

public class QsAPI {

	private static Map<String, Quest> questMap = new HashMap<String, Quest>();
	// 퀘스트 전체 맵
	private static List<Quest> randomQuests = new LinkedList<Quest>();
	// 랜덤퀘만 모은것

	private static Random ran = new Random();

	public static Quest getQuest(Map<String, Object> map) {
		try {
			String type = (String) map.get("type");
			Quest qs;
			if (type.equals("QsBreak")) {
				qs = new QsBreak(map);
			} else if (type.equals("QsCrafting")) {
				qs = new QsCrafting(map);
			} else if (type.equals("QsCustom")) {
				qs = new QsCustom(map);
			} else if (type.equals("QsEatFood")) {
				qs = new QsEatFood(map);
			} else if (type.equals("QsKill")) {
				qs = new QsKill(map);
			} else {
				return null;
			}

			if (qs == null || qs.getReward() == null) {
				return null;
			}
			return qs;
		} catch (Exception e) {
			System.out.println("[" + RsQuest.pluginName + "] " + map.get("name") + " 퀘스트 로드 중 오류 발생");
			e.printStackTrace();
			return null;
		}// 오류 검증
	}

	public static void setQuest(String data, Quest qs) {
		questMap.put(data, qs);
		if (qs.isRandom && !randomQuests.contains(qs)) {
			randomQuests.add(qs);
		}
	}

	public static void removeQuest(Quest qs) {
		int uuid = qs.getUUID();
		for (Quest quest : questMap.values()) {
			if (quest.getRequireQuestUUID().contains(uuid)) {
				quest.getRequireQuestUUID().remove(uuid);
			}
		}
		questMap.remove(qs);
		if (randomQuests.contains(qs)) {
			randomQuests.remove(qs);
		}
	}

	public static Quest getQuest(String name) {
		return questMap.get(name);
	}

	public static Quest getQuest(int uuid) {
		for (Quest qs : questMap.values()) {
			if (qs.getUUID() == uuid) {
				return qs;
			}
		}
		return null;
	}

	public static boolean isQuestType(String type) {
		return type.equals("break") || type.equals("crafting") || type.equals("custom") || type.equals("eatfood") || type.equals("kill");
	}

	public static boolean hasQuest(String name) {
		return questMap.containsKey(name);
	}

	public static Collection<Quest> getQuests() {
		return questMap.values();
	}

	public static Quest getRandomQuest() {
		if (randomQuests.size() == 0) {
			return null;
		} else {
			return randomQuests.get(ran.nextInt(randomQuests.size()));
		}
	}

	public static int createRandomUUID() {
		while (true) {
			int uuid = ran.nextInt(2100000000);
			for (Quest qs : questMap.values()) {
				if (qs.getUUID() == uuid) {
					continue;
				}
			}
			return uuid;
		}
	}
}
