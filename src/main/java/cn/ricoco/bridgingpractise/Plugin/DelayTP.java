package cn.ricoco.bridgingpractise.Plugin;

import cn.nukkit.Player;
import cn.nukkit.level.Position;

public class DelayTP {
    public TeleportP runner;
    public Thread thread;
    public DelayTP(Player p, Position pos,int delay){
        runner = new TeleportP();
        TeleportP.p=p;
        TeleportP.pos=pos;
        TeleportP.delay=delay;
        thread = new Thread(runner);
        thread.start();
    }
}
class TeleportP implements Runnable{
    public static Position pos;
    public static Player p;
    public static int delay;
    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            p.teleport(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}