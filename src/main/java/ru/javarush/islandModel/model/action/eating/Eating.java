package ru.javarush.islandModel.model.action.eating;

import ru.javarush.islandModel.Plant;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.animal.predator.*;
import ru.javarush.islandModel.model.island.Location;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Eating {
    private static final Map<Class<? extends Predator>, Map<Class<? extends Eatable>, Integer>> FOOD_PREFERENCES;
    private static final Map<Class<? extends Animal>, Double> DAILY_ALLOWANCE;

    static {
        FOOD_PREFERENCES = new HashMap<>();
        FOOD_PREFERENCES.put(Wolf.class, Map.of(Horse.class, 10, Deer.class, 15, Rabbit.class, 60,
                Mouse.class, 80, Goat.class, 60, Sheep.class, 70, Hog.class, 15, Buffalo.class, 10,
                Duck.class, 40));
        FOOD_PREFERENCES.put(Python.class, Map.of(Fox.class, 15, Rabbit.class, 20, Mouse.class, 40,
                Duck.class, 10));
        FOOD_PREFERENCES.put(Fox.class, Map.of(Rabbit.class, 70, Mouse.class, 90, Duck.class, 60,
                Caterpillar.class, 40));
        FOOD_PREFERENCES.put(Bear.class, Map.of(Python.class, 80, Horse.class, 40, Deer.class, 80,
                Rabbit.class, 80, Mouse.class, 90, Goat.class, 70, Sheep.class, 70, Hog.class, 50,
                Buffalo.class, 20, Duck.class, 10));
        FOOD_PREFERENCES.put(Eagle.class, Map.of(Fox.class, 10, Rabbit.class, 90, Mouse.class, 90,
                Duck.class, 80));
        FOOD_PREFERENCES.put(Mouse.class, Map.of(Caterpillar.class, 90));
        FOOD_PREFERENCES.put(Hog.class, Map.of(Caterpillar.class, 90, Mouse.class, 50));
        FOOD_PREFERENCES.put(Duck.class, Map.of(Caterpillar.class, 90));

        DAILY_ALLOWANCE = new HashMap<>();
        DAILY_ALLOWANCE.put(Wolf.class, 8.0);
        DAILY_ALLOWANCE.put(Python.class, 3.0);
        DAILY_ALLOWANCE.put(Fox.class, 2.0);
        DAILY_ALLOWANCE.put(Bear.class, 80.0);
        DAILY_ALLOWANCE.put(Eagle.class, 1.0);
        DAILY_ALLOWANCE.put(Horse.class, 60.0);
        DAILY_ALLOWANCE.put(Deer.class, 50.0);
        DAILY_ALLOWANCE.put(Rabbit.class, 0.45);
        DAILY_ALLOWANCE.put(Mouse.class, 0.01);
        DAILY_ALLOWANCE.put(Goat.class, 10.0);
        DAILY_ALLOWANCE.put(Sheep.class, 15.0);
        DAILY_ALLOWANCE.put(Hog.class, 50.0);
        DAILY_ALLOWANCE.put(Buffalo.class, 100.0);
        DAILY_ALLOWANCE.put(Duck.class, 0.15);
        DAILY_ALLOWANCE.put(Caterpillar.class, 0.0);
    }

    public static boolean isHungry(Animal animal) {
        //return animal.getSaturationWithFood() < DAILY_ALLOWANCE.get(animal.getClass());
        boolean is = animal.getSaturationWithFood() < DAILY_ALLOWANCE.get(animal.getClass());
        if (is) System.out.println(String.format("%s isn't hungry", animal.getClass().getSimpleName()));
        else System.out.println(String.format("%s is hungry", animal.getClass().getSimpleName()));
        return is;
    }

    public static boolean isAnyoneToEat(Predator predator, Location currentLocation) {
        //return !getEatableAnimals(predator, currentLocation.getAllEatable()).isEmpty();
        boolean has = !getEatableAnimals(predator, currentLocation.getAllEatable()).isEmpty();
        if (has) System.out.println("has somebody to eat");
        else System.out.println("has not anybody to eat");
        return has;
    }

    public static boolean tryNotToBeEaten(Eatable eatable, Location currentLocation) {
        List<Predator> localDangerousAnimals = getDangerousAnimals(eatable, currentLocation.getAllPredators());

        if (localDangerousAnimals.isEmpty()) {
            System.out.println("no dangerous to be eaten");
            return true;
        }

        /*return localDangerousAnimals.stream()
                .noneMatch(predator -> tryToEatOne(predator, eatable));*/
        boolean eaten = localDangerousAnimals.stream()
                .noneMatch(predator -> tryToEatOne(predator, eatable));
        if (eaten) System.out.println(String.format("no one could eat %s", eatable.getClass().getSimpleName()));
        else System.out.println(String.format("%s be eaten", eatable.getClass().getSimpleName()));
        return eaten;
    }

    public static void eatPlants(Herbivore herbivore, Location currentLocation) {
        Plant plant = currentLocation.getPlant();

        double toEat = DAILY_ALLOWANCE.get(herbivore.getClass()) - ((Animal) herbivore).getSaturationWithFood();
        System.out.println(String.format("need to eat %s kg plants", toEat));

        if (plant.getCurrentWeight() < toEat) toEat = plant.getCurrentWeight();

        currentLocation.removePlant(plant.getCurrentWeight() - toEat);
        ((Animal) herbivore).setCurrentWeight(((Animal) herbivore).getCurrentWeight() + toEat);
        ((Animal) herbivore).setSaturationWithFood(((Animal) herbivore).getSaturationWithFood() + toEat);
    }

    public static boolean tryToEatAll(Predator predator, Location currentLocation) {
        List<Eatable> localEatableAnimals = getEatableAnimals(predator, currentLocation.getAllEatable());
        
        if (localEatableAnimals.isEmpty()) {
            System.out.println("no eatables to eat");
            return false;
        }
        
        /*return localEatableAnimals.stream()
                .anyMatch(eatable -> tryToEatOne(predator, eatable));*/
        boolean eat = localEatableAnimals.stream()
                .anyMatch(eatable -> tryToEatOne(predator, eatable));
        if (eat) System.out.println(String.format("%s ate eayable", predator.getClass().getSimpleName()));
        return eat;
    }

    private static boolean tryToEatOne(Predator predator, Eatable eatable) {
        boolean beEaten = ThreadLocalRandom.current().nextInt(100) + 1 <= getProbability(predator, eatable);

        if (beEaten) {
            predator.eat((Animal) eatable);
            eatable.beEaten();
            System.out.println(String.format("%s be eaten by %s", eatable.getClass().getSimpleName(), predator.getClass().getSimpleName()));

            eatable = null;
        } else {
            ((Animal) predator).starve();
            ((Animal) eatable).starve();
        }

        return beEaten;
    }

    private static List<Predator> getDangerousAnimals(Eatable eatable, List<Predator> allLocalPredators) {
        return allLocalPredators.stream()
                .filter(predator -> getDangerousClasses(eatable).contains(predator.getClass()))
                .collect(Collectors.toList());
    }

    private static List<Eatable> getEatableAnimals(Predator predator, List<Eatable> allLocalEatables) {
        return allLocalEatables.stream()
                .filter(eatable -> getEatableClasses(predator).contains(eatable.getClass()))
                .collect(Collectors.toList());
    }

    private static List<Class<? extends Predator>> getDangerousClasses(Eatable eatable) {
        return FOOD_PREFERENCES.entrySet().stream()
                        .filter(entrySet -> entrySet.getValue().containsKey(eatable.getClass()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
    }

    private static List<Class<? extends Eatable>> getEatableClasses(Predator predator) {
        return new ArrayList<>(FOOD_PREFERENCES.get(predator.getClass()).keySet());
    }

    private static int getProbability(Predator predator, Eatable eatable) {
        return FOOD_PREFERENCES.get(predator.getClass()).get(eatable.getClass());
    }
}
