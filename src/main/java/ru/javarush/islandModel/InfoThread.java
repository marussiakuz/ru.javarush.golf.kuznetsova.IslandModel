package ru.javarush.islandModel;

import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.herbivore.*;
import ru.javarush.islandModel.model.animal.omnivore.Duck;
import ru.javarush.islandModel.model.animal.omnivore.Hog;
import ru.javarush.islandModel.model.animal.omnivore.Mouse;
import ru.javarush.islandModel.model.animal.predator.*;
import ru.javarush.islandModel.model.island.Island;
import ru.javarush.islandModel.model.island.Location;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class InfoThread extends Thread {
    private Island island;
    private static Map<Class<? extends Animal>, String> animalPrints = new HashMap<>();

    static {
        animalPrints.put(Buffalo.class, "\uD83D\uDC03");
        animalPrints.put(Bear.class, "\uD83D\uDC3B");
        animalPrints.put(Horse.class, "\uD83D\uDC0E");
        animalPrints.put(Deer.class, "\uD83E\uDD8C");
        animalPrints.put(Hog.class, "\uD83D\uDC17");
        animalPrints.put(Sheep.class, "\uD83D\uDC11");
        animalPrints.put(Goat.class, "\uD83D\uDC10");
        animalPrints.put(Wolf.class, "\uD83D\uDC3A");
        animalPrints.put(Python.class, "\uD83D\uDC0D");
        animalPrints.put(Fox.class, "\uD83E\uDD8A");
        animalPrints.put(Eagle.class, "\uD83E\uDD85");
        animalPrints.put(Rabbit.class, "\uD83D\uDC07");
        animalPrints.put(Duck.class, "\uD83E\uDD86");
        animalPrints.put(Mouse.class, "\uD83D\uDC01");
        animalPrints.put(Caterpillar.class, "\uD83D\uDC1B");
    }

    public InfoThread(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (!currentThread().isInterrupted()) {
            animalPrints.entrySet()
                    .forEach(entry -> printInfo(entry.getKey(), getCount(entry.getKey())));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                interrupt();
            }

            System.out.println("__________________________________________________________________________\n");
        }
    }

    private void printInfo(Class<? extends Animal> clazz, int count) {
        AtomicReference<Double> sumSaturationWithFood = new AtomicReference<>(0.0);
        AtomicInteger maleCount = new AtomicInteger(0);
        AtomicInteger femaleCount = new AtomicInteger(0);

        int totalCount = (int) island.getLocations().values().stream()
                .map(Location::getAnimals)
                .flatMap(Collection::stream)
                .filter(animal -> animal.getClass() == clazz)
                .peek(animal -> sumSaturationWithFood.getAndUpdate(v -> v + animal.getSaturationWithFood()))
                .peek(animal -> {
                    if(animal.isMale()) maleCount.addAndGet(1);
                    else femaleCount.addAndGet(1);
                })
                .count();

        /*System.out.println("sumSaturationWithFood/totalCount = " + sumSaturationWithFood.get()/totalCount);
        System.out.println("getDailyAllowance(clazz) " + getDailyAllowance(clazz));*/

        System.out.printf("%s %-6s   Male %-6s Female %-6s   average saturation with food %.1f%% %n", animalPrints.get(clazz),
                count, maleCount.get(), femaleCount.get(),
                clazz.getSimpleName().equals("Caterpillar") ?
                        100.0 : (sumSaturationWithFood.get()/totalCount * 100) / getDailyAllowance(clazz));
    }

    private int getCount(Class<? extends Animal> clazz) {
        int getCount = 0;
        try {
            Field field = clazz.getDeclaredField("count");
            field.setAccessible(true);
            getCount = field.getInt(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return getCount;
    }

    private double getDailyAllowance(Class<? extends Animal> clazz) {
        double dailyAllowance = 0;
        try {
            Field field = clazz.getDeclaredField("dailyAllowance");
            field.setAccessible(true);
            dailyAllowance = field.getDouble(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return dailyAllowance;
    }

}
