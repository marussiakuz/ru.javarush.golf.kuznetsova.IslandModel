package ru.javarush.islandModel.model.animal;

import lombok.*;
import ru.javarush.islandModel.model.island.Coordinate;

@NoArgsConstructor
@Data
@ToString
public abstract class Animal {
    protected double currentWeight;
    protected boolean isMale;
    protected double saturationWithFood;
    protected Coordinate currentCoordinate;

    public abstract boolean starve();

    public abstract void move(Coordinate coordinate); // система координат + enum Direction

    public abstract void reproduce();

    public abstract void dieOfHunger();
}
