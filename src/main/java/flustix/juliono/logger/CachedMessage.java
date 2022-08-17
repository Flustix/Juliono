package flustix.juliono.logger;

public class CachedMessage {
    public long id;
    public long userId;
    public long channelId;
    public String content;
    public String[] attachments;

    public CachedMessage(long id, long userId, long channelId, String content, String[] attachments) {
        this.id = id;
        this.userId = userId;
        this.channelId = channelId;
        this.content = content;
        this.attachments = attachments;
    }
}