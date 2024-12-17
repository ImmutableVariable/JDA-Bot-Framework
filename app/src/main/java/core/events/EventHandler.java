package core.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.App;
import core.commandhandler.CommandHandler;
import core.database.DatabaseManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventHandler extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static DatabaseManager databaseManager;

    public EventHandler(String database_url) {
        super();
        databaseManager = new DatabaseManager(database_url);
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("Logged in as: " + event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        try {
            CommandHandler.executeCommand(event, databaseManager.getConnection());
        } catch (Exception e) {
            logger.error("Error executing command", e);
        }
    }

    public static void shutdown() {
        if (databaseManager != null) {
            databaseManager.close();
        }
    }
}