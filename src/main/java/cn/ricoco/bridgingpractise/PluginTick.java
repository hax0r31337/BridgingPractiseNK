package cn.ricoco.bridgingpractise;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.ricoco.bridgingpractise.Utils.ExpUtils;
import cn.ricoco.bridgingpractise.Utils.LevelUtils;
import com.alibaba.fastjson.JSONObject;

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
    public int tick=0;
    public String[] sbTitle=variable.langjson.getString("sbtitle").split("");
    public int sbTitleCount=0;
    public void run() {
        String promptstr=variable.langjson.getString("prompt"),lname=variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"),weatherstr=variable.configjson.getJSONObject("pra").getString("weather");
        int ltime=variable.configjson.getJSONObject("pra").getInteger("time");
        Boolean prompt=variable.configjson.getJSONObject("pra").getBoolean("prompt");
        Boolean expSystem=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("enable");
        Boolean lvUp=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("levelup");
        Boolean expTip=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("getexp");
        Boolean timeEarn=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getBoolean("enable");
        int timeEarnC=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getInteger("sec");
        int timeEarnE=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getInteger("exp");
        while (true){
            try{
                Thread.sleep(500);
                tick++;
                Object[] pl=Server.getInstance().getOnlinePlayers().values().toArray();
                int arenac=0;
                if(pl.length==0){continue;}
                for (Object o : pl) {
                    Player p = (Player) o;
                    if (p.getPosition().getLevel().getName().equals(lname)) {
                        if(tick>=1) {
                            arenac++;
                            p.getFoodData().setLevel(20);
                            if(expSystem){
                                JSONObject plj=variable.playerLevelJSON.get(p.getName());
                                if(timeEarn) {
                                    variable.playerTime.put(p.getName(), variable.playerTime.get(p.getName()) + 1);
                                    if (variable.playerTime.get(p.getName())>=timeEarnC){
                                        variable.playerTime.put(p.getName(), 0);
                                        plj=ExpUtils.addExp(plj,timeEarnE,expTip,lvUp,"timeearn",p);
                                        variable.playerLevelJSON.put(p.getName(),plj);
                                    }
                                }
                                p.setExperience(plj.getInteger("exp"),plj.getInteger("level"));
                            }else{
                                p.setExperience(0);
                            }
                            if (prompt) {
                                p.sendPopup(promptstr.replaceAll("%1", variable.blocksecond.get(p.getName()) + "").replaceAll("%2", variable.blocklength.get(p.getName()) + "").replaceAll("%3", variable.blockmax.get(p.getName()) + ""));
                            }
                            variable.blocksecond.put(p.getName(), 0);
                        }
                    }
                }
                if(arenac>0&&tick>=1){
                    tick=0;
                    Level l=Server.getInstance().getLevelByName(lname);
                    l.setTime(ltime);
                    LevelUtils.setLevelWeather(l,weatherstr);
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
