package net.aerulion.membersurvey;

import java.util.HashMap;
import java.util.Map;
import net.aerulion.erenos.core.utils.console.ConsoleUtils;
import net.aerulion.membersurvey.cmd.CMD_survey;
import net.aerulion.membersurvey.listener.JoinListener;
import net.aerulion.membersurvey.listener.SurveyGUIListener;
import net.aerulion.membersurvey.task.LoadAllSurveysTask;
import net.aerulion.membersurvey.task.SurveyAlertTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

  public static @NotNull Map<String, Survey> activeSurveys = new HashMap<>();
  public static @NotNull Map<String, SurveyAlertTask> scheduledSurveyAlerts = new HashMap<>();
  public static Main plugin;

  @Override
  public void onDisable() {
    ConsoleUtils.sendColoredConsoleMessage(
        LegacyComponentSerializer.legacySection().deserialize(Lang.CONSOLE_DISABLING));
    ConsoleUtils.sendColoredConsoleMessage(
        LegacyComponentSerializer.legacySection().deserialize(Lang.CONSOLE_PLUGIN_DISABLED));
  }

  @Override
  public void onEnable() {
    ConsoleUtils.sendColoredConsoleMessage(
        LegacyComponentSerializer.legacySection().deserialize(Lang.CONSOLE_ENABLING));
    plugin = this;
    getCommand("survey").setExecutor(new CMD_survey());
    getCommand("survey").setTabCompleter(new CMD_survey());
    getServer().getPluginManager().registerEvents(new JoinListener(), this);
    getServer().getPluginManager().registerEvents(new SurveyGUIListener(), this);
    new LoadAllSurveysTask();
    ConsoleUtils.sendColoredConsoleMessage(
        LegacyComponentSerializer.legacySection().deserialize(Lang.CONSOLE_PLUGIN_ENABLED));
  }

}