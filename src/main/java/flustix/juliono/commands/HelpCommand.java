package flustix.juliono.commands;

import flustix.juliono.Main;
import flustix.juliono.command.Command;
import flustix.juliono.command.CommandList;
import flustix.juliono.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;

public class HelpCommand extends Command {
    public HelpCommand() {
        name = "help";
        desc = "Shows this message";
        longDesc = "Shows this message";
    }

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        if (args.length > 0) {
            if (CommandList.getCommands().containsKey(args[0])) {
                Command cmd = CommandList.getCommands().get(args[0]);
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(cmd.name)
                        .setColor(Main.accentColor)
                        .setDescription(cmd.longDesc);

                if (cmd.permissions.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Permission permission : cmd.permissions) {
                        sb.append(permission.getName()).append("\n");
                    }
                    embed.addField("Needed Permissions", sb.toString(), false);
                }

                MessageUtils.reply(msg, embed);
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(0xFF5555)
                        .setTitle("Not Found")
                        .setDescription("The command " + args[0] + " was not found!");

                MessageUtils.reply(msg, embed);
            }

            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Main.accentColor)
                .setTitle("Help")
                .setDescription("This is the help command.\n" + "Use `!help <command>` to get more information about a command.");

        for (Map.Entry<String, Command> command : CommandList.getCommands().entrySet()) {
            if (!command.getValue().hidden)
                embed.addField(command.getKey(), command.getValue().desc, true);
        }

        MessageUtils.reply(msg, embed);
    }
}
