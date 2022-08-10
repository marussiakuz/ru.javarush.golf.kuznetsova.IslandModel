package ru.javarush.islandModel.model;

import lombok.Builder;

@Builder
public class AnimalTypeInfo {
    private String print;
    private int totalCount;
    private int male;
    private int female;
    private double averageSaturationWithFood;

    @Override
    public String toString() {
        return String.format("%s %-6s   Male %-6s Female %-6s   average saturation with food %.1f%%", print,
                totalCount, male, female, averageSaturationWithFood);
    }
}
