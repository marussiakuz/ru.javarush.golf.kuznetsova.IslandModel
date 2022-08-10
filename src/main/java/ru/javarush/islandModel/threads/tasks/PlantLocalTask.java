package ru.javarush.islandModel.threads.tasks;

import ru.javarush.islandModel.model.Plant;

public class PlantLocalTask extends LocalTask {
    private final Plant plant;

    public PlantLocalTask(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void execute() {
        plant.grow();
    }
}
