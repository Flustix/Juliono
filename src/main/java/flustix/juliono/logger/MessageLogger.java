package flustix.juliono.logger;

import flustix.juliono.database.Database;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageLogger {
    public static void log(Message msg) {
        String attachments = "";

        for (Message.Attachment attachment : msg.getAttachments()) {
            if (!attachments.isEmpty()) {
                attachments += ",";
            }
            attachments += attachment.getUrl();
        }

        Database.execQuery("INSERT INTO `messages` (`userid`, `messageid`, `channelid`, `timestamp`, `content`, `attachments`) VALUES (" +
                "'" + msg.getAuthor().getId() +
                "', '" + msg.getId() +
                "', '" + msg.getChannel().getId() +
                "', '" + System.currentTimeMillis() +
                "', '" + msg.getContentRaw().replace("'", "\\'").replace("`", "\\`") +
                "', '" + attachments +
                "')");
    }

    public static LoggedMessage getLogged(String id) throws SQLException {
        ResultSet rs = Database.execQuery("SELECT * FROM `messages` WHERE `messageid` = '" + id + "'");

        while (rs.next()) {
            LoggedMessage message = new LoggedMessage();
            message.userid = rs.getString("userid");
            message.messageid = rs.getString("messageid");
            message.channelid = rs.getString("channelid");
            message.timestamp = rs.getLong("timestamp");
            message.content = rs.getString("content");
            message.attachments = rs.getString("attachments");

            return message;
        }

        return null;
    }
}