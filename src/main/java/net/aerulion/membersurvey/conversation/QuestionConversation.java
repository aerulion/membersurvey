package net.aerulion.membersurvey.conversation;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Lang;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class QuestionConversation extends ValidatingPrompt {

  @Override
  protected boolean isInputValid(ConversationContext con, String input) {
    return true;
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext con, String input) {
    ConversationFactory cf = new ConversationFactory(Main.plugin);
    ConversationPrefix cp = prefix -> Lang.CHAT_PREFIX;
    Conversation c = cf.withFirstPrompt(new OptionsConversation(input)).withEscapeSequence("stop")
        .withModality(false).withLocalEcho(false).withPrefix(cp)
        .buildConversation(con.getForWhom());
    c.begin();
    return null;
  }

  @Override
  public String getPromptText(ConversationContext con) {
    return Lang.CONVERSATION_QUESTION;
  }
}