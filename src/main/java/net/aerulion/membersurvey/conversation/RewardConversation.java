package net.aerulion.membersurvey.conversation;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.task.SaveToFileTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RewardConversation extends ValidatingPrompt {

    private final String question;
    private final List<String> options;

    public RewardConversation(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    @Override
    protected boolean isInputValid(ConversationContext con, String input) {
        try {
            double parsedDouble = Double.parseDouble(input);
            return parsedDouble >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext con, String input) {
        String id = UUID.randomUUID().toString();
        Main.activeSurveys.put(id, new Survey(id, this.question, this.options, new ArrayList<>(), Double.parseDouble(input)));
        ((Player) con.getForWhom()).sendMessage(Lang.MESSAGE_SURVEY_ADDED);
        new SaveToFileTask(id);
        return null;
    }

    @Override
    public String getPromptText(ConversationContext con) {
        return Lang.CONVERSATION_REWARD;
    }
}