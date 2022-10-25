package asteroids;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Settings implements SaveHandler {

    private final String FILE_NAME;
    private final String PARENTDIRECTORY_NAME;

    private HashMap<String, Object> defaultSettings = new HashMap<>();
    private HashMap<String, Object> settings = new HashMap<>();

    public Settings(String PARENTDIRECTORY_NAME, String FILE_NAME) {
        checkValidFileString(PARENTDIRECTORY_NAME, "Directory name can only include letters, numbers and underscores.");
        checkValidFileString(FILE_NAME, "File name can only include letters, numbers and underscores.");

        this.PARENTDIRECTORY_NAME = PARENTDIRECTORY_NAME;
        this.FILE_NAME = FILE_NAME;

        defaultSettings.put("masterVolume", 100.0);
        defaultSettings.put("FXVolume", 100.0);
        defaultSettings.put("musicVolume", 100.0);
        defaultSettings.put("difficultyIsHard", false);
        defaultSettings.put("firstTimePlaying", true);

        load();
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (String s : settings.keySet()) {
                writer.write(s + ":" + settings.get(s) + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            new File(PARENTDIRECTORY_NAME).mkdir();
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            // reader.readLine().map(element -> element.split(":")).forEach(element -> {
            // if (Pattern.matches("[0-9]*", element[1])) {
            // settings.put(element[0], element[1]);
            // } else if (element[1].isEqual("true")) {
            // settings.put(element[0], true);
            // } else if (element[1].isEqual("false")) {
            // settings.put(element[0], false);
            // }
            // });
            List<String[]> lines = reader.lines().map(element -> element.split(":"))
                    .map(element -> new String[] { element[0], element[1] })
                    .collect(Collectors.toList());

            lines.stream().forEach(element -> {
                if (Pattern.matches("[0-9]*[.][0-9]*", element[1])) {
                    Double number = Double.parseDouble(element[1]);
                    if (0.0 <= number && number <= 100.0)
                        settings.put(element[0], number);
                } else if (element[1].equals("true")) {
                    settings.put(element[0], true);
                } else if (element[1].equals("false")) {
                    settings.put(element[0], false);
                }
            });
            reader.close();
        } catch (FileNotFoundException e) {
            new File(PARENTDIRECTORY_NAME).mkdir();
            // scoresList = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : defaultSettings.keySet()) {
            if (!settings.containsKey(s)) {
                settings.put(s, defaultSettings.get(s));
            }
        }
    }

    public double getMasterVolume() {
        return (double) settings.get("masterVolume");
    }

    public double getFXVolume() {
        return (double) settings.get("FXVolume");
    }

    public double getMusicVolume() {
        return (double) settings.get("musicVolume");
    }

    public boolean getDifficultyIsHard() {
        return (boolean) settings.get("difficultyIsHard");
    }

    public boolean getFirstTimePlaying() {
        return (boolean) settings.get("firstTimePlaying");
    }

    public void setMasterVolume(double volume) {
        settings.put("masterVolume", volume);
        save();
    }

    public void setFXVolume(double volume) {
        settings.put("FXVolume", volume);
        save();
    }

    public void setMusicVolume(double volume) {
        settings.put("musicVolume", volume);
        save();
    }

    public void setDifficultyIsHard(boolean difficultyIsHard) {
        settings.put("difficultyIsHard", difficultyIsHard);
        save();
    }

    public void setFirstTimePlaying(boolean firstTimePlaying) {
        settings.put("firstTimePlaying", firstTimePlaying);
        save();
    }

    private String getFilePath() {
        return PARENTDIRECTORY_NAME + "/" + FILE_NAME + ".txt";

    }

    private void checkValidFileString(String s, String message) {
        if (!Pattern.matches("[a-zA-Z0-9_]+", s))
            throw new IllegalArgumentException(message);
    }

}
