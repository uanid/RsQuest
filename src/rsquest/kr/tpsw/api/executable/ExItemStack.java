package rsquest.kr.tpsw.api.executable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsquest.kr.tpsw.api.bukkit.API;
import rsquest.kr.tpsw.api.publica.ObjectAPI;

public class ExItemStack extends Executable {

	private transient ItemStack is = null;
	private transient String display = null;

	// 아이템 개수: value
	// 아이템 코드: arg0
	// 아이템 제목: arg1
	// 아이템 lore: arg2

	public ExItemStack() {
		is = new ItemStack(Material.STONE);
		display = "ExItemStack +null";
	}

	public void updateVariables() {
		if (args == null) {
			is = new ItemStack(Material.STONE);
			display = "ExItemStack +null";
		} else {
			int[] ii = API.getItemCode(args.get(0));
			Material m;
			if (ObjectAPI.isArrayHasIndex(Material.values(), ii[0])) {
				m = Material.matchMaterial(String.valueOf(ii[0]));
				//m = Material.getMaterial(ii[0]);
			} else {
				is.setAmount(0);
				display = "ExItemStack +{Name=null ItemStak}";
				return;
			}
			is = new ItemStack(m);
			is.setAmount(value);
			is.setDurability((short) ii[1]);
			display = "ExItemStack +{Name=" + m.name() + ":" + ii[1] + ", Amout=" + value + "}";

			ItemMeta im = is.getItemMeta();
			if (args.size() == 1) {
				return;
			} else if (args.size() == 2) {
				im.setDisplayName(args.get(1));
			} else if (args.size() >= 3) {
				List<String> list = new LinkedList<String>();
				for (int i = 2; i < args.size(); i++) {
					list.add(args.get(i));
				}
				im.setLore(list);
			}
			is.setItemMeta(im);

		}
	}

	@Override
	protected void abstractRun(Player player) throws Exception {
		int firstEmpty = player.getInventory().firstEmpty();
		if (firstEmpty == -1) {
			player.getWorld().dropItem(player.getLocation(), is);
		} else {
			player.getInventory().addItem(is);
		}
	}

	@Override
	public String getDisplayMsg() {
		if (message == null) {
			return display;
		} else {
			return message;
		}
	}

	@Override
	public Map<String, Object> toMapForSave(Map<String, Object> map) {
		map.put("type", "itemstack");
		map.put("value", value);
		map.put("message", message);
		map.put("args", args);
		map.put("allplayer", isAllPlayer);
		return map;
	}

	public String toString() {
		if (args == null) {
			return "Class=ExItemStack, Value=" + value + ", args=null, message=" + message + ", allplayer" + isAllPlayer;
		}
		return "Class=ExItemStack, Value=" + value + ", args=" + args.toString() + ", message=" + message + ", allplayer" + isAllPlayer;
	}
}
