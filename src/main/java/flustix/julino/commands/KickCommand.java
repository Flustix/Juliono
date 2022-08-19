package flustix.julino.commands;

import flustix.julino.command.Command;
import flustix.julino.utils.MessageUtils;
import flustix.julino.utils.UserUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class KickCommand extends Command {
    public KickCommand() {
        name = "kick";
        desc = "Kick a User.";
        longDesc = "Kicks a User from the Server.";
        permissions.add(Permission.KICK_MEMBERS);
    }

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        if (args.length == 0) {
            msg.getChannel().sendMessage("You need to specify a User to kick.").queue();
            return;
        }

        User toKick = UserUtils.getUser(args[0]);

        String reason = "";
        if (args.length > 1) {
            for (String arg : args) {
                if (arg.equals(args[0])) {
                    continue;
                }
                reason += arg + " ";
            }
        }

        msg.getGuild().kick(toKick, reason).complete();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Kicked " + toKick.getName() + " from the Server.")
                .setColor(0xFFAA55);

        if (!reason.isEmpty()) {
            embed.setDescription("With Reason: " + reason);
        }

        MessageUtils.reply(msg, embed);
    }
}
