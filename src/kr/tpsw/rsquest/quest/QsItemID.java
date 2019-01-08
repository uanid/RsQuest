package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.Map;

import rsquest.kr.tpsw.api.bukkit.API;
import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;

public abstract class QsItemID extends Quest implements Serializable {

	private static final long serialVersionUID = -7208343246244999016L;
	public int[] itemId;// 아이템 코드

	protected QsItemID(String name) {
		super(name);
		this.itemId = new int[] { 0, 0 };
	}

	protected QsItemID(Map<String, Object> dataMap) {
		super(dataMap);
		this.itemId = API.getItemCode((String) dataMap.get("idvalue"));
	}

	abstract public String getDescription(int count);

	abstract public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd);

}
