package ru.javarush.islandModel.model.action;

import ru.javarush.islandModel.model.action.eating.Eating;
import ru.javarush.islandModel.model.action.moving.Moving;
import ru.javarush.islandModel.model.action.reproduction.Reproduction;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.animal.herbivore.Herbivore;
import ru.javarush.islandModel.model.animal.predator.Predator;
import ru.javarush.islandModel.model.island.Island;
import ru.javarush.islandModel.model.island.Location;

public class ActionThread extends Thread {
    private Animal animal;
    private Island island;

    public ActionThread(Animal animal, Island island) {
        this.animal = animal;
        this.island = island;
    }

    @Override
    public void run() {
        Location currentLocation = island.getLocations().get(animal.getCurrentCoordinate());

        while (!currentThread().isInterrupted()) {
            Moving.move(animal, island);

            if (animal instanceof Eatable) {
                if(!Eating.tryNotToBeEaten((Eatable) animal, currentLocation))
                    currentThread().interrupt();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
                continue;
            }

            if (animal != null && animal instanceof Predator && Eating.isHungry(animal)
                    && Eating.isAnyoneToEat((Predator) animal, currentLocation)) {
                Eating.tryToEatAll((Predator) animal, currentLocation);
            }

            //assert animal != null;
            if (animal != null && animal instanceof Herbivore && Eating.isHungry(animal)) {
                Eating.eatPlants((Herbivore) animal, currentLocation);
            }

            //assert animal != null;
            if (animal != null && !Eating.isHungry(animal) && Reproduction.isReproductionPossible(animal, currentLocation)) {
                Reproduction.reproduceIfPossible(animal, currentLocation);
            }
        }
    }
}
