package flustix.juliono.logger;

import flustix.juliono.Main;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.User;

public class LoggedMessage {
    public String userid = "";
    public String messageid = "";
    public String channelid = "";
    public long timestamp = 0;
    public String content = "";
    public String attachments = "";

    public LoggedMessage() {}

    public User user;
    public Channel channel;

    public void loadData() {
        user = Main.bot.getUserById(userid);
        channel = Main.bot.getTextChannelById(channelid);

        if (user == null) {
            try {
                user = Main.bot.retrieveUserById(userid).complete();
            } catch (Exception ignored) {}
        }
    }
}
