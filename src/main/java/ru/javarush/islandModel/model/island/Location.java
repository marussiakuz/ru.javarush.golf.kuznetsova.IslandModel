package ru.javarush.islandModel.model.island;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javarush.islandModel.Plant;
import ru.javarush.islandModel.model.animal.Animal;
import ru.javarush.islandModel.model.animal.Eatable;
import ru.javarush.islandModel.model.animal.predator.Predator;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    private Coordinate coordinate;
    private List<Animal> animals;
    private boolean isRiver;
    private Plant plant;

    public boolean hasAnyPredator() {
        return animals.stream()
                .anyMatch(animal -> animal instanceof Predator);
    }

    public boolean hasAnyEatable() {
        return animals.stream()
                .anyMatch(animal -> animal instanceof Eatable);
    }

    public boolean hasSomePlant() {
        return plant.getCurrentWeight() > 0.0;
    }

    public List<Predator> getAllPredators() {
        return animals.stream()
                .filter(animal -> animal instanceof Predator)
                .map(animal -> (Predator) animal)
                .collect(Collectors.toList());
    }

    public List<Eatable> getAllEatable() {
        return animals.stream()
                .filter(animal -> animal instanceof Eatable)
                .map(animal -> (Eatable) animal)
                .collect(Collectors.toList());
    }

    public boolean addAnimal(Animal animal) {
        return animals.add(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public boolean addPlant(double weight) {
        if (plant.getCurrentWeight() + weight > 200) return false;
        plant.setCurrentWeight(plant.getCurrentWeight() + weight);
        return true;
    }

    public boolean removePlant(double weight) {
        if (plant.getCurrentWeight() - weight < 0) return false;
        plant.setCurrentWeight(plant.getCurrentWeight() - weight);
        return true;
    }
}
