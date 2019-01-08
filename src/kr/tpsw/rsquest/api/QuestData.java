package kr.tpsw.rsquest.api;

import java.util.LinkedHashMap;
import java.util.Map;

import kr.tpsw.rsquest.quest.QsAPI;
import kr.tpsw.rsquest.quest.Quest;

public class QuestData {

	//Äù½ºÆ® °´Ã¼¸¦ ÇÑ¹ø µÑ·¯½Ó
	
	private Quest quest = null;
	private int endCount = 0;
	private int questClearCount = 0;
	private int questUpdateCount = 0;

	public QuestData() {

	}

	public QuestData(Map<String, Object> map) {
		int i = (int) map.get("endCount");
		int j = (int) map.get("questClearCount");
		int k = (int) map.get("questUpdateCount");
		quest = QsAPI.getQuest((String) map.get("quest"));
		this.endCount = i;
		this.questClearCount = j;
		this.questUpdateCount = k;
	}

	public Map<String, Object> getDataMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (hasQuest()) {
			map.put("quest", quest.getName());
		} else {
			map.put("quest", null);
		}
		map.put("endCount", endCount);
		map.put("questClearCount", questClearCount);
		map.put("questUpdateCount", questUpdateCount);
		return map;
	}
	
	public boolean isEndCountOver() {// Äù½ºÆ® ´Þ¼º Á¶°Ç È®ÀÎ
		return getEndCount() >= getQuest().getEnd();
	}

	public boolean hasQuest() {
		return quest != null;
	}

	public Quest getQuest() {
		return quest;
	}

	public int getquestClearCount() {
		return questClearCount;
	}

	public int getquestUpdateCount() {
		return questUpdateCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public void addEndCount() {
		this.endCount++;
	}

	public void addQuestClearCount() {
		this.questClearCount++;
	}

	public void addQuestUpdateCount() {
		this.questUpdateCount++;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}
}
