package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.Map;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;

import org.bukkit.event.block.BlockBreakEvent;

public class QsBreak extends QsItemID implements Serializable {

	private static final long serialVersionUID = -2943730181166459434L;

	public QsBreak(String name) {
		super(name);
	}

	public QsBreak(Map<String, Object> dataMap) {
		super(dataMap);
	}

	@Override
	public String getDescription(int count) {
		return super.description + " (" + count + "/" + end + ")";
	}

	@Override
	public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd) {
		if (event instanceof BlockBreakEvent) {
			int blockid = ((BlockBreakEvent) event).getBlock().getTypeId();
			if (blockid == super.itemId[0]) {
				qd.addEndCount();
				return qp.isEndCountOver(qd);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}//퀘스트 종료 카운트 증가, 완료 여부 체크

	public String toString() {
		return "§aName=§e" + this.name + "§a, End=§e" + this.end + "§a, Type=§e" + "QsBreak" + "§a, Reward=§e" + reward.getDisplayMsg() + "§a, IsRandom=§e" + isRandom;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		super.setQuestToMap(map);
		map.put("type", "QsBreak");
		map.put("idvalue", itemId[0] + ":" + itemId[1]);
		return map;
	}

}
