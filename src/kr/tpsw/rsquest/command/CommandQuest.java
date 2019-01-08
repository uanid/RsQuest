package kr.tpsw.rsquest.command;

import kr.tpsw.rsquest.convert.RsQuestMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQuest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			RsQuestMessage.runCommand((Player) sender, label, args);
		} else {
			sender.sendMessage("이 명령어는 오직 플레이어만 사용이 가능합니다.");
		}
		return true;
	}// 아무거나 입력시 무조건 현재 소지중인 퀘스트 띄움
}
