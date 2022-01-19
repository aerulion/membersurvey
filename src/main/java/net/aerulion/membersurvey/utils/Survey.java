package net.aerulion.membersurvey.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Survey {

  private final String id;
  private final String question;
  private final List<String> options;
  private final @NotNull HashMap<String, String> results;
  private final double reward;

  public Survey(final String id, final String question, final List<String> options,
      final @NotNull List<String> results, final double reward) {
    this.id = id;
    this.question = question;
    this.options = options;
    this.results = new HashMap<>();
    for (final @NotNull String string : results) {
      final String @NotNull [] split = string.split("###");
      this.results.put(split[0], split[1]);
    }
    this.reward = reward;
  }

  public void addResult(final String uuid, final String result) {
    this.results.put(uuid, result);
  }

  public String getQuestion() {
    return question;
  }

  public List<String> getOptions() {
    return options;
  }

  public @NotNull Map<String, String> getResults() {
    return results;
  }

  public String getId() {
    return id;
  }

  public double getReward() {
    return reward;
  }
}