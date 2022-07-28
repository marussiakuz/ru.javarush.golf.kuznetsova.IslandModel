package ru.javarush.islandModel.model.animal.predator;

import lombok.Data;

import lombok.EqualsAndHashCode;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.island.Coordinate;

import java.util.concurrent.ThreadLocalRandom;

@EqualsAndHashCode(callSuper = true)
@Data
public class Wolf extends Animal implements Predator {
    private static double weight = 50.0;
    private static double dailyAllowance = 8.0;

    private static int count;

    public Wolf() {
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
        this.currentCoordinate = coordinate;
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

    public static double getWeight() {
        return weight;
    }

    public static double getDailyAllowance() {
        return dailyAllowance;
    }
}
