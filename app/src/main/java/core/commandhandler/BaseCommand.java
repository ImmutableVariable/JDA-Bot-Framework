package core.commandhandler;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class BaseCommand {
    protected String commandName;

    protected BaseCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public SlashCommandData commandData() {
        return Commands.slash(commandName, "No description provided.");
    }

    public void execute(SlashCommandInteractionEvent event) {
        event.reply(getCommandName() + " is not yet implemented.").queue();
    }
}
