package kr.tpsw.rsquest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.tpsw.rsquest.quest.QsAPI;
import kr.tpsw.rsquest.quest.Quest;

import org.bukkit.Bukkit;

import rsquest.kr.tpsw.api.publica.YamlConfiguration;

public class QsPlayer {

	private String name;
	private QuestData randomQuest = null;
	private QuestData linkQuest = null;
	// 퀘스트 진행도를 저장하는 객체

	private boolean isAchievedRandomQuest = false;
	private boolean isRefreshedRandomQuest = false;
	private List<Integer> achievedQuestUUID = null;

	public QsPlayer(String name) {
		this.name = name;
		this.achievedQuestUUID = new ArrayList<Integer>();
		this.randomQuest = new QuestData();
		this.linkQuest = new QuestData();
		this.setNewRandomQuest(false);
		// 처음 접속한 유저 생성자임
	}

	@SuppressWarnings({ "unchecked" })
	public QsPlayer(String name, YamlConfiguration dataMap) {// 생성자
		this.name = name;
		this.randomQuest = new QuestData((Map<String, Object>) dataMap.getMap("randomQuest"));
		this.linkQuest = new QuestData((Map<String, Object>) dataMap.getMap("linkQuest"));
		this.isAchievedRandomQuest = dataMap.getBoolean("isAchievedRandomQuest");
		this.isRefreshedRandomQuest = dataMap.getBoolean("isRefreshedRandomQuest");
		this.achievedQuestUUID = (List<Integer>) dataMap.getList("achievedQuestUUID");
	}

	public Map<String, Object> getDataMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("randomQuest", randomQuest.getDataMap());
		map.put("linkQuest", linkQuest.getDataMap());
		map.put("isAchievedRandomQuest", isAchievedRandomQuest);
		map.put("isRefreshedRandomQuest", isRefreshedRandomQuest);
		map.put("achievedQuestUUID", achievedQuestUUID);
		return map;
	}

	public int isEndCountOver(QuestData qd) {
		if (qd.isEndCountOver()) {
			if (qd == randomQuest) {
				setQuestToAchived(randomQuest);
				isAchievedRandomQuest = true;
			} else if (qd == linkQuest) {
				setQuestToAchived(linkQuest);
			} else {
				throw new IllegalArgumentException();
			}
			return 1;
		} else {
			return 0;
		}
	}// 퀘스트 달성 여부로 수정

	private void setQuestToAchived(QuestData qd) {
		if (!achievedQuestUUID.contains(qd.getQuest().getUUID())) {
			achievedQuestUUID.add(qd.getQuest().getUUID());
		}
		try {
			qd.getQuest().getReward().run(Bukkit.getPlayer(this.name));
		} catch (Exception e) {
			System.out.println("[RsQuest] Executable실행 중 오류 발생");
			System.out.println("[RsQuest] Display= " + qd.getQuest().getReward().getDisplayMsg());
			e.printStackTrace();
		}
		qd.setEndCount(0);
		qd.addQuestClearCount();
		qd.setQuest(null);
	}// 메서드 통합

	public List<Integer> getAchievedQuestUUID() {
		return achievedQuestUUID;
	}

	public void setNewRandomQuest(boolean isRefresh) {
		isAchievedRandomQuest = false;
		isRefreshedRandomQuest = isRefresh;

		Quest qs = QsAPI.getRandomQuest();
		randomQuest.setQuest(qs);
		this.randomQuest.setEndCount(0);

		if (qs != null) {
			this.randomQuest.addQuestUpdateCount();
		}
	}// 랜덤퀘스트 업데이트

	public String getName() {
		return name;
	}

	public boolean isAchievedRandomQuest() {
		return isAchievedRandomQuest;
	}

	public boolean isRefreshedRandomQuest() {
		return isRefreshedRandomQuest;
	}

	public QuestData getRandomQuestData() {
		return randomQuest;
	}

	public QuestData getLinkQuestData() {
		return linkQuest;
	}// getter메서드 5개

	public void setIsAchievedRandomQuest(boolean bool) {// 퀘스트 달성
		isAchievedRandomQuest = bool;
	}

	public void setIsRefreshedRandomQuest(boolean bool) {// 퀘스트 달성
		isRefreshedRandomQuest = bool;
	}// setter메서드 2개
}
