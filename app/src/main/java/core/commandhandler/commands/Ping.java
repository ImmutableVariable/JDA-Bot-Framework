package core.commandhandler.commands;

import core.commandhandler.BaseCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class Ping extends BaseCommand {
    public Ping() {
        super("ping");
    }

    @Override
    public SlashCommandData commandData() {
        return Commands.slash(commandName, "Replies with pong!");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}