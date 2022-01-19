package net.aerulion.membersurvey.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.task.SaveToFileTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RewardConversation extends ValidatingPrompt {

  private final String question;
  private final List<String> options;

  public RewardConversation(final String question, final List<String> options) {
    this.question = question;
    this.options = options;
  }

  @Override
  protected boolean isInputValid(final @NotNull ConversationContext context, final @NotNull String input) {
    try {
      final double parsedDouble = Double.parseDouble(input);
      return parsedDouble >= 0;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  @Override
  protected Prompt acceptValidatedInput(final @NotNull ConversationContext context, final @NotNull String input) {
    final String id = UUID.randomUUID().toString();
    Main.activeSurveys.put(id,
        new Survey(id, this.question, this.options, new ArrayList<>(), Double.parseDouble(input)));
    ((Player) context.getForWhom()).sendMessage(Lang.MESSAGE_SURVEY_ADDED);
    new SaveToFileTask(id);
    return null;
  }

  @Override
  public @NotNull String getPromptText(final @NotNull ConversationContext context) {
    return Lang.CONVERSATION_REWARD;
  }
}