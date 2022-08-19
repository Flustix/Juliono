package flustix.julino.logger;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageLogger {
    private static final Map<Long, CachedMessage> messages = new HashMap<>();

    public static void log(Message msg) {
        System.out.println("Logging message: " + msg.getContentRaw());
        ArrayList<String> attachments = new ArrayList<>();

        for (Message.Attachment attachment : msg.getAttachments()) {
            attachments.add(attachment.getUrl());
        }

        messages.put(msg.getIdLong(), new CachedMessage(msg.getIdLong(),
                msg.getAuthor().getIdLong(),
                msg.getChannel().getIdLong(),
                msg.getContentRaw(),
                attachments.toArray(new String[0])));

    }

    public static CachedMessage getLogged(long id) {
        return messages.getOrDefault(id, null);
    }
}