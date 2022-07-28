package ru.javarush.islandModel;

import lombok.Data;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.island.Island;
import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.predator.*;

import java.beans.JavaBean;
import java.util.HashMap;
import java.util.Map;

@Data
@JavaBean
public class Settings {
    private Island island;
    private static Map<Class<? extends Animal>, Integer> maxCountAnimals = new HashMap<>();
    private static int maxCountPlants = 200;
    private static long durationOfCycle = 10_000_000L;

    static {
        maxCountAnimals.put(Wolf.class, 30);
        maxCountAnimals.put(Buffalo.class, 10);
        maxCountAnimals.put(Python.class, 30);
        maxCountAnimals.put(Fox.class, 30);
        maxCountAnimals.put(Bear.class, 5);
        maxCountAnimals.put(Eagle.class, 20);
        maxCountAnimals.put(Horse.class, 20);
        maxCountAnimals.put(Deer.class, 20);
        maxCountAnimals.put(Rabbit.class, 150);
        maxCountAnimals.put(Mouse.class, 500);
        maxCountAnimals.put(Goat.class, 140);
        maxCountAnimals.put(Sheep.class, 140);
        maxCountAnimals.put(Hog.class, 50);
        maxCountAnimals.put(Duck.class, 200);
        maxCountAnimals.put(Caterpillar.class, 1000);
    }



    public static Map<Class<? extends Animal>, Integer> getMaxCountAnimals() {
        return maxCountAnimals;
    }

    public static int getMaxCountPlants() {
        return maxCountPlants;
    }

    public static long getDurationOfCycle() {
        return durationOfCycle;
    }
}
