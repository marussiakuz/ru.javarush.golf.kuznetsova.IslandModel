package ru.javarush.islandModel.model.island;

import lombok.Data;
import ru.javarush.islandModel.Plant;
import ru.javarush.islandModel.model.animal.AnimalFactory;
import ru.javarush.islandModel.Settings;
import ru.javarush.islandModel.model.animal.Animal;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Island {
    private Map<Coordinate, Location> locations;
    private boolean hasRiver;
    private int width;
    private int length;
    private River river;

    public Island(int width, int length) {
        this.width = width;
        this.length = length;
        this.locations = initializeArea(width, length);
        if (width > 1 && length > 2 || width > 2 && length > 1) {
            hasRiver = true;
            this.river = initializeRiver(width, length);
        }
        populateWithAnimalsAndPlants(locations, Settings.getMaxCountPlants(), Settings.getMaxCountAnimals());
    }

    private River initializeRiver(int width, int length) {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int i = 0; i < Math.min(length, width); i++) {
            locations.get(new Coordinate(i, Math.max(width, length)/2));
        }
        return new River(coordinates);
    }

    private Map<Coordinate, Location> initializeArea(int width, int length) {
        Map<Coordinate, Location> locations = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                locations.put(new Coordinate(i, j), new Location());
            }
        }
        if (hasRiver) river.getCoordinates().forEach(coordinate -> locations.get(coordinate).setRiver(true));
        return locations;
    }

    private void populateWithAnimalsAndPlants(Map<Coordinate, Location> locations, double maxCountPlants,
                                              Map<Class<? extends Animal>, Integer> maxCountAnimals) {
        locations.entrySet().stream()
                .filter(entry -> !entry.getValue().isRiver())
                .peek(entry -> entry.getValue().setPlant(new Plant(ThreadLocalRandom
                        .current()
                        .nextDouble((maxCountPlants) + 1.0))))
                .forEach(entry -> entry.getValue().setAnimals(AnimalFactory
                        .createAnimalsRandom(maxCountAnimals, entry.getKey())));
    }

    private void setCoordinate(List<Animal> animals, Coordinate coordinate) {
        animals.forEach(animal -> animal.setCurrentCoordinate(coordinate));
    }
}
