package net.aerulion.membersurvey.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Survey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SaveToFileTask extends BukkitRunnable {

  String uuid;

  public SaveToFileTask(final String uuid) {
    this.uuid = uuid;
    this.runTaskAsynchronously(Main.plugin);
  }

  @Override
  public void run() {
    final Survey survey = Main.activeSurveys.get(this.uuid);
    final @NotNull File file = new File("plugins/MemberSurvey/Surveys", this.uuid + ".yml");
    final @NotNull FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    cfg.set("QUESTION", survey.getQuestion());
    cfg.set("OPTIONS", survey.getOptions());
    cfg.set("REWARD", survey.getReward());
    final @NotNull List<String> results = new ArrayList<>();
    for (final String key : survey.getResults().keySet()) {
      results.add(key + "###" + survey.getResults().get(key));
    }
    cfg.set("RESULTS", results);
    try {
      cfg.save(file);
    } catch (final IOException ignored) {
    }
  }
}