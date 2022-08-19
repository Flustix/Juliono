package flustix.julino.utils;

import flustix.julino.Main;
import net.dv8tion.jda.api.entities.User;

public class UserUtils {
    public static User getUser(String id) {
        String userId = id
                .replace("<@", "")
                .replace(">", "");

        User u = Main.bot.getUserById(userId);

        if (u == null) {
            u = Main.bot.retrieveUserById(userId).complete();
        }

        return u;
    }
}
