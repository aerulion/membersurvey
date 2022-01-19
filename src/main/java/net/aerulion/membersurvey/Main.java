package net.aerulion.membersurvey;

import java.util.HashMap;
import java.util.Map;
import net.aerulion.membersurvey.cmd.CMD_survey;
import net.aerulion.membersurvey.listener.JoinListener;
import net.aerulion.membersurvey.listener.SurveyGUIListener;
import net.aerulion.membersurvey.task.LoadAllSurveysTask;
import net.aerulion.membersurvey.task.SurveyAlertTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends JavaPlugin {

  public static @NotNull Map<String, Survey> activeSurveys = new HashMap<>();
  public static @NotNull Map<String, SurveyAlertTask> scheduledSurveyAlerts = new HashMap<>();
  public static Main plugin;
  public static Economy economy;

  @Override
  public void onDisable() {
    ConsoleUtils.sendColoredConsoleMessage(Lang.CONSOLE_DISABLING);
    ConsoleUtils.sendColoredConsoleMessage(Lang.CONSOLE_PLUGIN_DISABLED);
  }

  @Override
  public void onEnable() {
    ConsoleUtils.sendColoredConsoleMessage(Lang.CONSOLE_ENABLING);
    plugin = this;
    if (!setupEconomy()) {
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    getCommand("survey").setExecutor(new CMD_survey());
    getCommand("survey").setTabCompleter(new CMD_survey());
    getServer().getPluginManager().registerEvents(new JoinListener(), this);
    getServer().getPluginManager().registerEvents(new SurveyGUIListener(), this);
    new LoadAllSurveysTask();
    ConsoleUtils.sendColoredConsoleMessage(Lang.CONSOLE_PLUGIN_ENABLED);
  }

  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    final @Nullable RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
        .getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    economy = rsp.getProvider();
    return true;
  }
}