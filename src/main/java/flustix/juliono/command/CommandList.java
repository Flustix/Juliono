package flustix.juliono.command;

import flustix.juliono.Main;
import flustix.juliono.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;

public class CommandList {
    static TreeMap<String, Command> commands = new TreeMap<>();

    public static void init() {
        Reflections reflects = new Reflections("flustix.juliono");
        Set<Class<? extends Command>> classes = reflects.getSubTypesOf(Command.class);
        for (Class<? extends Command> command : classes) {
            try {
                addCommand(command.getConstructor().newInstance());
            } catch (Exception e) {
                Main.logger.warn("Could not load command " + command.getName());
                Main.logger.warn(e.getMessage());
            }
        }
    }

    static void addCommand(Command command) {
        if (command.name.equals("")) {
            throw new IllegalArgumentException("Command name cannot be empty");
        }

        commands.put(command.name, command);
        Main.logger.info("Loaded command " + command.name);
    }

    public static void check(MessageReceivedEvent msg) {
        String[] args = msg.getMessage().getContentRaw().substring(Main.prefix.length()).split(" ");

        if (args.length > 0) exec(msg, args);
    }

    static void exec(MessageReceivedEvent msg, String[] split) {
        if (commands.containsKey(split[0])) {
            Command command = commands.get(split[0]);
            String[] args = Arrays.copyOfRange(split, 1, split.length);

            try {
                boolean hasPermission = true;

                if (command.permissions.size() > 0) {
                    for (Permission permission : command.permissions) {
                        if (!msg.getMember().hasPermission(permission)) {
                            hasPermission = false;
                        }
                    }
                }

                if (hasPermission) {
                    command.exec(msg, args);
                } else {
                    String missingPermissions = "";

                    for (Permission permission : command.permissions) {
                        if (!missingPermissions.equals("")) {
                            missingPermissions += ", ";
                        }
                        missingPermissions += permission.getName();
                    }

                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("You dont have enough permissions to run this command.")
                            .setDescription("You need the following permissions to execute this command: `" + missingPermissions + "`")
                            .setColor(0xFF5555);

                    MessageUtils.reply(msg, embed);
                }
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Error")
                        .setColor(0xFF5555)
                        .setDescription("An error occured while executing the command!")
                        .addField("Stacktrace", "```java\n" + e + "```", false);
                MessageUtils.reply(msg, embed);
            }
        }
    }

    public static TreeMap<String, Command> getCommands() {
        return commands;
    }
}
