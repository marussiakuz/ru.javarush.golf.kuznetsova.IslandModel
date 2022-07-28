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
public class Horse extends Animal implements Herbivore, Eatable {
    private static double weight = 400.0;
    private static double dailyAllowance = 60.0;

    private static int count;

    public Horse() {
        currentWeight = weight;
        isMale = ThreadLocalRandom.current().nextBoolean();
        saturationWithFood = ThreadLocalRandom.current().nextDouble(dailyAllowance) + 0.01;
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
        currentWeight *= 0.9;
        if (currentWeight <= weight/2) {
            dieOfHunger();
            return true;
        }
        return false;
    }

    @Override
    public void dieOfHunger() {
        --count;
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
