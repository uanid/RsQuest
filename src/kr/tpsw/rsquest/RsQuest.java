package kr.tpsw.rsquest;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QsPlayerAPI;
import kr.tpsw.rsquest.api.QuestData;
import kr.tpsw.rsquest.command.CommandQsAdmin;
import kr.tpsw.rsquest.command.CommandQuest;
import kr.tpsw.rsquest.command.CommandRsQuest;
import kr.tpsw.rsquest.convert.ConvertNewerVersion;
import kr.tpsw.rsquest.convert.RsQuestMessage;
import kr.tpsw.rsquest.convert.TimeCheck;
import kr.tpsw.rsquest.quest.QsAPI;
import kr.tpsw.rsquest.quest.Quest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import rsquest.kr.tpsw.api.bukkit.PlayersAPI;
import rsquest.kr.tpsw.api.publica.YamlConfiguration;

public class RsQuest extends JavaPlugin {

	public final static String pluginName = "RsQuest";

	public static boolean isEpicBossHooked = false;// 나중에 수정하거라

	public static RsQuest plugin;
	public static String version;

	// 에픽보스 확인, 커맨드 등록, 이벤트 등록, 데이터 로드 후, 날짜 체크
	public void onEnable() {

		PlayersAPI.initLoad(this);

		plugin = this;
		version = plugin.getDescription().getVersion();// 버전 가져오기

		getCommand("quest").setExecutor(new CommandQuest());
		getCommand("qsadmin").setExecutor(new CommandQsAdmin());
		getCommand("rsquest").setExecutor(new CommandRsQuest());

		boolean enable = WordPressParsing.initRegister(this, getCommand("qsupdate"), this.getFile());
		if (!enable) {
			return;
		}
		// 커맨드 등록

		EventBase eventbase;
		Bukkit.getPluginManager().registerEvents(eventbase = new EventBase(), this);
		// 일반 이벤트
		Plugin pl = Bukkit.getPluginManager().getPlugin("EpicBossRecoded");
		isEpicBossHooked = pl != null;// 에픽보스 체크
		if (isEpicBossHooked) {
			Bukkit.getPluginManager().registerEvents(new Listener() {
				@EventHandler
				public void onBossDeath(me.ThaH3lper.com.Api.BossDeathEvent event) {
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
			}, this);
		}// 에픽보스 이벤트 등록
			// 이벤트 등록

		ConvertNewerVersion.updateDateFile();
		// 구버전 데이터 파일 신버전으로 컨버팅

		File f = new File("plugins\\RsQuest\\Users");
		if (!f.exists()) {
			f.mkdirs();
		}// 폴더 생성(아마도 의미없다)

		loadQuest();

		for (File sf : f.listFiles()) {
			QsPlayerAPI.loadPlayer(sf);
		}// 일반 데이터 로드

		for (Player player : PlayersAPI.getOnlinePlayers()) {
			eventbase.onJoin(player);
		}// 리로드 문제 해결

		RsQuestMessage.initLoadMessage();
		// 메세지 로드
		
		enable = TimeCheck.initTimeCheck(this);
		if (!enable) {
			System.out.println("[RsQuest] 퀘스트 플러그인을 강제로 종료합니다.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		// rstimer 연동

		System.out.println("[RsQuest] 플러그인 활성화 완료 v" + version);
	}

	public void onDisable() {

		for (QsPlayer p : QsPlayerAPI.getPlayers()) {
			QsPlayerAPI.savePlayer(p.getName());
		}// 유저 저장

		saveQuest();
		// 퀘스트 저장

		TimeCheck.shutDownTimeCheck();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void loadQuest() {
		YamlConfiguration quest = new YamlConfiguration("plugins\\" + pluginName + "\\quests.yml");
		for (String key : quest.getKeys(false)) {
			Map<String, Object> map = (Map<String, Object>) quest.getMap(key);
			Quest qs = QsAPI.getQuest(map);
			if (qs == null) {
				System.out.println(key + " 퀘스트에서 오류 발생");
				String localtime = new Date().toLocaleString().replace(':', '-');
				System.out.println("백업 데이터: " + localtime);
				quest.saveYaml(new File("plugins\\" + pluginName + "\\" + localtime + ".yml"));
				continue;
			}
			QsAPI.setQuest(qs.getName(), qs);
		}
	}

	private void saveQuest() {
		YamlConfiguration quest = new YamlConfiguration("plugins\\" + pluginName + "\\quests.yml");
		quest.clear();
		for (Quest qs : QsAPI.getQuests()) {
			Map<String, Object> map = qs.toMapForSave(new LinkedHashMap<String, Object>());
			quest.set((String) map.get("name"), map);
		}
		quest.saveYaml();
	}
}
