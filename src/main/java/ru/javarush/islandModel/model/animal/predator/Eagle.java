package ru.javarush.islandModel.model.animal.predator;

import lombok.Builder;
import lombok.Data;

import lombok.EqualsAndHashCode;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.island.Coordinate;

import java.util.concurrent.ThreadLocalRandom;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class Eagle extends Animal implements Predator {
    private static double weight = 6.0;
    private static double dailyAllowance = 1.0;

    private static int count;

    public Eagle() {
        currentWeight = weight;
        isMale = ThreadLocalRandom.current().nextBoolean();
        saturationWithFood = ThreadLocalRandom.current().nextDouble(dailyAllowance) + 0.01;
        count++;
    }

    public static int getCount() {
        return count;
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
    public void eat(Animal animal) {
        currentWeight += animal.getCurrentWeight();
        saturationWithFood += animal.getCurrentWeight();
    }
}
