package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.EntityEventPacket;

public class EntityUtils {
    public static void displayHurt(Player e){
        EntityEventPacket pk = new EntityEventPacket();
        pk.eid = e.getId();
        pk.event = EntityEventPacket.HURT_ANIMATION;
        e.getLevel().getPlayers().values().forEach((player -> player.dataPacket(pk)));
    }
}
