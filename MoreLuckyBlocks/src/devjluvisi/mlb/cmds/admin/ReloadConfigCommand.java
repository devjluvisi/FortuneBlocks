package devjluvisi.mlb.cmds.admin;

import org.bukkit.command.CommandSender;

import devjluvisi.mlb.MoreLuckyBlocks;
import devjluvisi.mlb.util.Range;
import devjluvisi.mlb.util.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class ReloadConfigCommand implements SubCommand {
	
	private MoreLuckyBlocks plugin;
	public ReloadConfigCommand(MoreLuckyBlocks plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "reload";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Reload the plugin config.";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/mlb reload";
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "mlb.admin.reload";
	}

	@Override
	public boolean isAllowConsole() {
		return true;
	}

	@Override
	public Range getArgumentRange() {
		return new Range(1, 1);
	}

	@Override
	public ExecutionResult perform(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.BLUE + "Configuration files have been saved and reloaded.");
		plugin.getBlocksYaml().save();
		plugin.getBlocksYaml().reload();
		
		plugin.getConfigYaml().save();
		plugin.getConfigYaml().reload();
		
		plugin.getMessagesYaml().save();
		plugin.getMessagesYaml().reload();
		return ExecutionResult.PASSED;
	}

}