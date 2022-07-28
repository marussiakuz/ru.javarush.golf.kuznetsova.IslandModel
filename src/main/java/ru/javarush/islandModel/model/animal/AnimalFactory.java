package ru.javarush.islandModel.model.animal;

import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.animal.predator.*;
import ru.javarush.islandModel.model.island.Coordinate;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AnimalFactory {

    public static List<Animal> createAnimalsRandom(Map<Class<? extends Animal>, Integer> maxCountAnimals,
                                                   Coordinate coordinate) {
        List<Animal> animals = new ArrayList<>();

        maxCountAnimals.forEach((key, value) -> animals.addAll(getCertainNumberOfAnimals(key,
                ThreadLocalRandom.current().nextInt(value + 1), coordinate)));

        return animals;
    }

    private static List<Animal> getCertainNumberOfAnimals(Class<? extends Animal> clazz, int count, Coordinate coordinate) {
        return Arrays.stream(new int[count])
                .mapToObj(iteration -> createAnimal(clazz))
                .peek(animal -> animal.setCurrentCoordinate(coordinate))
                .collect(Collectors.toList());
    }

    private static Animal createAnimal(Class<? extends Animal> clazz) {
        Animal animal = null;

        switch (clazz.getSimpleName()) {
            case "Wolf" : animal = new Wolf();
                break;
            case "Python" : animal = new Python();
                break;
            case "Fox" : animal = new Fox();
                break;
            case "Eagle" : animal = new Eagle();
                break;
            case "Bear" : animal = new Bear();
                break;
            case "Sheep" : animal = new Sheep();
                break;
            case "Rabbit" : animal = new Rabbit();
                break;
            case "Mouse" : animal = new Mouse();
                break;
            case "Horse" : animal = new Horse();
                break;
            case "Hog" : animal = new Hog();
                break;
            case "Goat" : animal = new Goat();
                break;
            case "Duck" : animal = new Duck();
                break;
            case "Deer" : animal = new Deer();
                break;
            case "Caterpillar" : animal = new Caterpillar();
                break;
            case "Buffalo" : animal = new Buffalo();
        }

        return animal;
    }

    private static List<Animal> createAnimalWithGender(Animal[] animals) {
        for (Animal animal : animals) {
            System.out.println(animal.getClass().getSimpleName());
        }

        return Arrays.stream(animals)

                .peek(animal -> animal.setMale(ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }

    /*private static Animal initializeAnimal(Animal animal) {

    }*/
}
