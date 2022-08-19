package flustix.julino.commands;

import com.google.gson.JsonParser;
import flustix.julino.Main;
import flustix.julino.command.Command;
import flustix.julino.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super();
        this.name = "reload";
        this.desc = "Reloads the bot's config file.";
        this.longDesc = "Reloads the bot's config file.";
        this.permissions.add(Permission.ADMINISTRATOR);
    }

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        Main.config = JsonParser.parseString(Files.readString(Path.of("config/config.json"))).getAsJsonObject();

        MessageUtils.reply(msg, "Reloaded config file.");
    }
}
