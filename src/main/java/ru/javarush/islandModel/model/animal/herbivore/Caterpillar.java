package ru.javarush.islandModel.model.animal.herbivore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.javarush.islandModel.Plant;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.island.Coordinate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class Caterpillar extends Animal implements Herbivore, Eatable {
    private static double weight = 0.01;
    private static double dailyAllowance = 0.0;

    private static int count;

    public Caterpillar() {
        isMale = ThreadLocalRandom.current().nextBoolean();
        count++;
    }

    @Override
    public void move(Coordinate coordinate) {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public boolean starve() {
        return false;
    }

    @Override
    public void dieOfHunger() {

    }

    @Override
    public void beEaten() {
        --count;
    }

    @Override
    public void eat(Plant plant) {
        double toEat = dailyAllowance - saturationWithFood;

        if (plant.getCurrentWeight() < toEat) toEat = plant.getCurrentWeight();

        plant.setCurrentWeight(plant.getCurrentWeight() - toEat);
        currentWeight += toEat;
        saturationWithFood += toEat;
    }
}
