package core.commandhandler.commands;

import core.commandhandler.BaseCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class Echo extends BaseCommand {
    public Echo() {
        super("echo");
    }

    @Override
    public SlashCommandData commandData() {
        return Commands.slash(commandName, "Echoes the content back to you.")
            .addOption(OptionType.STRING, "content", "The content to echo.", true);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping contentOption = event.getOption("content");
        if (contentOption == null) {
            event.reply("No content provided.").queue();
        } else {
            event.reply(contentOption.getAsString()).queue();
        }
    }
}
