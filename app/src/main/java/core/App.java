package core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import core.commandhandler.CommandHandler;
import core.events.EventHandler;

public class App {
    // Basic config stuff
    private static Dotenv dotenv = Dotenv.load();
    public static String DISCORD_TOKEN = dotenv.get("DISCORD_TOKEN");
    public static String DATABASE_URL = dotenv.get("DATABASE_URL");
    public static String GUILD_ID = dotenv.get("GUILD_ID");
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        JDA jda = initializeJDA();

        if (jda == null) {
            logger.error("Error initializing JDA");
            return;
        }

        addShutdownHook(jda);
        processArguments(args, jda);
    }

    private static JDA initializeJDA() {
        try {
            return JDABuilder
                    .createLight(DISCORD_TOKEN)
                    .addEventListeners(new EventHandler(DATABASE_URL))
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            logger.error("Error initializing JDA", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private static void addShutdownHook(JDA jda) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventHandler.shutdown();
            jda.shutdown();
        }));
    }

    private static void processArguments(String[] args, JDA jda) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "global":
                    CommandHandler.registerCommandsGlobal(jda);
                    break;
                case "guild":
                    registerGuildCommands(jda);
                    break;
                default:
                    logger.warn("Unknown argument: {}", arg);
                    break;
            }
        }
    }

    private static void registerGuildCommands(JDA jda) {
        Guild guild = jda.getGuildById(GUILD_ID);
        if (guild != null) {
            CommandHandler.registerCommandsGuild(guild);
        } else {
            logger.error("Guild not found");
        }
    }
}