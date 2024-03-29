package net.aerulion.membersurvey.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.aerulion.erenos.core.utils.command.CommandUtils;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.conversation.QuestionConversation;
import net.aerulion.membersurvey.inventory.SurveyInventory;
import net.aerulion.membersurvey.task.EvaluationTask;
import net.aerulion.membersurvey.task.SurveyListTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_survey implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label,
      final String @NotNull [] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(Lang.ERROR_NO_PLAYER);
      return true;
    }

    if (!(sender.hasPermission("membersurvey.admin") || sender.hasPermission(
        "membersurvey.member"))) {
      sender.sendMessage(Lang.ERROR_NO_PERMISSION);
      return true;
    }

    if (args.length != 0) {
      if (sender.hasPermission("membersurvey.admin")) {
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("add")) {
            final @NotNull ConversationFactory cf = new ConversationFactory(Main.plugin);
            final @NotNull ConversationPrefix cp = prefix -> Lang.CHAT_PREFIX;
            final @NotNull Conversation c = cf.withFirstPrompt(new QuestionConversation())
                .withEscapeSequence("stop").withModality(false).withLocalEcho(false).withPrefix(cp)
                .buildConversation((Player) sender);
            c.begin();
            return true;
          }
          if (args[0].equalsIgnoreCase("list")) {
            new SurveyListTask((Player) sender);
            return true;
          }
        }
        if (args.length == 2) {
          if (args[0].equalsIgnoreCase("evaluate")) {
            if (!Main.activeSurveys.containsKey(args[1])) {
              sender.sendMessage(Lang.ERROR_SURVEY_ID_NOT_FOUND);
              return true;
            }
            new EvaluationTask((Player) sender, Main.activeSurveys.get(args[1]));
            return true;
          }
        }
      }
      sender.sendMessage(Lang.ERROR_WRONG_ARGUMENTS);
      return true;
    }

    for (final @NotNull Survey survey : Main.activeSurveys.values()) {
      if (!survey.getResults().containsKey(((Player) sender).getUniqueId().toString())) {
        ((Player) sender).openInventory(SurveyInventory.create(survey));
        return true;
      }
    }
    sender.sendMessage(Lang.MESSAGE_NO_SURVEY_AVAILABLE);
    return true;
  }

  @Override
  public List<String> onTabComplete(final @NotNull CommandSender sender,
      final @NotNull Command command, final @NotNull String label, final String @NotNull [] args) {
    if (sender.hasPermission("membersurvey.admin")) {
      if (args.length == 1) {
        return CommandUtils.filterForTabCompleter(
            new ArrayList<>(Arrays.asList("add", "delete", "evaluate", "list")), args[0]);
      }
      if (args.length == 2) {
        if (args[0].equalsIgnoreCase("evaluate")) {
          return CommandUtils.filterForTabCompleter(new ArrayList<>(Main.activeSurveys.keySet()),
              args[1]);
        }
      }
    }
    return Collections.emptyList();
  }

}