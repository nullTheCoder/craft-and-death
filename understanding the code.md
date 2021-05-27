#Some important files

###Main.java
Responsible for being the main file which loads everything and also for managing advanced players.

###AdvancedPlayer.java
Bound to every player through a hashmap
Contains values like thirst and temperature

#Mechanics and how to find them

###Recipe book
I don't know how it works or exists, I wrote it long ago and it is based of chestUI

###Custom items
Work by inserting id in to the last line of lore.
When ever you register a new item it will check if the file implements any of the following classes:
OreAlloy
ArmourItem
ConsumableItem
UsableItem

if it does then it will register that item in one of the following managers:

AlloyManager (OreAlloy)
DamageManager (ArmourItem)
ItemEventManager (consumableItem, UsableItem) 

###Defence system
Defence system has been rewritten.



Hopefully this file helped you understand what is happening just a bit better