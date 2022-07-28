package ru.javarush.islandModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Plant {
    private double currentWeight;

    public void grow() {
        if (currentWeight + 1 != 200) {
            ++currentWeight;
        }
    }
}
