package ru.javarush.islandModel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.javarush.islandModel.model.action.ActionThread;
import ru.javarush.islandModel.model.island.Island;
import ru.javarush.islandModel.model.island.Location;

@Component
public class Life implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        /*Island island = new Island(7, 8);

        island.getRiver().getCoordinates().stream()
                .forEach(System.out::println);

        Location location = island.getLocations().values().stream()
                .findFirst()
                .get();

        ActionThread thread = new ActionThread(location.getAnimals().get(0), island);
        thread.start();*/
    }

    public static void main(String[] args) {
        Island island = new Island(7, 8);

        island.getRiver().getCoordinates().stream()
                .forEach(System.out::println);

        Location location = island.getLocations().values().stream()
                .findFirst()
                .get();

        ActionThread thread = new ActionThread(location.getAnimals().get(0), island);
        InfoThread infoThread = new InfoThread(island);
        //infoThread.setDaemon(true);
        infoThread.start();
        thread.start();
    }
}
