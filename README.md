ru.javarush.golf.kuznetsova.IslandModel
# IslandModel

## The game implements the following functionality: 

1. Animals can choose any of the 8 directions (north, north-east, east, south-east, south, south-west, west, north-west),
   they cannot cross the river. Each step is chosen randomly depending on the available locations, but they cannot return 
   to previous locations within one cycle.
2. Animals eat only if they are hungry, and try to eat until they are full or have exhausted their attempts to eat.
3. Starvation increases with every step taken and unsuccessful attempts to eat. Fasting is accompanied by weight loss 
   and decreased satiety. Percent weight loss and percent reduced satiety can be set in the config.yaml
   The animal becomes exhausted with a weight loss of more than 50% and dies of hunger.
4. Animals can reproduce only if they are not hungry, there is a couple of the opposite sex and the couple also should 
   not be hungry.


You can change some settings in the config.yaml, including specifying the end condition of the game, such as the critical 
number of extinct animal species. The game will end anyway if all the animals die out.

You can also turn on or off the printing of statistics by location in the config.yaml.

The approximate data output looks like this:


