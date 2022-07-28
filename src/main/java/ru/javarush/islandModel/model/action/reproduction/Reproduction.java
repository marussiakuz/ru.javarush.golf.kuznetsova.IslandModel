package ru.javarush.islandModel.model.action.reproduction;

import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.animal.predator.*;
import ru.javarush.islandModel.model.island.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Reproduction {

    private static final Map<Class<? extends Animal>, Integer> maxCountBrood = new HashMap<>();

    static {
        maxCountBrood.put(Wolf.class, 6);
        maxCountBrood.put(Python.class, 3);
        maxCountBrood.put(Fox.class, 6);
        maxCountBrood.put(Eagle.class, 8);
        maxCountBrood.put(Bear.class, 3);
        maxCountBrood.put(Sheep.class, 3);
        maxCountBrood.put(Rabbit.class, 6);
        maxCountBrood.put(Mouse.class, 50);
        maxCountBrood.put(Horse.class, 1);
        maxCountBrood.put(Hog.class, 3);
        maxCountBrood.put(Goat.class, 4);
        maxCountBrood.put(Duck.class, 20);
        maxCountBrood.put(Deer.class, 3);
        maxCountBrood.put(Caterpillar.class, 40);
        maxCountBrood.put(Buffalo.class, 3);
    }

    public static boolean isReproductionPossible(Animal animal, Location location) {
        /*return location.getAnimals().stream()
                .filter(localAnimal -> localAnimal.getClass() == animal.getClass())
                .anyMatch(localAnimal -> animal.isMale() != animal.isMale());*/
        boolean is = location.getAnimals().stream()
                .filter(localAnimal -> localAnimal.getClass() == animal.getClass())
                .anyMatch(localAnimal -> animal.isMale() != animal.isMale());

        if (is) System.out.println(String.format("%s has someone to mate with", animal.getClass().getSimpleName()));
        else System.out.println(String.format("%s has not anyone to mate with", animal.getClass().getSimpleName()));
        return is;
    }

    public static boolean reproduceIfPossible(Animal animal, Location location) {
        if (isReproductionPossible(animal, location)) return false;
        if (getCurrentCount(animal, location) == getMaxCount(animal)) return false;
        List<Animal> updated = location.getAnimals();
        //updated.addAll(getBrood(animal));
        List<Animal> newBrood = getBrood(animal);
        System.out.println(String.format("Brood is %s count", newBrood.size()));
        updated.addAll(newBrood);

        location.setAnimals(updated);
        animal.starve();
        return true;
    }

    private static int getCurrentCount(Animal animal, Location location) {
        return (int) location.getAnimals().stream()
                .filter(localAnimal -> localAnimal.getClass() == animal.getClass())
                .count();
    }

    private static List<Animal> getBrood(Animal animal) {
        return Arrays.stream(new int[ThreadLocalRandom.current().nextInt((getMaxCount(animal)) + 1)])
                .mapToObj(iteration -> getNewAnimal(animal))
                .peek(newAnimal -> newAnimal.setCurrentCoordinate(animal.getCurrentCoordinate()))
                .peek(newAnimal -> newAnimal.setMale(ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }

    private static Animal getNewAnimal(Animal animal) {
        Animal newAnimal = null;
        Constructor<?> constructor = animal.getClass().getDeclaredConstructors()[0];
        try {
            newAnimal = (Animal) constructor.newInstance(new Object[constructor.getParameterCount()]);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return newAnimal;
    }

    private static int getMaxCount(Animal animal) {
        return maxCountBrood.get(animal.getClass());
    }
}
