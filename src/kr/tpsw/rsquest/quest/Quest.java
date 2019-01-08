package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;
import rsquest.kr.tpsw.api.executable.ExCommand;
import rsquest.kr.tpsw.api.executable.Executable;

public abstract class Quest implements Serializable {

	private static final long serialVersionUID = 8160920153910211282L;
	// 퀘스트에 대한 정보만 담음
	// 퀘스트 진행도 체크는 이 클래스에서
	// 퀘스트 진행도 저장은 QsPlayer클래스로

	protected String display;
	protected String description;
	protected String name;

	protected Executable reward;
	protected int end;
	protected int uuid;
	protected List<Integer> requireUUID;// 선행 퀘스트, -1일 경우 무시함
	protected boolean isRandom;

	public Quest(String name) {
		this.name = name;
		this.display = name + "퀘스트";
		this.description = name + "을 달성하세요.";
		this.reward = new ExCommand("say 퀘스트 완료", "cmdcon");
		this.requireUUID = new ArrayList<Integer>();
		this.end = 1;
		this.uuid = QsAPI.createRandomUUID();
		this.isRandom = true;
	}// 퀘스트 신규 생성

	@SuppressWarnings("unchecked")
	public Quest(Map<String, Object> dataMap) {
		this.name = (String) dataMap.get("name");
		this.description = (String) dataMap.get("description");
		this.display = (String) dataMap.get("display");
		this.end = (int) dataMap.get("end");
		this.uuid = (int) dataMap.get("uuid");
		Object obj;
		if ((obj = dataMap.get("requireUUID")) instanceof List) {
			this.requireUUID = new ArrayList<Integer>((List<Integer>) obj);
		} else {
			this.requireUUID = new ArrayList<Integer>();
		}
		this.reward = Executable.getExecutable((Map<String, Object>) dataMap.get("executable"));
		this.isRandom = (boolean) dataMap.get("isRandom");
	}

	public String getName() {
		return name;
	}

	public int getUUID() {
		return uuid;
	}

	public Executable getReward() {
		return reward;
	}

	public List<Integer> getRequireQuestUUID() {
		return requireUUID;
	}

	public int getEnd() {
		return end;
	}

	public String getDisplay() {
		return this.display;
	}

	public boolean isRandom() {
		return isRandom;
	}

	abstract public String getDescription(int count);

	// getter메서드 종료, 8개

	public void setEnd(int end) {
		this.end = end;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setReward(Executable ex) {
		this.reward = ex;
	}

	public void addRequireUUID(int uuid) {
		this.requireUUID.add(uuid);
	}

	public void setIsRandom(boolean bool) {
		this.isRandom = bool;
	}

	// setter메서드 종료, 6개

	public String toString() {
		return "§aName=§e" + this.name + "§a, end=§e" + this.end + "§a, Type=§enull§a, Reward=§enull";
	}

	protected void setQuestToMap(Map<String, Object> map) {
		map.put("name", name);
		map.put("end", end);
		map.put("uuid", uuid);
		map.put("isRandom", isRandom);
		map.put("requireUUID", requireUUID);
		map.put("display", display);
		map.put("description", description);
		map.put("executable", reward.toMapForSave(new LinkedHashMap<String, Object>()));
	}

	abstract public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd);

	// 퀘스트 카운트 증가 여부 판별

	abstract public Map<String, Object> toMapForSave(Map<String, Object> map);
	// 데이터 저장
}
