package ru.javarush.islandModel.utils;

import ru.javarush.islandModel.model.AnimalTypeInfo;
import ru.javarush.islandModel.model.Plant;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.island.Location;
import ru.javarush.islandModel.service.LocationService;
import ru.javarush.islandModel.settings.Settings;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Statistics {
    private final LocationService locationService;
    private static Map<Class<? extends Eatable>, Long> wasEatenFromStart = new ConcurrentHashMap<>();
    private static Map<Class<? extends Eatable>, Long> wasEatenForLastCycle = new ConcurrentHashMap<>();
    private static Map<Class<? extends Animal>, Long> diedOfHungerFromStart = new ConcurrentHashMap<>();
    private static Map<Class<? extends Animal>, Long> diedOfHungerForLastCycle = new ConcurrentHashMap<>();
    private static Map<Class<? extends Animal>, Long> wasBornFromStart = new ConcurrentHashMap<>();
    private static Map<Class<? extends Animal>, Long> wasBornForLastCycle = new ConcurrentHashMap<>();
    private final static Map<Class<? extends Animal>, String> ANIMAL_PRINTS = Settings.getSettings().getAnimalPrints();
    private final static String PLANT_PRINT = Settings.getSettings().getPlantPrint();
    private static long countOfCycles;

    public Statistics(LocationService locationService) {
        this.locationService = locationService;
    }

    public void printStatistics() {
        System.out.println("Day " + ++countOfCycles);
        ANIMAL_PRINTS.forEach((clazz, print) -> {
            System.out.println(getAnimalTypeInfo(clazz, print));
        });
        System.out.println(getPlantInfo(PLANT_PRINT) + "\n");
        printWasEaten();
        printDiedOfHunger();
        printCountOfBrood();
        clearPrevCycle();
        System.out.println("__________________________________________________________________________");
    }

    public static void addEatenAnimal(Class<? extends Eatable> clazz) {
        wasEatenFromStart.put(clazz, wasEatenFromStart.containsKey(clazz) ? wasEatenFromStart.get(clazz) + 1 : 1);
        wasEatenForLastCycle.put(clazz, wasEatenForLastCycle.containsKey(clazz) ? wasEatenForLastCycle.get(clazz) + 1
                : 1);
    }

    public static void addDiedOfHunger(Class<? extends Animal> clazz) {
        diedOfHungerFromStart.put(clazz, diedOfHungerFromStart.containsKey(clazz) ?
                diedOfHungerFromStart.get(clazz) + 1 : 1);
        diedOfHungerForLastCycle.put(clazz, diedOfHungerForLastCycle.containsKey(clazz) ?
                diedOfHungerForLastCycle.get(clazz) + 1 : 1);
    }

    public static void addWasBorn(Class<? extends Animal> clazz, int count) {
        wasBornFromStart.put(clazz, wasBornFromStart.containsKey(clazz) ? wasBornFromStart.get(clazz) + count : 1);
        wasBornForLastCycle.put(clazz, wasBornForLastCycle.containsKey(clazz) ?
                wasBornForLastCycle.get(clazz) + count : 1);
    }

    private static void clearPrevCycle() {
        wasEatenForLastCycle.clear();
        diedOfHungerForLastCycle.clear();
        wasBornForLastCycle.clear();
    }

    private void printWasEaten() {
        AtomicBoolean areThereAnyEaten = new AtomicBoolean(false);
        StringBuilder infoFromStart = new StringBuilder();

        wasEatenFromStart.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoFromStart.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyEaten.set(true);
                });

        if (!areThereAnyEaten.get()) System.out.println("Since the start of the game not a single animal has been eaten");
        else System.out.println("Since the start of the game have been eaten: \n" + infoFromStart.append("\n"));

        areThereAnyEaten.set(false);
        StringBuilder infoLastTime = new StringBuilder();

        Statistics.wasEatenForLastCycle.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoLastTime.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyEaten.set(true);
                });

        if (!areThereAnyEaten.get()) System.out.println("During the last time not a single animal has been eaten");
        else System.out.println("During the last day have been eaten: \n" + infoLastTime.append("\n"));
    }

    private void printDiedOfHunger() {
        AtomicBoolean areThereAnyDiedOfHunger = new AtomicBoolean(false);
        StringBuilder infoFromStart = new StringBuilder();

        Statistics.diedOfHungerFromStart.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoFromStart.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyDiedOfHunger.set(true);
                });

        if (!areThereAnyDiedOfHunger.get()) System.out.println("Since the start of the game not a single animal " +
                "has been died of hunger");
        else System.out.println("Since the start of the game have been died of hunger: \n" + infoFromStart.append("\n"));

        areThereAnyDiedOfHunger.set(false);
        StringBuilder infoLastTime = new StringBuilder();

        Statistics.diedOfHungerForLastCycle.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoLastTime.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyDiedOfHunger.set(true);
                });

        if (!areThereAnyDiedOfHunger.get()) System.out.println("During the last time not a single animal has been died " +
                "of hunger");
        else System.out.println("During the last day have been died of hunger: \n" + infoLastTime.append("\n"));
    }

    private void printCountOfBrood() {
        AtomicBoolean areThereAnyBrood = new AtomicBoolean(false);
        StringBuilder infoFromStart = new StringBuilder();

        Statistics.wasBornFromStart.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoFromStart.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyBrood.set(true);
                });

        if (!areThereAnyBrood.get()) System.out.println("Since the start of the game not a single animal " +
                "has been born");
        else System.out.println("Since the start of the game were born: \n" + infoFromStart.append("\n"));

        areThereAnyBrood.set(false);
        StringBuilder infoLastTime = new StringBuilder();

        Statistics.wasBornForLastCycle.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    infoLastTime.append(String.format("%s %s ", ANIMAL_PRINTS.get(entry.getKey()), entry.getValue()));
                    areThereAnyBrood.set(true);
                });

        if (!areThereAnyBrood.get()) System.out.println("During the last time not a single animal were born");
        else System.out.println("During the last day were born: \n" + infoLastTime.append("\n"));
    }

    private AnimalTypeInfo getAnimalTypeInfo(Class<? extends Animal> clazz, String print) {
        AtomicReference<Double> sumSaturationWithFood = new AtomicReference<>(0.0);
        AtomicInteger maleCount = new AtomicInteger(0);
        AtomicInteger femaleCount = new AtomicInteger(0);

        int totalCount = (int) locationService.getLocations().stream()
                .filter(location -> !location.isRiver())
                .map(Location::getAnimals)
                .map(map -> map.get(clazz))
                .flatMap(Collection::stream)
                .peek(animal -> sumSaturationWithFood.getAndUpdate(v -> v + animal.getSaturationWithFood()))
                .peek(animal -> {
                    if(animal.isMale()) maleCount.addAndGet(1);
                    else femaleCount.addAndGet(1);
                })
                .count();

        return AnimalTypeInfo.builder()
                .totalCount(totalCount)
                .male(maleCount.get())
                .female(femaleCount.get())
                .averageSaturationWithFood(totalCount == 0? 0 : (sumSaturationWithFood.get()/totalCount * 100)
                        / Settings.getSettings().getDailyAllowance().get(clazz))
                .print(print)
                .build();
    }

    private String getPlantInfo(String print) {
        return print + " average weight on location " + locationService.getLocations().stream()
                .filter(location -> !location.isRiver())
                .map(Location::getPlant)
                .mapToDouble(Plant::getCurrentWeight)
                .sum() / locationService.getLocations().stream().filter(location -> !location.isRiver()).count();
    }
}
