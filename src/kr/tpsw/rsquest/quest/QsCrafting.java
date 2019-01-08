package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.Map;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;

import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class QsCrafting extends QsItemID implements Serializable {

	private static final long serialVersionUID = -3352578438449841214L;

	public QsCrafting(String name) {
		super(name);
	}

	public QsCrafting(Map<String, Object> dataMap) {
		super(dataMap);
	}

	@Override
	public String getDescription(int count) {
		return super.description + "(" + count + "/" + end + ")";
	}

	@Override
	public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd) {
		if (event instanceof CraftItemEvent) {
			ItemStack is = ((CraftItemEvent) event).getCurrentItem();
			if (is.getTypeId() == super.itemId[0] && is.getDurability() == super.itemId[1]) {
				qd.addEndCount();
				return qp.isEndCountOver(qd);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public String toString() {
		return "」aName=」e" + this.name + "」a, End=」e" + this.end + "」a, Type=」e" + "QsCrafting" + "」a, Reward=」e" + reward.getDisplayMsg() + "」a, IsRandom=」e" + isRandom;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		super.setQuestToMap(map);
		map.put("type", "QsCrafting");
		map.put("idvalue", itemId[0] + ":" + itemId[1]);
		return map;
	}

}
