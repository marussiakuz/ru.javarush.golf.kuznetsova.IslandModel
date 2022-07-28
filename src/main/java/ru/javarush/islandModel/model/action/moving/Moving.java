package ru.javarush.islandModel.model.action.moving;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.animal.predator.*;
import ru.javarush.islandModel.model.island.Coordinate;
import ru.javarush.islandModel.model.island.Island;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
public class Moving {

    private static Map<Class<? extends Animal>, Integer> maxCountOfSteps;

    static {
        maxCountOfSteps = new HashMap<>();
        maxCountOfSteps.put(Wolf.class, 3);
        maxCountOfSteps.put(Python.class, 1);
        maxCountOfSteps.put(Fox.class, 2);
        maxCountOfSteps.put(Bear.class, 2);
        maxCountOfSteps.put(Eagle.class, 3);
        maxCountOfSteps.put(Horse.class, 4);
        maxCountOfSteps.put(Deer.class, 4);
        maxCountOfSteps.put(Rabbit.class, 2);
        maxCountOfSteps.put(Mouse.class, 1);
        maxCountOfSteps.put(Goat.class, 3);
        maxCountOfSteps.put(Sheep.class, 3);
        maxCountOfSteps.put(Hog.class, 2);
        maxCountOfSteps.put(Buffalo.class, 3);
        maxCountOfSteps.put(Duck.class, 4);
    }

    public static void move(Animal animal, Island island) {
        if (animal instanceof Caterpillar) return;
        System.out.println(String.format("starting coordinate of %s - %s", animal.getClass().getSimpleName(), animal.getCurrentCoordinate()));

        Arrays.stream(new int[ThreadLocalRandom.current().nextInt((getCountOfSteps(animal)) + 1)])
                .forEach(iteration -> animal.setCurrentCoordinate(chooseNextStep(animal.getCurrentCoordinate(), island)));

        island.getLocations().get(animal.getCurrentCoordinate()).getAnimals().add(animal);

        System.out.println(String.format("now %s is on %s", animal.getClass().getSimpleName(), animal.getCurrentCoordinate()));
    }

    private static int getCountOfSteps(Animal animal) {
        return ThreadLocalRandom.current().nextInt(maxCountOfSteps.get(animal.getClass())) + 1;
    }

    private static Coordinate chooseNextStep(Coordinate coordinate, Island island) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int nextX;
        int nextY;

        boolean doesGoHorizontally = ThreadLocalRandom.current().nextBoolean();
        if(doesGoHorizontally) {
            boolean doesGoToTheRight = !isRiverOnTheRight(coordinate, island) && (ThreadLocalRandom.current().nextBoolean()
                    || (isRiverOnTheLeft(coordinate, island)));
            if(doesGoToTheRight) {
                nextX = x + 1 == island.getWidth() ? 0 : x + 1;
            } else {
                nextX = x - 1 < 0 ? island.getWidth() - 1 : x - 1;
            }
            nextY = y;
        } else {
            boolean doesGoUp = !isRiverUp(coordinate, island) && (ThreadLocalRandom.current().nextBoolean()
                    || (isRiverDown(coordinate, island)));
            if(doesGoUp) {
                nextY = y - 1 < 0 ? island.getLength() - 1 : y - 1;
            } else {
                nextY = y + 1 == island.getLength() ? 0 : y + 1;
            }
            nextX = x;
        }

        return new Coordinate(nextX, nextY);
    }

    private static boolean isRiverOnTheRight(Coordinate coordinate, Island island) {
        if (coordinate.getX() >= island.getWidth() - 2) return false;
        return island.getRiver().getCoordinates().contains(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
    }

    private static boolean isRiverOnTheLeft(Coordinate coordinate, Island island) {
        if (coordinate.getY() <= 1) return false;
        return island.getRiver().getCoordinates().contains(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
    }

    private static boolean isRiverUp(Coordinate coordinate, Island island) {
        if (coordinate.getY() <= 1) return false;
        return island.getRiver().getCoordinates().contains(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
    }

    private static boolean isRiverDown(Coordinate coordinate, Island island) {
        if (coordinate.getY() >= island.getLength() - 2) return false;
        return island.getRiver().getCoordinates().contains(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
    }
}
