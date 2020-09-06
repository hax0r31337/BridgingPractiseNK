package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.ricoco.bridgingpractise.Main;
import cn.nukkit.Server;
import cn.ricoco.bridgingpractise.variable;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.Map;

import static cn.ricoco.bridgingpractise.Utils.FileUtils.ReadJar;

public class LevelUtils {
    public static void unzip(String dir){
        new File("./worlds/"+dir+"/region/").mkdirs();
        ReadJar("resources/level/level.dat","./worlds/"+dir+"/level.dat");
        ReadJar("resources/level/region/r.0.0.mca","./worlds/"+dir+"/region/r.0.0.mca");
        ReadJar("resources/level/region/r.0.1.mca","./worlds/"+dir+"/region/r.0.1.mca");
        ReadJar("resources/level/region/r.0.-1.mca","./worlds/"+dir+"/region/r.0.-1.mca");
        ReadJar("resources/level/region/r.-1.0.mca","./worlds/"+dir+"/region/r.-1.0.mca");
        ReadJar("resources/level/region/r.-1.-1.mca","./worlds/"+dir+"/region/r.-1.-1.mca");
    }
    public static void loadLevel(String string){
        Server.getInstance().loadLevel(string);
    }
    public static void unloadLevel(String string){
        Main m= Main.getPlugin();
        m.getServer().unloadLevel(m.getServer().getLevelByName(string));
    }
    public static void setLevelWeather(Level level, String mode){
        if (!mode.equals("clear")) {
            if(mode.equals("rain")){
                level.setRaining(true);
                level.setThundering(false);
            }else if(mode.equals("thunder")){
                level.setThundering(true);
                level.setRaining(false);
            }
        } else {
            level.setRaining(false);
            level.setThundering(false);
        }
    }
    public static void replaceBl(Map blockmap, int blength){
        JSONObject vR=variable.configjson.getJSONObject("pra").getJSONObject("victoryreplace");
        int repId=vR.getInteger("id"),repData=vR.getInteger("d");
        for(int i=0;i<blength;i++){
            try{
                Position pos= (Position) blockmap.get(i);
                pos.level.setBlockAt((int)pos.x,(int)pos.y,(int)pos.z,repId,repData);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
