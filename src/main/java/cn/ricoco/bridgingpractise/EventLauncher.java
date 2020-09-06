package cn.ricoco.bridgingpractise;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.ricoco.bridgingpractise.Plugin.ClearBlocks;
import cn.ricoco.bridgingpractise.Plugin.DelayTP;
import cn.ricoco.bridgingpractise.Plugin.Exp;
import cn.ricoco.bridgingpractise.Utils.EntityUtils;
import cn.ricoco.bridgingpractise.Utils.FileUtils;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.Map;

import static cn.ricoco.bridgingpractise.Utils.PlayerUtils.ClearBL;

public class EventLauncher implements Listener {
    private final Main plugin;
    public EventLauncher(Main main) {
        this.plugin = main;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        Player p=e.getPlayer();
        String jsonPath="./plugins/BridgingPractise/players/"+p.getName()+".json";
        if(!new File(jsonPath).exists()){
            FileUtils.writeFile(jsonPath,"{\n" +
                    "    \"level\":0,\n" +
                    "    \"exp\":0,\n" +
                    "    \"place\":0\n" +
                    "}");
        }
        if(e.getPlayer().getPosition().getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            new DelayTP(e.getPlayer(),Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("exit").getString("l"))),3000);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e){
        Player p=e.getPlayer();
        if(p.getPosition().getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            String pname=p.getName();new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),true);
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
            p.teleport(Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("exit").getString("l"))));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        if(p.getPosition().getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            String cmd=e.getMessage().substring(1).split(" ")[0];
            if(!variable.configjson.getJSONObject("pra").getJSONArray("enablecmd").contains(cmd)){
                e.setCancelled();
                p.sendMessage(variable.langjson.getString("cmddisable"));
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        if(p.getPosition().getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")) &&!variable.configjson.getJSONObject("pra").getBoolean("candrop")){
            e.setCancelled();
            p.sendMessage(variable.langjson.getString("cantdrop"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        Position pos=p.getPosition();
        if(pos.getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            if(pos.getY()<variable.lowy){
                ClearBL(p,false);
                return;
            }
            int bid=Position.fromObject(new Vector3(pos.x, pos.y-1, pos.z), pos.level).getLevelBlock().getId();
            if(bid==variable.configjson.getJSONObject("block").getInteger("res")){
                if(!variable.playeronresp.get(p.getName())){
                    p.sendTitle(variable.langjson.getString("setresp"));
                    variable.playeronresp.put(p.getName(),true);
                    Block bl=Position.fromObject(new Vector3(pos.x, pos.y-1, pos.z), pos.level).getLevelBlock();
                    variable.playerresp.put(p.getName(),Position.fromObject(new Vector3(bl.x+0.5,bl.y+1,bl.z+0.5),pos.level));
                    return;
                }
            }else{
                variable.playeronresp.put(p.getName(),false);
            }
            if(bid==variable.configjson.getJSONObject("block").getInteger("stop")){
                p.sendTitle(variable.langjson.getString("completebridge"));
                ClearBL(p,true);
                return;
            }
            if(bid==variable.configjson.getJSONObject("block").getInteger("backres")){
                p.sendTitle(variable.langjson.getString("backresp"));
                variable.playeronresp.put(p.getName(),true);
                p.teleport(Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("pra").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))));
                return;
            }
            if(bid==variable.configjson.getJSONObject("block").getInteger("speedup")){
                p.addEffect(Effect.getEffect(1).setAmplifier(variable.configjson.getJSONObject("pra").getInteger("speedlv")).setDuration(variable.configjson.getJSONObject("pra").getInteger("speedtick")).setVisible(false));
                return;
            }
            int eid=variable.configjson.getJSONObject("block").getInteger("elevator");
            if(bid==eid){
                if(!variable.playeronelevator.get(p.getName())){
                    variable.playeronelevator.put(p.getName(),true);
                    Position tppos=null;
                    double posx=pos.x,posy=pos.y-1,posz=pos.z;
                    Level posl=pos.level;
                    for(int i = 0; i<255; i++){
                        if(i==posy){
                            continue;
                        }
                        if(Position.fromObject(new Vector3(posx,i,posz),posl).getLevelBlock().getId()==eid){
                            tppos=Position.fromObject(new Vector3(posx,i+1,posz),posl);
                            break;
                        }
                    }
                    if(tppos==null){
                        p.sendMessage(variable.langjson.getString("tpfailed"));
                    }else{
                        p.teleport(tppos);
                    }
                }
            }else{
                variable.playeronelevator.put(p.getName(),false);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent e){
        if(e.isCancelled()){return;}
        Player p= Server.getInstance().getPlayer(e.getEntity().getName());
        Position pos=p.getPosition();
        if(pos.getLevel().getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            String c=e.getCause().toString();
            e.setCancelled();
            if(c.equals("FALL")){
                JSONObject json=variable.configjson.getJSONObject("pra");
                EntityUtils.displayHurt(p);
                if(json.getBoolean("iffalllagdmg")&&json.getFloat("falllagdmg")<=e.getDamage()){
                    ClearBL(p,false);
                }
                if(json.getBoolean("falldmgtip")){
                    p.sendTitle(variable.langjson.getString("falldmgtip").replaceAll("%1",e.getDamage()+""));
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        if(e.isCancelled()){return;}
        Entity en=e.getEntity();
        if(variable.configjson.getJSONObject("pra").getBoolean("pvpprotect")&& en.getPosition().level.getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            e.setCancelled();
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceEvent(BlockPlaceEvent e){
        if(e.isCancelled()){return;}
        Block b=e.getBlock();
        if(b.level.getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            Player p=e.getPlayer();
            Map m=variable.blockpos.get(p.getName());
            m.put(variable.blocklength.get(p.getName()),Position.fromObject(new Vector3(b.x,b.y,b.z),b.level));
            variable.blocklength.put(p.getName(),variable.blocklength.get(p.getName())+1);
            variable.blocksecond.put(p.getName(),variable.blocksecond.get(p.getName())+1);
            e.setCancelled();
            b.level.setBlockAt((int)b.x,(int)b.y,(int)b.z,b.getId(),b.getDamage());
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent e){
        if(e.isCancelled()){return;}
        Block b=e.getBlock();
        if(b.level.getName().equals(variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l"))){
            if(b.getId()==variable.configjson.getJSONObject("block").getJSONObject("pra").getInteger("id")){
                Item[] dr={};
                e.setDrops(dr);
            }else{
                e.setCancelled();
            }
        }
    }
}
