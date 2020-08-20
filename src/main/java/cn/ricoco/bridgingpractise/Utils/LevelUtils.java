package cn.ricoco.bridgingpractise.Utils;

import cn.ricoco.bridgingpractise.Main;
import cn.nukkit.Server;

import java.io.File;

import static cn.ricoco.bridgingpractise.Utils.FileUtils.ReadJar;

public class LevelUtils {
    public static void unzip(String dir){
        new File("./worlds/"+dir+"/region/").mkdirs();
        ReadJar("map/level.dat","./worlds/"+dir+"/level.dat");
        ReadJar("map/region/r.0.0.mca","./worlds/"+dir+"/region/r.0.0.mca");
        ReadJar("map/region/r.0.-1.mca","./worlds/"+dir+"/region/r.0.-1.mca");
        ReadJar("map/region/r.-1.0.mca","./worlds/"+dir+"/region/r.-1.0.mca");
        ReadJar("map/region/r.-1.-1.mca","./worlds/"+dir+"/region/r.-1.-1.mca");
    }
    public static void loadLevel(String string){
        Server.getInstance().loadLevel(string);
    }
    public static void unloadLevel(String string){
        Main m= Main.getPlugin();
        m.getServer().unloadLevel(m.getServer().getLevelByName(string));
    }
}
