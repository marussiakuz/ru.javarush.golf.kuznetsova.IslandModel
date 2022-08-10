package ru.javarush.islandModel.threads;

import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.island.Location;
import ru.javarush.islandModel.service.AnimalService;
import ru.javarush.islandModel.service.LocationService;
import ru.javarush.islandModel.threads.tasks.AnimalLocalTask;
import ru.javarush.islandModel.threads.tasks.LocalTask;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LocationManager implements Runnable {
    private final LocationService locationService;
    private final AnimalService animalService;
    private final Queue<LocalTask> tasks = new ConcurrentLinkedQueue<>();

    public LocationManager(LocationService locationService, AnimalService animalService) {
        this.locationService = locationService;
        this.animalService = animalService;
    }

    @Override
    public void run() {
        locationService.getLocations()
                .forEach(this::setLocalTask);
    }

    private void setLocalTask(Location location) {
        List<Animal> localAnimals = location.getAllAnimals();

        location.getLock().lock();
        try {
            localAnimals.stream()
                    .filter(Objects::nonNull)
                    .forEach(animal -> tasks.add(new AnimalLocalTask(animal, animalService)));
        } finally {
            location.getLock().unlock();
        }

        tasks.forEach(LocalTask::execute);
        tasks.clear();
    }
}
