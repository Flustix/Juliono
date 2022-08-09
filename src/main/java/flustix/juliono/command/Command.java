package flustix.juliono.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public String name = "";
    public String desc = "";
    public String longDesc = "";
    public List<Permission> permissions = new ArrayList<>();
    public boolean hidden = false;

    public Command() {}

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        msg.getChannel().sendTyping().complete();
    }
}
