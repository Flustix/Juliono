package flustix.juliono.listeners;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import flustix.juliono.Main;
import flustix.juliono.command.CommandList;
import flustix.juliono.logger.LoggedMessage;
import flustix.juliono.logger.MessageLogger;
import flustix.juliono.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class MessageListener extends ListenerAdapter {
    public void onMessageReceived(@NotNull MessageReceivedEvent msg) {
        String content = msg.getMessage().getContentRaw().toLowerCase();

        if (content.startsWith(Main.prefix)) CommandList.check(msg);

        if (msg.getGuild().getId().equals("787723588202397707") && !msg.getAuthor().isBot()) {
            MessageLogger.log(msg.getMessage());
        }
    }

    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        try {
            LoggedMessage message = MessageLogger.getLogged(event.getMessageId());
            message.loadData();

            EmbedBuilder embed = new EmbedBuilder().setColor(Main.accentColor);

            embed.setTitle("Message deleted!");
            embed.setDescription(
                    "Sent by " + message.user.getAsMention() +
                            " in " + message.channel.getAsMention() + " " +
                            TimeAgo.using(message.timestamp)
            );

            if (!message.content.isEmpty())
                embed.addField("Content", message.content, false);

            if (!message.attachments.isEmpty())
                embed.addField("Attachments", message.attachments.replace(",", ", "), false);

            MessageUtils.send(Main.config.get("logChannel").getAsString(), embed);
        } catch (SQLException e) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("A message got deleted! But... i dont know what it was.")
                    .setDescription("Message ID: " + event.getMessageId())
                    .setColor(Main.accentColor);
            MessageUtils.send(Main.config.get("logChannel").getAsString(), embed);
            e.printStackTrace();
        }
    }
}
