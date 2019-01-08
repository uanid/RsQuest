package kr.tpsw.rsquest.quest;

import java.io.Serializable;
import java.util.Map;

import kr.tpsw.rsquest.RsQuest;
import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QuestData;
import me.ThaH3lper.com.Api.BossDeathEvent;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class QsKill extends Quest implements Serializable {

	private static final long serialVersionUID = -2943730181166459434L;
	public String entityName;

	public QsKill(String name) {
		super(name);
		this.entityName = "Cow";
	}
	
	public QsKill(Map<String, Object> dataMap) {
		super(dataMap);
		this.entityName = (String) dataMap.get("idvalue");
	}

	@Override
	public String getDescription(int count) {
		return super.description + " (" + count + "/" + end + ")";
	}

	@Override
	public int updateQuestStatus(Object event, QsPlayer qp, QuestData qd) {
		if (event instanceof EntityDeathEvent) {
			EntityDeathEvent eventd = (EntityDeathEvent) event;
			EntityType entityT = eventd.getEntityType();
			LivingEntity entityL = eventd.getEntity();
			String entity = null;

			if ((entity = entityL.getCustomName()) == null) {
				entity = entityT.getName();
			}// 엔티티 이름 쓰기

			// System.out.println("이벤트 작동 " + entity);
			if (entity.equals(entityName)) {
				qd.addEndCount();
				return qp.isEndCountOver(qd);
			} else {
				return 0;
			}

		} else if (RsQuest.isEpicBossHooked && event instanceof BossDeathEvent) {
			BossDeathEvent eventd = (BossDeathEvent) event;
			if (eventd.getBossName().equals(entityName)) {
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
		return "§aName=§e" + this.name + "§a, End=§e" + this.end + "§a, Type=§e" + "QsKill" + "§a, Reward=§e" + reward.getDisplayMsg() + "§a, IsRandom=§e" + isRandom;
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		super.setQuestToMap(map);
		map.put("type", "QsKill");
		map.put("idvalue", entityName);
		return map;
	}

}
