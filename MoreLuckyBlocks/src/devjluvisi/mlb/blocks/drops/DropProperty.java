package devjluvisi.mlb.blocks.drops;

import org.bukkit.inventory.ItemStack;

import devjluvisi.mlb.util.ConfigManager;

public interface DropProperty {
	
	/**
	 * @return The drop but converted into an item that can be displayed.
	 */
	ItemStack asItem();
	
	/**
	 * @return If the drop is a valid object in Minecraft that can be run.
	 */
	boolean isValid();

}
