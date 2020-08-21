package cn.ricoco.bridgingpractise;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.ricoco.bridgingpractise.Utils.LevelUtils;

public class PluginTick {
    public static Runner runner;
    public static Thread thread;
    public static void StartTick(){
        runner = new Runner();
        thread = new Thread(runner);
        thread.start();
    }
}
class Runner implements Runnable{
    public void run() {
        String lname=variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"),weatherstr=variable.configjson.getJSONObject("pra").getString("weather");
        int ltime=variable.configjson.getJSONObject("pra").getInteger("time");
        while (true){
            try{
                Thread.sleep(1000);
                Object[] pl=Server.getInstance().getOnlinePlayers().values().toArray();
                int arenac=0;
                if(pl.length==0){continue;}
                for(int i=0;i<pl.length;i++){
                    Player p= (Player) pl[i];
                    if(p.getPosition().getLevel().getName()==lname){
                        arenac++;
                        p.getFoodData().setLevel(20);
                    }
                }
                if(arenac>0){
                    Level l=Server.getInstance().getLevelByName(lname);
                    l.setTime(ltime);
                    LevelUtils.setLevelWeather(l,weatherstr);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
