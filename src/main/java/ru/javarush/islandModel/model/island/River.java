package ru.javarush.islandModel.model.island;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class River {
    private Set<Coordinate> coordinates;
}
