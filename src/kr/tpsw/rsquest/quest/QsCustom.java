package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.Map;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;

public class QsCustom extends Quest implements Serializable {

	private static final long serialVersionUID = 4328462208000003971L;

	public QsCustom(String name) {
		super(name);
	}

	public QsCustom(Map<String, Object> dataMap) {
		super(dataMap);
	}

	@Override
	public String getDescription(int count) {
		return super.description;
	}

	@Override
	public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd) {
		// 커스텀은 이런거 안 쓰니까 상관없잖아!
		return 0;
	}

	public String toString() {
		return "§aName=§e" + this.name + "§a, End=§e" + this.end + "§a, Type=§e" + "QsCustom" + "§a, Reward=§e" + reward.getDisplayMsg() + "§a, IsRandom=§e" + isRandom;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		super.setQuestToMap(map);
		map.put("type", "QsCustom");
		return map;
	}
}
