package flustix.julino;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import flustix.julino.command.CommandList;
import flustix.julino.listeners.GuildMemberListener;
import flustix.julino.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.EnumSet;


public class Main {
    public static String prefix = "!";
    public static int accentColor = 0xf36641;
    public static JDA bot;
    public static Logger logger = LoggerFactory.getLogger("julino");
    public static JsonObject config;
    public static Date startTime;

    public static void main(String[] args) throws Exception {
        startTime = Date.from(new Date().toInstant());
        CommandList.init();

        config = JsonParser.parseString(Files.readString(Path.of("config/config.json"))).getAsJsonObject();

        JDABuilder jda = JDABuilder.createDefault(config.get("token").getAsString());
        jda.enableIntents(EnumSet.allOf(GatewayIntent.class));
        bot = jda.build();
        bot.addEventListener(new MessageListener());
        bot.addEventListener(new GuildMemberListener());
    }
}
