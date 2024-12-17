package core.commandhandler;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.commandhandler.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class CommandHandler {
    private final static Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    private final static Map<String, BaseCommand> commands = new HashMap<>();

    static {
        addCommandToMap(new Ping());
        addCommandToMap(new Echo());
        addCommandToMap(new DatabaseTest());
    }

    private static void addCommandToMap(BaseCommand command) {
        commands.put(command.getCommandName(), command);
    }
    
    public static void executeCommand(SlashCommandInteractionEvent event, Connection connection) {
        String commandName = event.getName();
        BaseCommand command = commands.get(commandName);

        if (command != null) {
            if (command instanceof DatabaseCommand) {
                ((DatabaseCommand) command).execute(event, connection);
            } else {
                command.execute(event);
            }
        } else {
            event.reply("Unknown command").queue(); 
        }
    }

    // Register commands for a specific guild
    public static void registerCommandsGuild(Guild guild) {
        try {
            guild.updateCommands()
                .addCommands(commands.values().stream()
                    .map(BaseCommand::commandData)
                    .toArray(CommandData[]::new))
                .queue();

            logger.info("Guild Commands registered successfully.");
        } catch (Exception e) {
            logger.error("Error registering commands for guild", e);
        }
    }

    // Register commands globally
    public static void registerCommandsGlobal(JDA jda) {
        try {
            jda.updateCommands()
                .addCommands(commands.values().stream()
                    .map(BaseCommand::commandData)
                    .toArray(CommandData[]::new))
                .queue();

            logger.info("Global Commands registered successfully.");
        } catch (Exception e) {
            logger.error("Error registering global commands", e);
        }
    }
}
