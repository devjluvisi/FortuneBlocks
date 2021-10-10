package devjluvisi.mlb.cmds;

import devjluvisi.mlb.MoreLuckyBlocks;
import devjluvisi.mlb.cmds.SubCommand.ExecutionResult;
import devjluvisi.mlb.cmds.admin.*;
import devjluvisi.mlb.cmds.admin.struct.ExitCommand;
import devjluvisi.mlb.cmds.admin.struct.SaveCommand;
import devjluvisi.mlb.cmds.admin.struct.StructureCommand;
import devjluvisi.mlb.cmds.general.*;
import devjluvisi.mlb.cmds.lb.DropsCommand;
import devjluvisi.mlb.cmds.lb.InfoCommand;
import devjluvisi.mlb.cmds.lb.ListCommand;
import devjluvisi.mlb.cmds.lb.RedeemCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

/**
 * Class which manages the base command within the plugin "/mlb" and then goes
 * through all of the individual "subcommands" which have their own seperate
 * classes.
 * <p>
 * Every command in the plugin routes through this class during the execution
 * cycle.
 *
 * @author jacob
 */
public class CommandManager implements CommandExecutor {

    private final LinkedList<SubCommand> subcommands;

    public CommandManager(MoreLuckyBlocks plugin) {
        this.subcommands = new LinkedList<>();
        this.subcommands.add(new VersionCommand(plugin));
        this.subcommands.add(new InfoCommand(plugin));
        this.subcommands.add(new ListCommand(plugin));
        this.subcommands.add(new RedeemCommand(plugin));
        this.subcommands.add(new DropsCommand(plugin));
        this.subcommands.add(new UsageCommand(this));
        this.subcommands.add(new PermissionsCommand(this));
        this.subcommands.add(new LuckCommand(plugin));
        this.subcommands.add(new BriefCommand());
        this.subcommands.add(new ConfigCommand(plugin));
        this.subcommands.add(new CreateCommand(plugin));
        this.subcommands.add(new StructureCommand(plugin));
        this.subcommands.add(new DisableCommand(plugin));
        this.subcommands.add(new EditCommand(plugin));
        this.subcommands.add(new GiveCommand(plugin));
        this.subcommands.add(new TransformCommand(plugin));
        this.subcommands.add(new ItemCommand());
        this.subcommands.add(new LuckSetCommand(plugin));
        this.subcommands.add(new PurgeCommand(plugin));
        this.subcommands.add(new ExitCommand(plugin));
        this.subcommands.add(new SaveCommand(plugin));
        this.subcommands.add(new ResetCommand(plugin));
        this.subcommands.add(new SettingsCommand(plugin));
        this.subcommands.add(new TestCommand(plugin));
        this.subcommands.add(new HelpCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "[MoreLuckyBlocks] " + ChatColor.RED
                    + "Unknown Command.\nType /mlb help to view a list of commands.");
            return true;
        }

        // Go through all of the sub commands and check if the argument matches.
        for (final SubCommand sub : this.subcommands) {
            if (args[0].equalsIgnoreCase(sub.getName())) {

                if (!(sender instanceof Player) && !sub.isAllowConsole()) {
                    sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
                    return true;
                }

                if ((!sub.getPermission().isBlank()) && !sender.hasPermission(sub.getPermission())) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
                    return true;
                }

                // If the arguments provided in the command match the exact range of the
                // subcommand.
                if (!sub.getArgumentRange().isInRange(args.length)) {
                    sender.sendMessage(ChatColor.RED + "Incorrect Usage.");
                    sender.sendMessage(ChatColor.RED + sub.getSyntax());
                    return true;
                }
                // Get the result that comes out after the subcommand is performed.
                final ExecutionResult result = sub.perform(sender, args);

                switch (result) {
                    case PASSED:
                        break;
                    case BAD_ARGUMENT_TYPE:
                        sender.sendMessage(ChatColor.RED + "Arguments not in correct format.");
                        break;
                    case INVALID_PLAYER:
                        sender.sendMessage(ChatColor.RED + "Player could not be found.");
                        break;
                    case BAD_USAGE:
                        sender.sendMessage(ChatColor.RED + "Incorrect Usage.");
                        sender.sendMessage(ChatColor.RED + sub.getSyntax());
                        break;
                }
                return true;
            }
        }

        // This executes if the user attempted to enter a subcommand using /mlb but the
        // subcommand does not exist.
        sender.sendMessage(ChatColor.RED + "Unknown Command.\nmlb help for a list of commands.");
        return true;
    }

    public LinkedList<SubCommand> getSubcommands() {
        return this.subcommands;
    }

}
