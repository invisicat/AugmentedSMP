package dev.ricecx.augmentedsmp.commands;


import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.core.command.CommandsEnum;
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
                if(args[0].contains("command:") && CommandsEnum.fromName(args[0]).isPresent())
                    sendCommandHelp(sender, CommandsEnum.fromName(args[0]).get());
                else if(args[0].contains("category:") && CommandCategory.fromName(args[0]) != null)
                    sendCategoryHelp(sender, CommandCategory.fromName(args[0]));
                else
                    sendPaginatedHelpMessage(sender, 1);
            }
        }
    }

    private void sendCategoryHelp(CommandSender sender, CommandCategory category) {}
    private void sendCommandHelp(CommandSender sender, CommandsEnum command) {
        BaseComponent[] title = Constants.createTitle().create();
        sender.sendMessage(" ");
        sender.spigot().sendMessage(title);
        sender.sendMessage(command.getCommandMetadata().name());
        sender.sendMessage("Usage:" + command.getCommandMetadata().usage());
        sender.sendMessage("Aliases:" + String.join(",", command.getCommandMetadata().aliases()));
        sender.sendMessage("Description:" + command.getCommandMetadata().description());
    }

    private void sendHelpMessage(CommandSender sender) {
        for (CommandsEnum command : CommandsEnum.values()) {
            sender.sendMessage("/asmp " + command.getCommandMetadata().name() + " - " + command.getCommandMetadata().description());
        }
    }

    private void sendPaginatedHelpMessage(CommandSender sender, int currPage) {
        int maxPages = (int) Math.ceil(CommandsEnum.values().length / 8.0);

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
        for (CommandsEnum command : Arrays.stream(CommandsEnum.values()).collect(Collectors.toList()).subList(idx, Math.min(CommandsEnum.values().length, (idx + 8 - 1)))) {
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
            for (CommandsEnum command : CommandsEnum.values()) {
                combinations.add("command:" + command.getCommandMetadata().name());
            }

            for (int i = 0; i < ((int) Math.ceil(CommandsEnum.values().length / 8.0)); i++) {
                combinations.add(String.valueOf(i + 1));
            }
        }

        return combinations;
    }
}
