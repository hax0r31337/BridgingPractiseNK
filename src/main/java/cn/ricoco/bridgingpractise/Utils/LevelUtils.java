package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.level.Level;
import cn.ricoco.bridgingpractise.Main;
import cn.nukkit.Server;

import java.io.File;

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
}
