package ru.javarush.islandModel.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.animal.predator.Predator;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter(AccessLevel.PROTECTED)
@Data
@Slf4j
public class Settings {
    private int width;
    private int length;
    private long duringOfCycle;
    private double percentWeightLoss;
    private double percentReducedSatiety;
    private double maxWeightOfPlant;
    private String plantPrint;
    private Map<Class<? extends Animal>, Integer> maxCountOfSteps = new ConcurrentHashMap<>();
    private Map<Class<? extends Predator>, Map<Class<? extends Eatable>, Integer>> foodPreferences = new ConcurrentHashMap<>();
    private Map<Class<? extends Animal>, Double> dailyAllowance = new ConcurrentHashMap<>();
    private Map<Class<? extends Animal>, Double> weightAvg = new ConcurrentHashMap<>();
    private Map<Class<? extends Animal>, Integer> maxCountOfBrood = new ConcurrentHashMap<>();
    private Map<Class<? extends Animal>, Integer> maxCountOnLocation = new ConcurrentHashMap<>();
    private Map<Class<? extends Animal>, String> animalPrints = new ConcurrentHashMap<>();
    private int corePoolSize;

    @JsonIgnore
    public static final String CONFIG_YAML = "/Users/Marya/ru.javarush.golf.kuznetsova.IslandModel/src/main/resources/config.yaml";

    @JsonIgnore
    private static volatile Settings settings;

    private Settings() {
        setDefaultSettings();
        loadSettingsFromYml();
    }

    public static Settings getSettings() {
        if (settings == null) settings = new Settings();
        return settings;
    }

    private void setDefaultSettings() {
        width = DefaultSettings.WIDTH;
        length = DefaultSettings.LENGTH;
        duringOfCycle = DefaultSettings.DURING_OF_CYCLE;
        percentWeightLoss = DefaultSettings.PERCENT_WEIGHT_LOSS;
        percentReducedSatiety = DefaultSettings.PERCENT_REDUCED_SATIETY;
        maxWeightOfPlant = DefaultSettings.MAX_WEIGHT_OF_PLANT;
        plantPrint = DefaultSettings.PLANT_PRINT;
        maxCountOfSteps = DefaultSettings.MAX_COUNT_OF_STEPS;
        foodPreferences = DefaultSettings.FOOD_PREFERENCES;
        dailyAllowance = DefaultSettings.DAILY_ALLOWANCE;
        weightAvg = DefaultSettings.ANIMAL_AVG_WEIGHT;
        maxCountOfBrood = DefaultSettings.MAX_COUNT_BROOD;
        maxCountOnLocation = DefaultSettings.MAX_COUNT_ON_LOCATION;
        animalPrints = DefaultSettings.ANIMAL_PRINTS;
        corePoolSize = DefaultSettings.CORE_POOL_SIZE;
    }

    private void loadSettingsFromYml() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader reader = mapper.readerForUpdating(this);
        try {
            reader.readValue(new File(CONFIG_YAML));
        } catch (IOException e) {
            log.info("failed attempt to download settings from a yaml file {}", CONFIG_YAML);
        }
    }

    @Override
    public String toString() {
        ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
        yaml.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return yaml.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}