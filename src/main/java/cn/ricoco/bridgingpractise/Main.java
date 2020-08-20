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
            FileUtils.writeFile("./plugins/BridgingPractise/config.json","{\n" +
                    "    \"block\":{\n" +
                    "        \"pra\":{\"id\":24,\"d\":0,\"c\":448},\n" +
                    "        \"stop\":{\"id\":152,\"d\":0},\n" +
                    "        \"res\":{\"id\":133,\"d\":0}\n" +
                    "    },\n" +
                    "    \"pos\":{\n" +
                    "        \"lowy\":13.0,\n" +
                    "        \"pra\":{\"x\":128.0,\"y\":21.0,\"z\":135.0,\"l\":\"bpractise\"},\n" +
                    "        \"exit\":{\"x\":"+ws.x+",\"y\":"+ws.y+",\"z\":"+ws.z+",\"l\":\"world\"}\n" +
                    "    },\n" +
                    "    \"pra\":{\n" +
                    "        \"instabreak\":false,\n" +
                    "        \"falldmgtip\":true,\n" +
                    "        \"pvpprotect\":false,\n" +
                    "        \"fullhunger\":true,\n" +
                    "        \"falllagdmg\":20,\n" +
                    "        \"iffalllagdmg\":true,\n" +
                    "    },\n" +
                    "}");
            LevelUtils.unzip("bpractise");
        }
        if(!new File("./plugins/BridgingPractise/lang.json").exists()){
            FileUtils.writeFile("./plugins/BridgingPractise/lang.json","{}");
        }
        variable.configjson=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/config.json"));
        variable.langjson=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/lang.json"));
        try {
            FileUtils.Copydir("./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/","./plugins/BridgingPractise/cache/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LevelUtils.loadLevel(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"));
        getServer().getPluginManager().registerEvents(new EventLauncher(this), this);
        plugin.getServer().getCommandMap().register("bpractise",new RunCommand("bpractise","Bridging Practise"));
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


