package core.commandhandler;

import java.sql.Connection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface DatabaseCommand {
    void execute(SlashCommandInteractionEvent event, Connection connection);
}
