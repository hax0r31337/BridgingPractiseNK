package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class PlayerUtils {
    public static void addItemToPlayer(Player player, Item item){
        if(player.getInventory().canAddItem(item)){
            player.getInventory().addItem(item);
        }
    }
}
