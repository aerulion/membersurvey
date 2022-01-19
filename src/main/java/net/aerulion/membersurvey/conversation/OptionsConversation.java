package net.aerulion.membersurvey.conversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Lang;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;

public class OptionsConversation extends ValidatingPrompt {

  private final String question;

  public OptionsConversation(final String question) {
    this.question = question;
  }

  @Override
  protected boolean isInputValid(final @NotNull ConversationContext context, final @NotNull String input) {
    return true;
  }

  @Override
  protected Prompt acceptValidatedInput(final @NotNull ConversationContext context, final @NotNull String input) {
    final @NotNull List<String> options = new ArrayList<>();
    Collections.addAll(options, input.split("#"));
    final @NotNull ConversationFactory cf = new ConversationFactory(Main.plugin);
    final @NotNull ConversationPrefix cp = prefix -> Lang.CHAT_PREFIX;
    final @NotNull Conversation c = cf.withFirstPrompt(new RewardConversation(this.question, options))
        .withEscapeSequence("stop").withModality(false).withLocalEcho(false).withPrefix(cp)
        .buildConversation(context.getForWhom());
    c.begin();
    return null;
  }

  @Override
  public @NotNull String getPromptText(final @NotNull ConversationContext context) {
    return Lang.CONVERSATION_OPTIONS;
  }
}