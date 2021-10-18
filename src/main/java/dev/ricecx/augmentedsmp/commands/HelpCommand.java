package dev.ricecx.augmentedsmp.commands;

import dev.ricecx.augmentedsmp.core.command.Commands;
import dev.ricecx.augmentedsmp.core.command.annotations.Command;
import dev.ricecx.augmentedsmp.core.command.CommandCategory;
import dev.ricecx.augmentedsmp.core.command.ICommand;
import dev.ricecx.augmentedsmp.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Command(
        name = "help",
        usage = "help [page] | [commandName] | [category]",
        category = CommandCategory.GENERAL
)
public class HelpCommand implements ICommand {

    private static final List<String> combinations = new ArrayList<>();

    @Override
    public void run(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sendHelpMessage(sender);
            return;
        }

        if(args.length <= 0) {
            sendPaginatedHelpMessage(sender, 1);
        } else {
            try {
                int pageNumber = Integer.parseInt(args[0]);
                sendPaginatedHelpMessage(sender, pageNumber);
            } catch(NumberFormatException e) {
                String[] input = args[0].split("(category:)|(command:)");
                if(args[0].contains("command:") && Commands.fromName(input[1]).isPresent())
                    sendCommandHelp(sender, Commands.fromName(input[1]).get());
                else if(args[0].contains("category:") && CommandCategory.fromName(input[1]) != null)
                    sendCategoryHelp(sender, CommandCategory.fromName(input[1]));
                else
                    sendPaginatedHelpMessage(sender, 1);
            }
        }
    }

    private void sendCategoryHelp(CommandSender sender, CommandCategory category) {}
    private void sendCommandHelp(CommandSender sender, Commands command) {
        BaseComponent[] title = Constants.createTitle().create();
        sender.sendMessage(" ");
        sender.spigot().sendMessage(title);
        sender.sendMessage(fmt("&8--------------------------------------"));
        sender.sendMessage(fmt("&dCommand:" + command.getCommandMetadata().name()));
        sender.sendMessage("&dUsage: " + command.getCommandMetadata().usage());
        sender.sendMessage("&dAliases: " + String.join(",", command.getCommandMetadata().aliases()));
        sender.sendMessage("&dDescription: " + command.getCommandMetadata().description());
    }

    private void sendHelpMessage(CommandSender sender) {
        for (Commands command : Commands.values()) {
            sender.sendMessage("/asmp " + command.getCommandMetadata().name() + " - " + command.getCommandMetadata().description());
        }
    }

    private void sendPaginatedHelpMessage(CommandSender sender, int currPage) {
        int maxPages = (int) Math.ceil(Commands.values().length / 8.0);

        if(currPage > maxPages || currPage <= 0) currPage = 1;

        TextComponent leftArrow = new TextComponent(" ◀ ");
        TextComponent rightArrow = new TextComponent("▶ ");

        rightArrow.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/asmp help " + (currPage + 1)));
        rightArrow.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Go to page " + (currPage + 1))));
        leftArrow.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/asmp help " + (currPage - 1)));
        leftArrow.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Go to page " + (currPage - 1))));
        // Title
        BaseComponent[] title =  Constants.createTitle()
                .append(" Page " + currPage + "/" + maxPages).color(ChatColor.WHITE)
                .append(leftArrow).bold(true)
                .append(rightArrow).bold(true)
                .create();

        sender.sendMessage(" ");
        sender.spigot().sendMessage(title);
        sender.sendMessage(fmt("&8--------------------------------------"));

        int idx = (currPage - 1) * 8;
        for (Commands command : Arrays.stream(Commands.values()).collect(Collectors.toList()).subList(idx, Math.min(Commands.values().length, (idx + 8 - 1)))) {
            sender.sendMessage(fmt("&a/asmp " + command.getCommandMetadata().name() + " - " + command.getCommandMetadata().description()));
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();

        if (args.length <= 0) {
            completions.addAll(getAllPossibleCombinations());
        } else if (args.length <= 2)
            StringUtil.copyPartialMatches(args[1], getAllPossibleCombinations(), completions);
        else
            return List.of();

        Collections.sort(completions);
        return completions;
    }

    private List<String> getAllPossibleCombinations() {
        if (combinations.size() <= 0) {

            for (CommandCategory category : CommandCategory.values()) {
                combinations.add("category:" + StringUtils.capitalize(category.name().toLowerCase(Locale.ROOT)));
            }
            for (Commands command : Commands.values()) {
                combinations.add("command:" + command.getCommandMetadata().name());
            }

            for (int i = 0; i < ((int) Math.ceil(Commands.values().length / 8.0)); i++) {
                combinations.add(String.valueOf(i + 1));
            }
        }

        return combinations;
    }
}
