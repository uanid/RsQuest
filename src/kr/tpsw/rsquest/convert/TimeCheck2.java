package kr.tpsw.rsquest.convert;

import static kr.tpsw.rstimer.RsTimer.isTimerTable;
import static kr.tpsw.rstimer.RsTimer.removeTimerTable;
import static kr.tpsw.rstimer.RsTimer.setTimerTable;

import java.util.LinkedList;
import java.util.List;

import kr.tpsw.rsquest.RsQuest;
import kr.tpsw.rstimer.api.NewData;
import kr.tpsw.rstimer.api.TimerTable;

import org.bukkit.plugin.Plugin;

import rsquest.kr.tpsw.api.publica.YamlConfiguration;
import rstimer.kr.tpsw.api.executable.Executable;

public class TimeCheck2 {

	public static TimerTable tm = null;

	protected static void initRegister(Plugin pl) throws Exception {
		YamlConfiguration config = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\date.yml");
		List<String> dateList = config.getStringList("dateList");
		if (dateList.size() == 0) {
			dateList.add("-1 -1 -1 2 0");
			dateList.add("-1 -1 -1 12 0");
			dateList.add("-1 -1 -1 17 0");
			config.set("dateList", dateList);
		}

		String ldate = config.getString("ld");
		NewData ld;
		if (ldate == null) {
			ld = NewData.newInstance();
		} else {
			ld = NewData.newInstance(ldate);
		}

		if (isTimerTable("@RsQuest")) {
			removeTimerTable("@RsQuest");
		}

		tm = TimerTable.getInstance("@RsQuest");
		List<NewData> ndlist = tm.getNDateList();
		for (String str : dateList) {
			NewData nd = NewData.newInstance(str);
			ndlist.add(nd);
		}// newdate 추가
		Executable ex = Executable.getExecutable(new String[]{"type=command", "command='qsadmin questupdateall", "cmdtype=cmdcon", "message='랜덤 퀘스트를 새로 부여합니다.'"});
		//Executable ex = new ExCommand("qsadmin questupdateall", "cmdcon");
		tm.setExecutable(ex);
		tm.setLd(ld);
		tm.runTable(pl);
		setTimerTable(tm);
	}

	protected static void shutDownRegister() {
		if (tm != null) {
			tm.cancelTable();
			YamlConfiguration config = new YamlConfiguration("plugins\\" + RsQuest.pluginName + "\\date.yml");
			config.set("ld", tm.getLd().toString());
			List<String> list = new LinkedList<String>();
			for(NewData nd : tm.getNDateList()){
				list.add(nd.toString());
			}
			config.set("dateList", list);
			config.saveYaml();
		}
	}

}
