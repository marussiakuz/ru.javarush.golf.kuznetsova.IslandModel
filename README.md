ru.javarush.golf.kuznetsova.IslandModel
# IslandModel

## The game implements the following functionality: 

1. Animals can choose any of the 8 directions (north, north-east, east, south-east, south, south-west, west, north-west),
   they cannot cross the river. Each step is chosen randomly depending on the available locations, but they cannot return 
   to previous locations within one cycle.
2. Animals eat only if they are hungry, and try to eat until they are full or have exhausted their attempts to eat.
3. Starvation increases with every step taken and unsuccessful attempts to eat. Fasting is accompanied by weight loss 
   and decreased satiety. Percent weight loss and percent reduced satiety can be set in the config.yaml. 
   The animal becomes exhausted with a weight loss of more than 50% and dies of hunger.
4. Animals can reproduce only if they are not hungry, there is a couple of the opposite sex and the couple also should 
   not be hungry.


You can change some settings in the config.yaml, including specifying the end condition of the game, such as the critical 
number of extinct animal species. The game will end anyway if all the animals die out.

You can also turn on or off the printing of statistics by location in the config.yaml.

The approximate data output looks like this:

<img width="600" alt="Снимок экрана 2022-08-13 в 22 39 59" src="https://user-images.githubusercontent.com/96682553/184510187-3e397d62-5a87-421c-92f5-6531f56f9d14.png">

If the output of statistics for each location is enabled:

<img width="944" alt="Снимок экрана 2022-08-13 в 22 43 20" src="https://user-images.githubusercontent.com/96682553/184510309-bfd8cfa0-fa54-41b3-a730-a6b0cef37631.png">

End of the game:

<img width="861" alt="Снимок экрана 2022-08-13 в 22 42 37" src="https://user-images.githubusercontent.com/96682553/184510276-f832b97f-7f3c-4c62-a273-40e030de296f.png">
