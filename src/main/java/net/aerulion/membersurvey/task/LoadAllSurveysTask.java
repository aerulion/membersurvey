package net.aerulion.membersurvey.task;

import java.io.File;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class LoadAllSurveysTask extends BukkitRunnable {

  public LoadAllSurveysTask() {
    this.runTaskAsynchronously(Main.plugin);
  }

  @Override
  public void run() {
    final long start = System.currentTimeMillis();
    File folder = new File("plugins/MemberSurvey/Surveys");
    File[] listOfFiles = folder.listFiles();
    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile()) {
          FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
          Main.activeSurveys.put(file.getName().substring(0, file.getName().length() - 4),
              new Survey(file.getName().substring(0, file.getName().length() - 4),
                  cfg.getString("QUESTION"), cfg.getStringList("OPTIONS"),
                  cfg.getStringList("RESULTS"), cfg.getDouble("REWARD")));
        }
      }
    }
    ConsoleUtils.sendColoredConsoleMessage(
        Lang.CHAT_PREFIX + "§e" + Main.activeSurveys.size() + Lang.CONSOLE_SURVEYS_LOADED + (
            System.currentTimeMillis() - start) + "ms");
  }
}