package devjluvisi.mlb.blocks.drops;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LuckyBlockItem implements LootProperty {

	private ItemStack item;

	public LuckyBlockItem(ItemStack item) {
		super();
		this.item = item.clone();
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item.clone();
	}

	public String enchantsConfigString() {
		if (item.getEnchantments().size() == 0) {
			return "";
		}
		String str = "[";
		for (Enchantment e : item.getEnchantments().keySet()) {
			str += e.getKey().getKey().toString().toUpperCase() + ":" + item.getEnchantments().get(e) + ",";
		}
		// Remove trailing comma.
		str = str.substring(0, str.length() - 1);
		str += "]";
		return str;
	}

	@Override
	public String toString() {
		return "LuckyBlockItem [item=" + item + "]";
	}

	@Override
	public ItemStack asItem() {
		return item;
	}

	@Override
	public boolean isValid() {
		if ((item == null) || (item.getAmount() < 1) || (item.getType() == null))
			return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		return item.equals(((LuckyBlockItem) obj).item);
	}

}
