package ru.javarush.islandModel.threads;

import ru.javarush.islandModel.utils.Statistics;
import ru.javarush.islandModel.service.AnimalService;
import ru.javarush.islandModel.service.LocationService;
import ru.javarush.islandModel.settings.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IslandManager extends Thread {
    private final Statistics statistics;
    private final LocationService locationService;
    private final AnimalService animalService;
    private static final long DURING_OF_CYCLE = Settings.getSettings().getDuringOfCycle();
    private static final int CORE_POOL_SIZE = Settings.getSettings().getCorePoolSize();

    public IslandManager(LocationService locationService, AnimalService animalService) {
        this.locationService = locationService;
        this.animalService = animalService;
        statistics = new Statistics(locationService);
    }

    @Override
    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        executorService.scheduleWithFixedDelay(this::executeLocationManager, 0L,
                DURING_OF_CYCLE, TimeUnit.MILLISECONDS);
    }

    private void executeLocationManager() {
        statistics.printStatistics();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new LocationManager(locationService, animalService));
    }
}
