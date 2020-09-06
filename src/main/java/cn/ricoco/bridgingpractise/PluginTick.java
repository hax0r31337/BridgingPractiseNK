package cn.ricoco.bridgingpractise;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.ricoco.bridgingpractise.Utils.ExpUtils;
import cn.ricoco.bridgingpractise.Utils.LevelUtils;
import cn.ricoco.bridgingpractise.Utils.ScoreboardUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

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
    public void run() {
        String promptstr=variable.langjson.getString("prompt"),lname=variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"),weatherstr=variable.configjson.getJSONObject("pra").getString("weather");
        int ltime=variable.configjson.getJSONObject("pra").getInteger("time");
        Boolean prompt=variable.configjson.getJSONObject("pra").getBoolean("prompt");
        Boolean expSystem=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("enable");
        Boolean lvUp=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("levelup");
        Boolean expTip=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("getexp");
        Boolean scoreb=variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("scoreboard");
        int SBCount=0;
        String sbTitle=variable.langjson.getString("sbtitle");
        String[] sbTitleL=variable.langjson.getString("sbtitle").split("");
        JSONArray SBThing=variable.configjson.getJSONObject("pra").getJSONArray("scoreboard");
        Boolean timeEarn=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getBoolean("enable");
        int timeEarnC=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getInteger("sec");
        int timeEarnE=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("timeearn").getInteger("exp");
        Boolean blockEarn=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("blockearn").getBoolean("enable");
        int blockEarnC=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("blockearn").getInteger("bls");
        int blockEarnE=variable.configjson.getJSONObject("pra").getJSONObject("exp").getJSONObject("blockearn").getInteger("exp");
        while (true){
            try{
                Thread.sleep(500);
                tick++;
                Object[] pl=Server.getInstance().getOnlinePlayers().values().toArray();
                int arenac=0;
                if(pl.length==0){continue;}
                String SB_Title="";
                if(SBCount<sbTitleL.length) {
                    for (int i = 0; i < sbTitleL.length; i++) {
                        if(i<SBCount){
                            SB_Title=SB_Title+"§f"+sbTitleL[i];
                        }else{
                            SB_Title=SB_Title+"§e"+sbTitleL[i];
                        }
                    }
                }else if(SBCount==sbTitleL.length||SBCount==sbTitleL.length+2){
                    SB_Title="§f"+sbTitle;
                }else if(SBCount==sbTitleL.length+1){
                    SB_Title="§e"+sbTitle;
                }else if(SBCount==sbTitleL.length+3){
                    SB_Title="§e"+sbTitle;
                    SBCount=0;
                }
                SBCount++;
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
                                        ExpUtils.addExp(plj, timeEarnE, expTip, lvUp, "timeearn", p);
                                        variable.playerLevelJSON.put(p.getName(),plj);
                                    }
                                }
                                if(blockEarn){
                                    int pBC=variable.playerBlock.get(p.getName());
                                    if(pBC>=blockEarnC){
                                        variable.playerBlock.put(p.getName(),pBC%blockEarnC);
                                        int addExp=pBC/blockEarnC;
                                        ExpUtils.addExp(plj, blockEarnE * addExp, expTip, lvUp, "blockearn", p);
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
                        if(scoreb){
                            ArrayList<String> arr=new ArrayList<>();
                            JSONObject plj=variable.playerLevelJSON.get(p.getName());
                            String SB_Player=p.getName(),SB_Level=plj.getInteger("level")+"",SB_LowProgcess=plj.getInteger("exp")+"",SB_MaxProgcess=ExpUtils.calcNeedExp(plj.getInteger("level")+1)+"",SB_Placed=plj.getInteger("place")+"";
                            p.setNameTag("§7[e"+SB_Level+"§7]§f"+p.getName());
                            for(int i=0;i<SBThing.size();i++){
                                arr.add(SBThing.getString(i).replaceAll("%player",SB_Player).replaceAll("%level%",SB_Level).replaceAll("%lowProgcess%",SB_LowProgcess).replaceAll("%maxProgcess%",SB_MaxProgcess).replaceAll("%placed%",SB_Placed));
                            }
                            ScoreboardUtils.showSBFromArrayList(p,arr,SB_Title);
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
