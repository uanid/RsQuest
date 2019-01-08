package kr.tpsw.rsquest.convert;

import java.util.LinkedList;
import java.util.List;

import kr.tpsw.rsquest.RsQuest;
import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QsPlayerAPI;
import kr.tpsw.rsquest.api.QuestData;
import kr.tpsw.rsquest.quest.Quest;

import org.bukkit.entity.Player;

import rsquest.kr.tpsw.api.publica.YamlConfiguration;

public class RsQuestMessage {

	private static YamlConfiguration msg = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\message.yml");

	private static String LINK_QUEST_COUNT;// 퀘스트 통계
	private static List<String> LINK_QUEST;
	private static String DO_NOT_HAVE_LINK_QUEST;// 가지고 있는 연계퀘 없음

	private static String RANDOM_QUEST_COUNT;// 퀘스트 통계
	private static List<String> RANDOM_QUEST;
	private static String DO_NOT_HAVE_RANDOM_QUEST;// 가지고 있는 랜덤퀘 없음

	private static String IS_ACHIVED_RANDOM_QUEST;// 랜덤 퀘스트 달성 여부
	private static String ALREADY_ACHIVED_RANDOM_QUEST;// 이미 랜덤퀘 달성
	private static String ALREADY_USED_REFESH;// 이미 새로고침 사용
	private static String HOW_TO_REFRESH_RANDOM_QUEST;// 퀘스트 새로 고침을 사용하려면...
	private static String USE_RANDOM_QUEST_REFRESH;// 퀘스트를 새로 부여받습니다.

	private static String FIRST_LINK_QUEST;// 1) 연계퀘
	private static String SECOND_RANDOM_QUEST;// 2) 랜덤퀘
	private static String LINEEEEEEEEEE;// ================
	
	public static String RANDOM_QUEST_UPDATE_MSG;

	public static boolean initLoadMessage() {
		if (msg.isList("quest.RANDOM_QUEST")) {
			RANDOM_QUEST = msg.getStringList("quest.RANDOM_QUEST");
		} else {
			return false;
		}
		if (msg.isList("quest.LINK_QUEST")) {
			LINK_QUEST = msg.getStringList("quest.LINK_QUEST");
		} else {
			return false;
		}
		LINK_QUEST_COUNT = msg.getString("quest.LINK_QUEST_COUNT");
		DO_NOT_HAVE_LINK_QUEST = msg.getString("quest.DO_NOT_HAVE_LINK_QUEST");
		RANDOM_QUEST_COUNT = msg.getString("quest.RANDOM_QUEST_COUNT");
		DO_NOT_HAVE_RANDOM_QUEST = msg.getString("quest.DO_NOT_HAVE_RANDOM_QUEST");
		IS_ACHIVED_RANDOM_QUEST = msg.getString("quest.IS_ACHIVED_RANDOM_QUEST");
		ALREADY_ACHIVED_RANDOM_QUEST = msg.getString("quest.ALREADY_ACHIVED_RANDOM_QUEST");
		ALREADY_USED_REFESH = msg.getString("quest.ALREADY_USED_REFESH");
		HOW_TO_REFRESH_RANDOM_QUEST = msg.getString("quest.HOW_TO_REFRESH_RANDOM_QUEST");
		USE_RANDOM_QUEST_REFRESH = msg.getString("quest.USE_RANDOM_QUEST_REFRESH");
		FIRST_LINK_QUEST = msg.getString("quest.FIRST_LINK_QUEST");
		SECOND_RANDOM_QUEST = msg.getString("quest.SECOND_RANDOM_QUEST");
		LINEEEEEEEEEE = msg.getString("quest.LINEEEEEEEEEE");
		RANDOM_QUEST_UPDATE_MSG = msg.getString("qsadmin.RANDOM_QUEST_UPDATE_MSG");
		return true;
	}

	public static void saveDefaultMessage() {
		msg.set("quest.FIRST_LINK_QUEST", "§b1) §a연계 퀘스트");
		msg.set("quest.LINK_QUEST_COUNT", "§a퀘스트 통계: §7(<clearcount>/<updatecount>)");
		List<String> list = new LinkedList<String>();
		list.add("§6제목: §f<display>");
		list.add("§6내용: §f<description>");
		list.add("§6보상: §f<reward>");
		msg.set("quest.LINK_QUEST", list);
		msg.set("quest.DO_NOT_HAVE_LINK_QUEST", "§6가지고 있는 연계 퀘스트가 없습니다.");
		msg.set("quest.LINEEEEEEEEEE", "§7============================");
		
		msg.set("quest.ALREADY_USED_REFESH", "§6이미 새로고침을 사용했습니다.");
		msg.set("quest.USE_RANDOM_QUEST_REFRESH", "§6퀘스트를 새로 부여받습니다.");
		msg.set("quest.ALREADY_ACHIVED_RANDOM_QUEST", "§6이미 랜덤 퀘스트를 달성했습니다.");
		
		msg.set("quest.SECOND_RANDOM_QUEST", "§b2) §a랜덤 퀘스트");
		msg.set("quest.IS_ACHIVED_RANDOM_QUEST", "§a랜덤 퀘스트 달성 여부: <boolean>");
		msg.set("quest.RANDOM_QUEST_COUNT", "§a퀘스트 통계: §7(<clearcount>/<updatecount>)");
		msg.set("quest.RANDOM_QUEST", new LinkedList<String>(list));
		msg.set("quest.DO_NOT_HAVE_RANDOM_QUEST", "§6가지고 있는 랜덤 퀘스트가 없습니다.");
		msg.set("quest.HOW_TO_REFRESH_RANDOM_QUEST", "§7퀘스트 새로 고침을 사용하려면 §e/<cmd>");
		
		msg.set("qsadmin.RANDOM_QUEST_UPDATE_MSG", "§c[RsQuest] §6모든 유저의 랜덤 퀘스트를 새로 부여했습니다.");
		msg.saveYaml();
	}

	public static void runCommand(Player sender, String label, String[] args) {
		QsPlayer qp = QsPlayerAPI.getPlayer(sender.getName());

		QuestData qd = qp.getLinkQuestData();
		sender.sendMessage(FIRST_LINK_QUEST);
		sender.sendMessage(LINK_QUEST_COUNT.replace("<clearcount>", String.valueOf(qd.getquestClearCount())).replace("<updatecount>", String.valueOf(qd.getquestUpdateCount())));
		if (qd.hasQuest()) {
			Quest qs = qd.getQuest();
			sender.sendMessage(LINK_QUEST.get(0).replace("<display>", qs.getDisplay()));
			sender.sendMessage(LINK_QUEST.get(1).replace("<description>", qs.getDescription(qd.getEndCount())));
			sender.sendMessage(LINK_QUEST.get(2).replace("<reward>", qs.getReward().getDisplayMsg()));
		} else {
			sender.sendMessage(DO_NOT_HAVE_LINK_QUEST);
		}
		// 링크 퀘스트
		sender.sendMessage(LINEEEEEEEEEE);

		if (args.length == 1 && (args[0].equals("refresh") || args[0].equals("새로고침"))) {
			if (qp.isRefreshedRandomQuest()) {
				sender.sendMessage(ALREADY_USED_REFESH);
			} else if (!qp.isAchievedRandomQuest() && qp.getRandomQuestData().hasQuest()) {
				qp.setNewRandomQuest(true);
				sender.sendMessage(USE_RANDOM_QUEST_REFRESH);
			} else {
				sender.sendMessage(ALREADY_ACHIVED_RANDOM_QUEST);
			}
			return;
		}// 리프레시
		String bool = qp.isAchievedRandomQuest() ? "§btrue" : "§cfalse";
		qd = qp.getRandomQuestData();
		sender.sendMessage(SECOND_RANDOM_QUEST);
		sender.sendMessage(IS_ACHIVED_RANDOM_QUEST.replace("<boolean>", String.valueOf(bool)));
		sender.sendMessage(RANDOM_QUEST_COUNT.replace("<clearcount>", String.valueOf(qd.getquestClearCount())).replace("<updatecount>", String.valueOf(qd.getquestUpdateCount())));
		if (!qp.isAchievedRandomQuest() && qd.hasQuest()) {
			Quest qs = qd.getQuest();
			sender.sendMessage(RANDOM_QUEST.get(0).replace("<display>", qs.getDisplay()));
			sender.sendMessage(RANDOM_QUEST.get(1).replace("<description>", qs.getDescription(qd.getEndCount())));
			sender.sendMessage(RANDOM_QUEST.get(2).replace("<reward>", qs.getReward().getDisplayMsg()));
			if (qp.isRefreshedRandomQuest()) {
				sender.sendMessage(ALREADY_USED_REFESH);
			} else {
				String str = label.equals("quest") ? "quest refresh" : "퀘스트 새로고침";
				sender.sendMessage(HOW_TO_REFRESH_RANDOM_QUEST.replace("<cmd>", str));
			}
		} else if (qp.isAchievedRandomQuest()) {
			sender.sendMessage(ALREADY_ACHIVED_RANDOM_QUEST);
		} else {
			sender.sendMessage(DO_NOT_HAVE_RANDOM_QUEST);
		}

		// 랜덤 퀘스트

	}
}
