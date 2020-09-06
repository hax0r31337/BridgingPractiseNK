package cn.ricoco.bridgingpractise;

import cn.nukkit.level.Level;
import cn.ricoco.bridgingpractise.Command.RunCommand;
import cn.ricoco.bridgingpractise.Plugin.MetricsLite;
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
        try {
            FileUtils.deldir("./plugins/BridgingPractise/cache/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        new File("./plugins/BridgingPractise/lang/").mkdir();
        new File("./plugins/BridgingPractise/players/").mkdir();
        if(!new File("./plugins/BridgingPractise/config.json").exists()){
            Level deflevel=Server.getInstance().getDefaultLevel();
            Position ws=deflevel.getSpawnLocation();
            String cfgpath="./plugins/BridgingPractise/config.json";
            Position wpos=deflevel.getSafeSpawn();
            FileUtils.ReadJar("resources/config.json",cfgpath);
            FileUtils.writeFile(cfgpath,FileUtils.readFile(cfgpath).replaceAll("%1",wpos.x+"").replaceAll("%2",wpos.y+"").replaceAll("%3",wpos.z+"").replaceAll("%4",deflevel.getName()));
            LevelUtils.unzip("bpractise");
        }
        if(!new File("./plugins/BridgingPractise/lang/en_us.json").exists()){
            FileUtils.ReadJar("resources/lang/en_us.json","./plugins/BridgingPractise/lang/en_us.json");
        }
        if(!new File("./plugins/BridgingPractise/lang/zh_cn.json").exists()){
            FileUtils.ReadJar("resources/lang/zh_cn.json","./plugins/BridgingPractise/lang/zh_cn.json");
        }
        variable.configjson=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/config.json"));
        String langpath="./plugins/BridgingPractise/lang/"+variable.configjson.getJSONObject("pra").getString("language")+".json";
        if(!new File(langpath).exists()){
            plugin.getLogger().warning("LANGUAGE \""+variable.configjson.getJSONObject("pra").getString("language")+".json\" NOT FOUND.LOADING EN_US.json");
            langpath="./plugins/BridgingPractise/lang/en_us.json";
        }
        variable.langjson=JSONObject.parseObject(FileUtils.readFile(langpath));
        variable.disabledmg=variable.configjson.getJSONObject("pra").getJSONArray("disabledmg");
        try {
            FileUtils.Copydir("./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/","./plugins/BridgingPractise/cache/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LevelUtils.loadLevel(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"));
        getServer().getPluginManager().registerEvents(new EventLauncher(this), this);
        plugin.getServer().getCommandMap().register(variable.configjson.getJSONObject("pra").getString("command"),new RunCommand(variable.configjson.getJSONObject("pra").getString("command"),"Bridging Practise"));
        variable.lowy=variable.configjson.getJSONObject("pos").getDouble("lowy");
        PluginTick.StartTick();
        try {
            new MetricsLite(this,8604);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject placeBlock=variable.configjson.getJSONObject("block");
        variable.cantPlaceOn.add(placeBlock.getInteger("stop"));
        variable.cantPlaceOn.add(placeBlock.getInteger("res"));
        variable.cantPlaceOn.add(placeBlock.getInteger("speedup"));
        variable.cantPlaceOn.add(placeBlock.getInteger("backres"));
        variable.cantPlaceOn.add(placeBlock.getInteger("elevator"));
        Server.getInstance().getLogger().info("§eBridgingPractiseNK §fBy §bRicoGG §aSuccessfully Loaded.");
    }
    @Override
    public void onDisable() {
        LevelUtils.unloadLevel(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"));
        try {
            FileUtils.deldir("./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/");
            FileUtils.Copydir("./plugins/BridgingPractise/cache/","./worlds/"+variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")+"/");
            FileUtils.deldir("./plugins/BridgingPractise/cache/");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Main getPlugin() {
        return plugin;
    }
}


