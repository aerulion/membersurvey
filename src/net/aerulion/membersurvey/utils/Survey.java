package net.aerulion.membersurvey.utils;

import java.util.HashMap;
import java.util.List;

public class Survey {

    private final String id;
    private final String question;
    private final List<String> options;
    private final HashMap<String, String> results;
    private final double reward;

    public Survey(String id, String question, List<String> options, List<String> results, double reward) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.results = new HashMap<>();
        for (String string : results) {
            String[] split = string.split("###");
            this.results.put(split[0], split[1]);
        }
        this.reward = reward;
    }

    public void addResult(String uuid, String result) {
        this.results.put(uuid, result);
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public HashMap<String, String> getResults() {
        return results;
    }

    public String getId() {
        return id;
    }

    public double getReward() {
        return reward;
    }
}