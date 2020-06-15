package net.aerulion.membersurvey.task;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Survey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveToFileTask extends BukkitRunnable {

    String uuid;

    public SaveToFileTask(String uuid) {
        this.uuid = uuid;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        Survey survey = Main.activeSurveys.get(this.uuid);
        File file = new File("plugins/MemberSurvey/Surveys", this.uuid + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("QUESTION", survey.getQuestion());
        cfg.set("OPTIONS", survey.getOptions());
        cfg.set("REWARD", survey.getReward());
        List<String> results = new ArrayList<>();
        for (String key : survey.getResults().keySet()) {
            results.add(key + "###" + survey.getResults().get(key));
        }
        cfg.set("RESULTS", results);
        try {
            cfg.save(file);
        } catch (IOException ignored) {
        }
    }
}