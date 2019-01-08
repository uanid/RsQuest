package kr.tpsw.rsquest.command;

import kr.tpsw.rsquest.RsQuest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rsquest.kr.tpsw.api.executable.Executable;

public class CommandRsQuest implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("§6플러그인 이름:§c RsQuest");
		sender.sendMessage("§6버전:§c " + RsQuest.version);
		sender.sendMessage("§6제작자:§c TPsw");
		sender.sendMessage("§6에픽보스 연동:§c " + RsQuest.isEpicBossHooked);
		
		for (String line : Executable.getInfo()) {
			sender.sendMessage(line);
		}

		// 정보 보여주는 명령어
		return true;
	}

}
