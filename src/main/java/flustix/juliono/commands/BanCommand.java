package flustix.juliono.commands;

import flustix.juliono.command.Command;
import flustix.juliono.utils.MessageUtils;
import flustix.juliono.utils.UserUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BanCommand extends Command {
    public BanCommand() {
        name = "ban";
        desc = "Ban a User.";
        longDesc = "Bans a User from the Server.";
        permissions.add(Permission.BAN_MEMBERS);
    }

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        if (args.length == 0) {
            msg.getChannel().sendMessage("You need to specify a User to ban.").queue();
            return;
        }

        User toBan = UserUtils.getUser(args[0]);

        String reason = "";
        if (args.length > 1) {
            for (String arg : args) {
                if (arg.equals(args[0])) {
                    continue;
                }
                reason += arg + " ";
            }
        }

        msg.getGuild().ban(toBan, 7, reason).complete();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Banned " + toBan.getName() + " from the Server.")
                .setColor(0xFF5555);

        if (!reason.isEmpty()) {
            embed.setDescription("With Reason: " + reason);
        }

        MessageUtils.reply(msg, embed);
    }
}
