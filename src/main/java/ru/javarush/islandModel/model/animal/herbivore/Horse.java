package ru.javarush.islandModel.model.animal.herbivore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.settings.Settings;

import java.util.concurrent.ThreadLocalRandom;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Horse extends Animal implements Herbivore, Eatable {
    private static double weightAvg = Settings.getSettings().getWeightAvg().get(Horse.class);
    private static double dailyAllowance = Settings.getSettings().getDailyAllowance().get(Horse.class);

    public Horse() {
        currentWeight = weightAvg;
        isMale = ThreadLocalRandom.current().nextBoolean();
        saturationWithFood = ThreadLocalRandom.current().nextDouble(dailyAllowance);
    }

    @Override
    public boolean isHungry() {
        return saturationWithFood < dailyAllowance;
    }

    @Override
    public boolean isExhausted() {
        return currentWeight <= weightAvg / 2;
    }
}
