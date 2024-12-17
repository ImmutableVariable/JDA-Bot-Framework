package core.commandhandler.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import core.commandhandler.BaseCommand;
import core.commandhandler.DatabaseCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class DatabaseTest extends BaseCommand implements DatabaseCommand {
    public DatabaseTest() {
        super("database-test");
    }

    @Override
    public SlashCommandData commandData() {
        return Commands.slash(commandName, "Returns 1 from sqlite database.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            resultSet.next();
            int result = resultSet.getInt(1);
            event.reply("Result: " + result).queue();
        } catch (Exception e) {
            event.reply("Error fetching quote from database.").queue();
        }
    }
}
