package cn.ricoco.bridgingpractise.Command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.ricoco.bridgingpractise.Plugin.ClearBlocks;
import cn.ricoco.bridgingpractise.Plugin.Exp;
import cn.ricoco.bridgingpractise.Utils.ExpUtils;
import cn.ricoco.bridgingpractise.Utils.FileUtils;
import cn.ricoco.bridgingpractise.Utils.PlayerUtils;
import cn.ricoco.bridgingpractise.variable;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RunCommand extends Command {
    public RunCommand(String name, String description) {
        super(name, description);
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!sender.isPlayer()){sender.sendMessage(variable.langjson.getString("notplayer"));return false;}
        if(args.length!=1){sender.sendMessage(variable.langjson.getString("usage").replaceAll("%1",variable.configjson.getJSONObject("pra").getString("command")));return false;}
        Player p= Server.getInstance().getPlayer(sender.getName());
        String levelName=p.getPosition().getLevel().getName(),pname=p.getName();
        switch (args[0]){
            case "join":
                if(!levelName.equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
                    Map<Integer,Position> m=new HashMap<>();
                    variable.blockpos.put(pname,m);
                    variable.playergamemode.put(pname,p.getGamemode());
                    variable.blocklength.put(pname,0);
                    variable.playerinv.put(pname,p.getInventory().getContents());
                    variable.playerhunger.put(pname,p.getFoodData().getLevel());
                    variable.blocksecond.put(pname,0);
                    variable.blockmax.put(pname,0);
                    p.getInventory().clearAll();
                    JSONObject j=variable.configjson.getJSONObject("block").getJSONObject("pra");
                    PlayerUtils.addItemToPlayer(p,Item.get(j.getInteger("id"),j.getInteger("d"),j.getInteger("c")));
                    j=variable.configjson.getJSONObject("block").getJSONObject("pickaxe");
                    PlayerUtils.addItemToPlayer(p,Item.get(j.getInteger("id"),j.getInteger("d"),1));
                    Position pos=Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")));
                    variable.playerresp.put(pname,pos);
                    variable.playeronresp.put(pname,false);
                    variable.playeronelevator.put(pname,false);
                    variable.playerLevel.put(pname,new Exp(p.getExperience(),p.getExperienceLevel()));
                    variable.playerBlock.put(pname,0);
                    variable.playerTime.put(pname,0);
                    JSONObject plj=JSONObject.parseObject(FileUtils.readFile("./plugins/BridgingPractise/players/"+pname+".json"));
                    variable.playerLevelJSON.put(pname,plj);
                    p.setNameTag("§7[§6"+plj.getInteger("level")+"§7]§f"+p.getName());
                    if(variable.configjson.getJSONObject("pra").getJSONObject("exp").getBoolean("enable")){
                        p.setExperience(plj.getInteger("exp"),plj.getInteger("level"));
                    }else{
                        p.setExperience(0);
                    }
                    sender.sendMessage(variable.langjson.getString("joinedarena"));
                    p.teleport(pos);
                    p.setGamemode(0);
                }else{
                    sender.sendMessage(variable.langjson.getString("stillinarena"));
                }
                break;
            case "leave":
                if(levelName.equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
                    new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),true);
                    p.setGamemode(variable.playergamemode.get(pname));
                    p.getInventory().setContents(variable.playerinv.remove(pname));
                    variable.playerresp.remove(pname);
                    variable.blocksecond.remove(pname);
                    variable.blockmax.remove(pname);
                    variable.playeronresp.remove(pname);
                    variable.playeronelevator.remove(pname);
                    variable.playerBlock.remove(pname);
                    variable.playerTime.remove(pname);
                    Exp exp=variable.playerLevel.remove(pname);
                    p.setExperience(exp.getExp(),exp.getLv());
                    FileUtils.writeFile("./plugins/BridgingPractise/players/"+pname+".json",JSONObject.toJSONString(variable.playerLevelJSON.remove(pname)));
                    p.getFoodData().setLevel(variable.playerhunger.remove(pname));
                    p.setNameTag(p.getName());
                    p.teleport(Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("exit").getString("l"))));
                    sender.sendMessage(variable.langjson.getString("leavearena"));
                }else{
                    sender.sendMessage(variable.langjson.getString("notinarena"));
                }
                break;
            default:
                sender.sendMessage(variable.langjson.getString("usage").replaceAll("%1",variable.configjson.getJSONObject("pra").getString("command")));
        }
        return false;
    }
}