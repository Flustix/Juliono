package flustix.juliono.listeners;

import flustix.juliono.Main;
import flustix.juliono.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildMemberListener extends ListenerAdapter {
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (!event.getGuild().getId().equals("787723588202397707")) {
            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Welcome to the server, " + event.getUser().getName() + "!")
                .setDescription("Please read the rules and enjoy your stay!")
                .setThumbnail(event.getUser().getAvatarUrl())
                .setColor(Main.accentColor);

        MessageBuilder message = new MessageBuilder()
                .setContent(event.getUser().getAsMention())
                .setEmbeds(embed.build());

        MessageUtils.send(Main.config.get("joinChannel").getAsString(), message);
    }

    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (!event.getGuild().getId().equals("787723588202397707")) {
            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Goodbye, " + event.getUser().getName() + "!")
                .setDescription("We hope to see you again soon!")
                .setColor(Main.accentColor);

        MessageUtils.send(Main.config.get("leaveChannel").getAsString(), embed);
    }
}
