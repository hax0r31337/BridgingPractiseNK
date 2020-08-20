package cn.ricoco.bridgingpractise;

import cn.ricoco.bridgingpractise.Command.RunCommand;
import cn.ricoco.bridgingpractise.Utils.FileUtils;
import cn.ricoco.bridgingpractise.Utils.LevelUtils;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

public class Main extends PluginBase {
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin=this;
        new File("./plugins/BridgingPractise/cache/").mkdirs();
        if(!new File("./plugins/BridgingPractise/config.json").exists()){
            Position ws=Server.getInstance().getLevelByName("world").getSpawnLocation();
            FileUtils.ReadJar("resources/config.json","./plugins/BridgingPractise/config.json");
            LevelUtils.unzip("bpractise");
        }
        if(!new File("./plugins/BridgingPractise/lang.json").exists()){
            FileUtils.ReadJar("resources/lang.json","./plugins/BridgingPractise/lang.json");
        }
        variable.configjson=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/config.json"));
        variable.langjson=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/lang.json"));
        variable.disabledmg=variable.configjson.getJSONObject("pra").getJSONArray("disabledmg");
        try {
            FileUtils.Copydir("./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/","./plugins/BridgingPractise/cache/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LevelUtils.loadLevel(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"));
        getServer().getPluginManager().registerEvents(new EventLauncher(this), this);
        plugin.getServer().getCommandMap().register("bpractise",new RunCommand(variable.configjson.getJSONObject("pra").getString("command"),"Bridging Practise"));
        variable.lowy=variable.configjson.getJSONObject("pos").getDouble("lowy");
        PluginTick.StartTick();
    }
    @Override
    public void onDisable() {
        LevelUtils.unloadLevel(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"));
        try {
            FileUtils.deldir("./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/");
            FileUtils.Copydir("./plugins/BridgingPractise/cache/","./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Main getPlugin() {
        return plugin;
    }
}


