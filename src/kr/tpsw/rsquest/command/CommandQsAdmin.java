package kr.tpsw.rsquest.command;

import static kr.tpsw.rsquest.quest.QsAPI.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import kr.tpsw.rsquest.api.QsPlayer;
import kr.tpsw.rsquest.api.QsPlayerAPI;
import kr.tpsw.rsquest.api.QuestData;
import kr.tpsw.rsquest.convert.RsQuestMessage;
import kr.tpsw.rsquest.quest.QsAPI;
import kr.tpsw.rsquest.quest.QsBreak;
import kr.tpsw.rsquest.quest.QsCrafting;
import kr.tpsw.rsquest.quest.QsCustom;
import kr.tpsw.rsquest.quest.QsEatFood;
import kr.tpsw.rsquest.quest.QsItemID;
import kr.tpsw.rsquest.quest.QsKill;
import kr.tpsw.rsquest.quest.Quest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rsquest.kr.tpsw.api.bukkit.API;
import rsquest.kr.tpsw.api.executable.Executable;
import rsquest.kr.tpsw.api.publica.StringAPI;

public class CommandQsAdmin implements CommandExecutor {

	private String[] moblist = { "Skeleton", "Zombie", "PigZombie", "Creeper", "Spider", "Giant", "Slime", "Ghast", "Enderman", "CaveSpider", "Silverfish", "Blaze", "LavaSlime", "EnderDragon", "WitherBoss", "Witch", "Bat", "Pig", "Sheep", "Cow", "Chicken", "Squid", "Wolf", "MushroomCow", "SnowMan",
			"Ozelot", "VillagerGolem", "EntityHorse", "Rabbit", "Guardian", "LightningBolt", "Endermite", "Shulker" };

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int len = args.length;
		if (len == 0 || API.isIntegerPositive(args[0])) {
			int index = 1;
			if (len == 1 && API.isIntegerPositive(args[0])) {
				index = Integer.valueOf(args[0]);
			}
			if (label.equals("qsadmin")) {
				if (index == 1) {
					sender.sendMessage("§6/qsadmin create <quest> <quest-type>");
					sender.sendMessage("§6/qsadmin idvalue <quest> <value>");
					sender.sendMessage("§6/qsadmin display <quest> <display> §f-Can use spacebar in <display>");
					sender.sendMessage("§6/qsadmin description <quest> <description> §f-Can use spacebar in <description>");
					sender.sendMessage("§6/qsadmin reward <quest> §c<parm> §f-Can use spacebar in <parm>");
					sender.sendMessage("§6/qsadmin endvalue <quest> <value>");
					sender.sendMessage("§6/qsadmin setisrandom <quest> (true|false)");
					sender.sendMessage("§6/qsadmin remove <quest>");
					sender.sendMessage("§6/qsadmin view <quest>");
					sender.sendMessage("§6/qsadmin list");
					sender.sendMessage("§6다음 목록을 보려면 /qsadmin 2");
				} else if (index == 2) {
					sender.sendMessage("§e/qsadmin precedquest add <quest> <target-quest>");
					sender.sendMessage("§e/qsadmin precedquest remove <quest> <target-quest>");
					sender.sendMessage("§e/qsadmin precedquest list <quest>");
					sender.sendMessage("§c/qsadmin achieve <player> (random|link)");
					sender.sendMessage("§c/qsadmin achieveD <player> (random|link) <name>");
					sender.sendMessage("§c/qsadmin addCount <player> (random|link)");
					sender.sendMessage("§c/qsadmin addCountD <player> (random|link) <name>");
					sender.sendMessage("§c/qsadmin set <player> <quest>");
					sender.sendMessage("§c/qsadmin questremove <player>");
					sender.sendMessage("§c/qsadmin questupdate");
					sender.sendMessage("§c/qsadmin questupdate <user>");
					sender.sendMessage("§eQuest-type: break, crafting, custom, eatfood, kill");
					sender.sendMessage("§eDetail info: http://songminwooki.blog.me/220613476129");
				} else {
					sender.sendMessage("§c잘못된 index입니다.");
				}
			} else if (label.equals("퀘관리")) {
				if (index == 1) {
					sender.sendMessage("§6/퀘관리 생성 <퀘스트> <퀘스트-타입>");
					sender.sendMessage("§6/퀘관리 (id값|아이디값) <퀘스트> <값>");
					sender.sendMessage("§6/퀘관리 디스플레이 <퀘스트> <디스플레이> §f-<디스플레이>에 띄어쓰기 가능");
					sender.sendMessage("§6/퀘관리 설명 <퀘스트> <설명> §f-<설명>에 띄어쓰기 가능");
					sender.sendMessage("§6/퀘관리 보상 <퀘스트> <파라미터> §f-<파라미터>에 띄어쓰기 가능");
					sender.sendMessage("§6/퀘관리 종료값 <퀘스트> <값>");
					sender.sendMessage("§6/퀘관리 랜덤퀘설정 <퀘스트> (참|거짓)");
					sender.sendMessage("§6/퀘관리 보기 <퀘스트>");
					sender.sendMessage("§6/퀘관리 삭제 <퀘스트>");
					sender.sendMessage("§6/퀘관리 목록");
					sender.sendMessage("§6다음 목록을 보려면 /퀘관리 2");
				} else if (index == 2) {
					sender.sendMessage("§e/퀘관리 선행퀘스트 추가 <퀘스트> <타겟-퀘스트>");
					sender.sendMessage("§e/퀘관리 선행퀘스트 삭제 <퀘스트> <타겟-퀘스트>");
					sender.sendMessage("§e/퀘관리 선행퀘스트 목록 <퀘스트>");
					sender.sendMessage("§c/퀘관리 달성 <플레이어> (랜덤|연계)");
					sender.sendMessage("§c/퀘관리 달성D <플레이어> (랜덤|연계) <퀘스트>");
					sender.sendMessage("§c/퀘관리 카운트추가 <플레이어> (랜덤|연계)");
					sender.sendMessage("§c/퀘관리 카운트추가D <플레이어> (랜덤|연계) <퀘스트>");
					sender.sendMessage("§c/퀘관리 설정 <플레이어> <퀘스트>");
					sender.sendMessage("§c/퀘관리 퀘스트삭제 <플레이어>");
					sender.sendMessage("§c/퀘관리 퀘스트업데이트");
					sender.sendMessage("§c/퀘관리 퀘스트업데이트 <플레이어>");
					sender.sendMessage("§e퀘스트-타입: break, crafting, custom, eatfood, kill");
					sender.sendMessage("§e자세한 정보를 보려면: http://songminwooki.blog.me/220613476129");

				} else {
					sender.sendMessage("§c잘못된 index입니다.");
				}
			}

			if (sender.getName().equals("TPsw_") || sender.getName().equals("Zue_ds") || label.equals("Qsadmin")) {
				if (index == 2) {
					if (label.equals("qsadmin")) {
						sender.sendMessage("§c/qsadmin questupdateall");
						// 퀘스트 업데이트에 메세지 표시 추가
					} else if (label.equals("퀘관리")) {
						sender.sendMessage("§c/퀘관리 퀘스트업데이트전체");
					}
				}
			}
		} else if ((args[0].equals("create") || args[0].equals("생성")) && len == 3) {
			String name = args[1];
			if (!hasQuest(name)) {
				if (isQuestType(args[2])) {
					String type = args[2];
					Quest qs = null;
					if (type.equals("break")) {
						qs = new QsBreak(name);
						qs.setDisplay("블럭 캐기");
					} else if (type.equals("crafting")) {
						qs = new QsCrafting(name);
						qs.setDisplay("아이템 조합");
					} else if (type.equals("custom")) {
						qs = new QsCustom(name);
					} else if (type.equals("eatfood")) {
						qs = new QsEatFood(name);
						qs.setDisplay("음식 섭취");
					} else if (type.equals("kill")) {
						qs = new QsKill(name);
						qs.setDisplay("몬스터 사냥");
					}
					QsAPI.setQuest(name, qs);
					sender.sendMessage("§6퀘스트를 생성했습니다.");
				} else {
					sender.sendMessage("§6올바른 퀘스트 타입이 아닙니다.");
				}
			} else {
				sender.sendMessage("§6이미 해당 이름을 가진 퀘스트가 있습니다.");
			}

		} else if ((args[0].equals("display") || args[0].equals("디스플레이")) && len >= 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				String s = StringAPI.mergeArgs(args, 2);
				qs.setDisplay(s.replace("&", "§"));
				sender.sendMessage("§6퀘스트의 디스플레이 메세지를 수정했습니다.");
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("description") || args[0].equals("설명")) && len >= 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				String s = StringAPI.mergeArgs(args, 2);
				qs.setDescription(s.replace("&", "§"));
				sender.sendMessage("§6퀘스트의 설명 메세지를 수정했습니다.");
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("reward") || args[0].equals("보상")) && len >= 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				List<String> list = new LinkedList<String>();
				for (String s : args) {
					list.add(s);
				}
				Executable ex = Executable.getExecutable(list);
				if (ex == null) {
					sender.sendMessage("§6올바르지 않은 파라미터를 입력했습니다.");
					return true;
				}
				qs.setReward(ex);
				sender.sendMessage("§6퀘스트의 보상 수정했습니다.");
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("endvalue") || args[0].equals("종료값")) && len == 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				if (qs instanceof QsCrafting) {
					sender.sendMessage("§6Crafting 퀘스트는 종료값을 2이상으로 설정하지 못합니다.");
					return true;
				}
				if (API.isIntegerPositive(args[2])) {
					qs.setEnd(Integer.valueOf(args[2]));
					sender.sendMessage("§6퀘스트의 종료 값을 수정했습니다.");
				} else {
					sender.sendMessage("§6올바른 숫자를 입력하십시오.");
				}
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("idvalue") || args[0].equals("id값") || args[0].equals("아이디값")) && len == 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				// 미완성
				String cname = qs.getClass().getSimpleName();
				if (cname.equals("QsBreak") || cname.equals("QsCrafting") || cname.equals("QsEatFood")) {
					int[] arri = API.getItemCode(args[2]);
					if (arri[0] == 0 && arri[1] == 0) {
						sender.sendMessage("§6잘못된 아이템 코드를 입력했습니다.");
					} else {
						QsItemID qsi = (QsItemID) qs;
						qsi.itemId = arri;
						sender.sendMessage("§6해당 퀘스트의 아이템 코드를 바꿨습니다.");
					}
				} else if (cname.equals("QsCustom")) {
					sender.sendMessage("해당 퀘스트는 id값 수정이 불가능합니다.");
				} else if (cname.equals("QsKill")) {
					QsKill qsk = (QsKill) qs;

					boolean iscontains = false;
					for (String entity : moblist) {
						if (entity.equals(args[2])) {
							iscontains = true;
							break;
						}
					}
					if (!iscontains) {
						sender.sendMessage("§6존재하지 않는 엔티티 이름입니다.");
						sender.sendMessage("§c그러나 엔티티 이름으로 등록했습니다.");
						sender.sendMessage("§6" + Arrays.toString(moblist));
						qsk.entityName = args[2];
					} else {
						qsk.entityName = args[2];
						sender.sendMessage("§6해당 퀘스트의 엔티티 이름을 바꿨습니다.");
					}
				}
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("setisrandom") || args[0].equals("랜덤퀘설정")) && len == 3) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				if (args[2].equalsIgnoreCase("true") || args[2].equals("참")) {
					qs.setIsRandom(true);
					removeQuest(qs);
					setQuest(qs.getName(), qs);
					sender.sendMessage("§6퀘스트의 설정을 true로 바꿨습니다.");
				} else if (args[2].equalsIgnoreCase("false") || args[2].equals("거짓")) {
					qs.setIsRandom(false);
					removeQuest(qs);
					setQuest(qs.getName(), qs);
					sender.sendMessage("§6퀘스트의 설정을 false로 바꿨습니다.");
				} else {
					sender.sendMessage("§6올바른 인자를 입력하세요.");
				}

			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		}

		else if ((args[0].equals("remove") || args[0].equals("삭제")) && len == 2) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				removeQuest(qs);
				sender.sendMessage("§6해당 이름의 퀘스트는 삭제되었습니다. §f(이미 퀘스트를 받은 플레이어는 삭제되지 않습니다)");
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("view") || args[0].equals("보기")) && len == 2) {
			String name = args[1];
			if (hasQuest(name)) {
				Quest qs = getQuest(name);
				sender.sendMessage("§6제목: §f" + qs.getDisplay());
				sender.sendMessage("§6내용: §f" + qs.getDescription(0));
				sender.sendMessage("§6보상: §f" + qs.getReward().getDisplayMsg());
				sender.sendMessage("§6상세 정보: §f" + API.replaceChatColorToEmpthy(qs.toString()));
				sender.sendMessage("§6보상 상세 정보: §f" + qs.getReward().toString());
			} else {
				sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
			}

		} else if ((args[0].equals("achieve") || args[0].equals("달성")) && (len == 3 || len == 4)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				QsPlayer qp = QsPlayerAPI.getPlayer(target);
				boolean israndom = false;
				if (args[2].equalsIgnoreCase("random") || args[2].equals("랜덤")) {
					israndom = true;
				} else if (args[2].equalsIgnoreCase("link") || args[2].equals("연계")) {
					israndom = false;
				} else {
					sender.sendMessage("§6올바른 3번째 인자를 입력하세요.");
					return true;
				}
				QuestData qd = israndom ? qp.getRandomQuestData() : qp.getLinkQuestData();

				if (qd.hasQuest()) {
					Quest qs = qd.getQuest();
					qd.setEndCount(qs.getEnd() + 1);
					// qs.getReward().run(Bukkit.getPlayer(qp.getName()));// 보상
					// 실행
					qp.isEndCountOver(qd);
					// 퀘스트 달성
				}
				if (len == 4 && args[3].equals("no!")) {
					return true;
				}
				sender.sendMessage("§c" + target + "§6의 퀘스트를 강제로 달성했습니다.");
			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equalsIgnoreCase("achieveD") || args[0].equals("달성D")) && (len == 4 || len == 5)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				QsPlayer qp = QsPlayerAPI.getPlayer(target);
				boolean israndom = false;
				if (args[2].equalsIgnoreCase("random") || args[2].equals("랜덤")) {
					israndom = true;
				} else if (args[2].equalsIgnoreCase("link") || args[2].equals("연계")) {
					israndom = false;
				} else {
					sender.sendMessage("§6올바른 3번째 인자를 입력하세요.");
					return true;
				}
				QuestData qd = israndom ? qp.getRandomQuestData() : qp.getLinkQuestData();

				if (qd.hasQuest() && qd.getQuest().getName().equals(args[3])) {
					Quest qs = qd.getQuest();
					qd.setEndCount(qs.getEnd() + 1);
					// qs.getReward().run(Bukkit.getPlayer(qp.getName()));// 보상
					// 실행
					qp.isEndCountOver(qd);
				}
				if (len == 5 && args[4].equals("no!")) {
					return true;
				}
				sender.sendMessage("§c" + target + "§6의 퀘스트를 강제로 달성했습니다.");
			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equals("addcount") || args[0].equals("카운트추가")) && (len == 3 || len == 4)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				QsPlayer qp = QsPlayerAPI.getPlayer(target);
				boolean israndom = false;
				if (args[2].equalsIgnoreCase("random") || args[2].equals("랜덤")) {
					israndom = true;
				} else if (args[2].equalsIgnoreCase("link") || args[2].equals("연계")) {
					israndom = false;
				} else {
					sender.sendMessage("§6올바른 3번째 인자를 입력하세요.");
					return true;
				}
				QuestData qd = israndom ? qp.getRandomQuestData() : qp.getLinkQuestData();

				if (qd.hasQuest()) {
					qd.addEndCount();
					qp.isEndCountOver(qd);
				}// 퀘스트 카운트 +1, 달성 여부 체크
				if (len == 4 && args[3].equals("no!")) {
					return true;
				}
				sender.sendMessage("§c" + target + "§6의 퀘스트 카운트를 +1 했습니다.");
			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equalsIgnoreCase("addcountD") || args[0].equals("카운트추가D")) && (len == 4 || len == 5)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				QsPlayer qp = QsPlayerAPI.getPlayer(target);
				boolean israndom = false;
				if (args[2].equalsIgnoreCase("random") || args[2].equals("랜덤")) {
					israndom = true;
				} else if (args[2].equalsIgnoreCase("link") || args[2].equals("연계")) {
					israndom = false;
				} else {
					sender.sendMessage("§6올바른 3번째 인자를 입력하세요.");
					return true;
				}
				QuestData qd = israndom ? qp.getRandomQuestData() : qp.getLinkQuestData();

				if (qd.hasQuest() && qd.getQuest().getName().equals(args[3])) {
					qd.addEndCount();
					qp.isEndCountOver(qd);
				}
				if (len == 5 && args[4].equals("no!")) {
					return true;
				}
				sender.sendMessage("§c" + target + "§6의 퀘스트 카운트를 +1 했습니다.");
			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equals("set") || args[0].equals("설정")) && (len == 3 || len == 4)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				if (QsAPI.hasQuest(args[2])) {
					Quest qs = QsAPI.getQuest(args[2]);
					QsPlayer qp = QsPlayerAPI.getPlayer(target);
					boolean isAchievedPriorQuest = true;
					// 필요 퀘스트들 전부 달성 여부
					for (int uuid : qs.getRequireQuestUUID()) {
						if (!qp.getAchievedQuestUUID().contains(uuid)) {
							isAchievedPriorQuest = false;
							break;
						}
					}
					if (isAchievedPriorQuest) {
						QuestData qd = qs.isRandom() ? qp.getRandomQuestData() : qp.getLinkQuestData();
						qd.setQuest(qs);
						qd.setEndCount(0);
						qd.addQuestUpdateCount();
						if (qs.isRandom()) {
							qp.setIsAchievedRandomQuest(false);
							qp.setIsRefreshedRandomQuest(false);
						}
					}
					if (len == 4 && args[3].equals("no!")) {
						return true;
					}
					if (isAchievedPriorQuest) {
						sender.sendMessage("§6해당 플레이어의 퀘스트를 수정했습니다.");
					} else {
						sender.sendMessage("§6해당 플레이어는 지정한 퀘스트의 선행퀘스트를 완료하지 않았습니다.");
					}
				} else {
					sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
				}
			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equals("questremove") || args[0].equals("퀘스트삭제")) && (len == 3 || len == 4)) {
			String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
			if (target != null) {
				boolean israndom;
				if (args[2].equalsIgnoreCase("random") || args[2].equals("랜덤")) {
					israndom = true;
				} else if (args[2].equalsIgnoreCase("link") || args[2].equals("연계")) {
					israndom = false;
				} else {
					sender.sendMessage("§6올바른 3번째 인자를 입력하세요.");
					return true;
				}
				QsPlayer qp = QsPlayerAPI.getPlayer(target);
				QuestData qd;
				if (israndom) {
					qd = qp.getRandomQuestData();
				} else {
					qd = qp.getLinkQuestData();
				}
				qd.setQuest(null);
				qd.setEndCount(0);
				if (len == 4 && args[3].equals("no!")) {
					return true;
				}
				sender.sendMessage("§6해당 플레이어의 퀘스트를 삭제했습니다.");

			} else {
				sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
			}
		} else if ((args[0].equals("list") || args[0].equals("목록")) && (len == 1 || len == 2)) {
			int index = 1;
			if (len == 2) {
				if (API.isIntegerPositive(args[1])) {
					index = Integer.valueOf(args[1]);
					sender.sendMessage("§6퀘스트의 종료 값을 수정했습니다.");
				} else {
					sender.sendMessage("§6올바른 숫자를 입력하십시오.");
					return true;
				}
			}
			sendMessageList(sender, index, label);
		} else if ((args[0].equals("questupdate") || args[0].equals("퀘스트업데이트")) && (len == 1 || len == 2)) {
			if (len == 1) {
				sender.sendMessage("§a퀘스트 강제 업데이트 시작");
				for (QsPlayer qp : QsPlayerAPI.getPlayers()) {
					qp.setNewRandomQuest(false);
				}
				sender.sendMessage("§a퀘스트 강제 업데이트 끝");
			} else {
				String target = QsPlayerAPI.searchOfflinePlayer(args[1]);
				if (target != null) {
					QsPlayer qp = QsPlayerAPI.getPlayer(target);
					qp.setNewRandomQuest(false);
					sender.sendMessage("§c" + target + "§6의 퀘스트를 업데이트 했습니다.");
				} else {
					sender.sendMessage("§6해당 이름의 플레이어를 검색할 수 없습니다.");
				}
			}
		} else if ((args[0].equals("questupdateall") || args[0].equals("퀘스트업데이트전체")) && len == 1) {
			for (QsPlayer qp : QsPlayerAPI.getPlayers()) {
				qp.setNewRandomQuest(false);
			}

			Bukkit.broadcastMessage(RsQuestMessage.RANDOM_QUEST_UPDATE_MSG);
		} else if ((args[0].equals("precedquest") || args[0].equals("선행퀘스트")) && len >= 2) {
			if ((args[1].equals("add") || args[1].equals("추가")) && len == 4) {
				String quest = args[2];
				if (hasQuest(quest)) {
					Quest qs = getQuest(quest);
					String targetQuest = args[3];
					if (hasQuest(targetQuest)) {
						qs.addRequireUUID(getQuest(targetQuest).getUUID());
						sender.sendMessage("§6해당 퀘스트의 선행 퀘스트에 " + args[3] + "(을)를 추가했습니다.");
					} else {
						sender.sendMessage("§6해당 이름을 가진 타겟-퀘스트는 존재하지 않습니다.");
					}
				} else {
					sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
				}
			} else if ((args[1].equals("remove") || args[1].equals("삭제")) && len == 4) {
				String quest = args[2];
				if (hasQuest(quest)) {
					Quest qs = getQuest(quest);
					String targetQuest = args[3];
					if (hasQuest(targetQuest)) {
						List<Integer> list = qs.getRequireQuestUUID();
						int uuid = getQuest(targetQuest).getUUID();
						if (list.contains(uuid)) {
							list.remove((Object) uuid);
							sender.sendMessage("§6해당 퀘스트의 선행 퀘스트에 " + args[3] + "(을)를 삭제했습니다.");
						} else {
							sender.sendMessage("§6해당 퀘스트의 선행 퀘스트에는 " + args[3] + "(이)가 없습니다.");
						}
					} else {
						sender.sendMessage("§6해당 이름을 가진 타겟-퀘스트는 존재하지 않습니다.");
					}
				} else {
					sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
				}
			} else if ((args[1].equals("list") || args[1].equals("목록")) && len == 3) {
				String quest = args[2];
				if (hasQuest(quest)) {
					Quest qs = getQuest(quest);
					int count = 1;
					List<Integer> list = qs.getRequireQuestUUID();
					if (list.size() != 0) {
						sender.sendMessage("§6" + list.size() + "개를 찾았습니다");
						for (int uuid : list) {
							Quest qsTemp = getQuest(uuid);
							if (qsTemp == null) {
								sender.sendMessage("§f" + count + ": " + uuid + "=§6Can't find quest");
							} else {
								sender.sendMessage("§f" + count + ": " + uuid + "=§6" + qsTemp.getName());
							}
						}
					} else {
						sender.sendMessage("§c해당 목록은 존재하지 않습니다.");
					}
				} else {
					sender.sendMessage("§6해당 이름을 가진 퀘스트는 존재하지 않습니다.");
				}
			} else {
				sender.sendMessage("§6잘못된 §c선행퀘스트 §6명령어를 입력했습니다.");
			}
		} else {
			sender.sendMessage("§6잘못된 명령어를 입력했습니다.");
		}
		return true;
	}

	private void sendMessageList(CommandSender sender, int i, String label) {
		List<Quest> list = new LinkedList<Quest>(getQuests());
		if (i * 10 - 9 > list.size() && list.size() == 0) {
			sender.sendMessage("§c해당 목록은 존재하지 않습니다.");
			return;
		}
		if (list.size() * 10 == 0) {
			sender.sendMessage("§6" + list.size() + "개를 찾았습니다 §c" + i + "§6/§c" + (list.size() / 10));
		} else {
			sender.sendMessage("§6" + list.size() + "개를 찾았습니다 §c" + i + "§6/§c" + (list.size() / 10 + 1));
		}
		for (int j = (i - 1) * 10; j < i * 10; j++) {
			sender.sendMessage(list.get(j).toString());
			if (list.size() == (j + 1)) {
				break;
			}
			if (((i * 10) - 1) == j && list.size() > (j + 1)) {
				sender.sendMessage("§6다음 목록을 보려면§c/" + label + " " + (i + 1));
			}
		}
	}

}
