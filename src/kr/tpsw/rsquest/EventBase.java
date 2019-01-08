package kr.tpsw.rsquest;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QsPlayerAPI;
import kr.tpsw.rsquest.api.QuestData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventBase implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		onJoin(event.getPlayer());
	}

	public void onJoin(Player player) {
		if (QsPlayerAPI.hasPlayer(player.getName())) {
			// 추가 작업 없음
		} else {
			QsPlayerAPI.loadPlayer(player.getName());
			// 신규 등록
		}
		QsPlayerAPI.moveToOnline(player.getName());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		QsPlayerAPI.moveToOffline(event.getPlayer().getName());
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Player p;
		if ((p = event.getEntity().getKiller()) instanceof Player) {
			QsPlayer qp = QsPlayerAPI.getPlayer(p.getName());
			QuestData qd;
			if ((qd = qp.getLinkQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
				}
			}

			if (!qp.isAchievedRandomQuest() && (qd = qp.getRandomQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
					
				}
				
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			QsPlayer qp = QsPlayerAPI.getPlayer(event.getPlayer().getName());
			QuestData qd;
			if ((qd = qp.getLinkQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
				}
			}

			if (!qp.isAchievedRandomQuest() && (qd = qp.getRandomQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
					
				}
				
			}
		}
	}

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if (!event.isCancelled()) {
			QsPlayer qp = QsPlayerAPI.getPlayer(event.getWhoClicked().getName());
			QuestData qd;
			if ((qd = qp.getLinkQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
				}
			}

			if (!qp.isAchievedRandomQuest() && (qd = qp.getRandomQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
					
				}
				
			}
		}
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		if (!event.isCancelled()) {
			QsPlayer qp = QsPlayerAPI.getPlayer(event.getPlayer().getName());
			QuestData qd;
			if ((qd = qp.getLinkQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
				}
			}

			if (!qp.isAchievedRandomQuest() && (qd = qp.getRandomQuestData()).hasQuest()) {
				int i = qd.getQuest().updateQuestStatus(event, qp, qd);
				if (i != 0) {
					Bukkit.getPlayer(qp.getName()).sendMessage("§c" + qd.getQuest().getDisplay() + "§6(을)를 달성했습니다.");
				
				}
			}
		}
	}

}
