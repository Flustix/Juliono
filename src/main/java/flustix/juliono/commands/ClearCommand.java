package flustix.juliono.commands;

import flustix.juliono.command.Command;
import flustix.juliono.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ClearCommand extends Command {
    public ClearCommand() {
        name = "clear";
        desc = "Clears the chat.";
        longDesc = "Clears the chat.\nUse `!clear <number>` to clear a specific number of messages.";
        permissions.add(Permission.MESSAGE_MANAGE);
    }

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        int msgCount = 100;
        try {
            msgCount = Integer.parseInt(args[0]);
        } catch (Exception ignored) {}

        if (msgCount > 100) {
            MessageUtils.reply(msg, "You can only clear 100 messages at a time.");
            return;
        }

        if (msgCount < 1) {
            MessageUtils.reply(msg, "You can't clear less than 1 message. (Like what you are doing)");
            return;
        }


        int finalMsgCount = msgCount;
        new Thread(() -> {
            List<Message> msgList = msg.getChannel().getHistory().retrievePast(finalMsgCount).complete();
            msg.getChannel().purgeMessages(msgList);

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(0x55ff55)
                    .setTitle("Cleared " + finalMsgCount + " messages.");

            MessageUtils.send(msg.getChannel().getId(), embed);
        }).start();
    }
}
