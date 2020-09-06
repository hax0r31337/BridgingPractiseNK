package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.ricoco.bridgingpractise.Plugin.ClearBlocks;
import cn.ricoco.bridgingpractise.Plugin.DelayTP;
import cn.ricoco.bridgingpractise.variable;

import java.util.HashMap;
import java.util.Map;

public class PlayerUtils {
    public static void addItemToPlayer(Player player, Item item){
        if(player.getInventory().canAddItem(item)){
            player.getInventory().addItem(item);
        }
    }
    public static void ClearBL(Player p,Boolean repb){
        p.teleport(variable.playerresp.get(p.getName()));
        int ble=variable.blocklength.remove(p.getName());
        if(repb){
            LevelUtils.replaceBl(variable.blockpos.get(p.getName()),ble);
        }
        new ClearBlocks(variable.blockpos.remove(p.getName()),ble,variable.configjson.getJSONObject("pra").getBoolean("instabreak"));
        Map<Integer, Position> m=new HashMap<>();
        if(variable.blockmax.get(p.getName())<ble){
            variable.blockmax.put(p.getName(),ble);
        }
        variable.blockpos.put(p.getName(),m);
        variable.blocklength.put(p.getName(),0);
        variable.playeronresp.put(p.getName(),true);
    }
}
