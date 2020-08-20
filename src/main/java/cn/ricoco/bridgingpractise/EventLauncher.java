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
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.ricoco.bridgingpractise.Plugin.ClearBlocks;
import cn.ricoco.bridgingpractise.Plugin.DelayTP;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventLauncher implements Listener {
    private final Main plugin;
    public EventLauncher(Main main) {
        this.plugin = main;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        if(e.isCancelled()){return;}
        if(e.getPlayer().getPosition().getLevel().getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")&&!variable.configjson.getJSONObject("pra").getBoolean("candrop")){
            new DelayTP(e.getPlayer(),Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("exit").getString("l"))),3000);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        if(p.getPosition().getLevel().getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")&&!variable.configjson.getJSONObject("pra").getBoolean("candrop")){
            String pname=p.getName();
            new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),true);
            p.setGamemode(variable.playergamemode.get(pname));
            p.getInventory().setContents(variable.playerinv.remove(pname).getContents());
            variable.playerresp.remove(pname);
            p.getFoodData().setLevel(variable.playerhunger.remove(pname));
            p.teleport(Position.fromObject(new Vector3(variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("x"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("y"),variable.configjson.getJSONObject("pos").getJSONObject("exit").getDouble("z")),Server.getInstance().getLevelByName(variable.configjson.getJSONObject("pos").getJSONObject("exit").getString("l"))));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        if(p.getPosition().getLevel().getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")&&!variable.configjson.getJSONObject("pra").getBoolean("candrop")){
            e.setCancelled();
            p.sendMessage(variable.langjson.getString("cantdrop"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e){
        if(e.isCancelled()){return;}
        Player p=e.getPlayer();
        Position pos=p.getPosition();
        if(pos.getLevel().getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")){
            if(pos.getY()<variable.lowy){
                p.teleport(variable.playerresp.get(p.getName()));
                new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),variable.configjson.getJSONObject("pra").getBoolean("instabreak"));
                Map<Integer,Position> m=new HashMap<>();
                variable.blockpos.put(p.getName(),m);
                variable.blocklength.put(p.getName(),0);
                variable.playeronresp.put(p.getName(),true);
                return;
            }
            int bid=Position.fromObject(new Vector3(pos.x, pos.y-1, pos.z), pos.level).getLevelBlock().getId();
            if(bid==variable.configjson.getJSONObject("block").getJSONObject("res").getInteger("id")){
                if(!variable.playeronresp.get(p.getName())){
                    p.sendTitle(variable.langjson.getString("setresp"));
                    variable.playeronresp.put(p.getName(),true);
                    return;
                }
            }else{
                variable.playeronresp.put(p.getName(),false);
            }
            if(bid==variable.configjson.getJSONObject("block").getJSONObject("stop").getInteger("id")){
                p.sendTitle(variable.langjson.getString("completebridge"));
                new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),variable.configjson.getJSONObject("pra").getBoolean("instabreak"));
                Map<Integer,Position> m=new HashMap<>();
                variable.blockpos.put(p.getName(),m);
                variable.blocklength.put(p.getName(),0);
                p.teleport(variable.playerresp.get(p.getName()));
                return;
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent e){
        if(e.isCancelled()){return;}
        Player p= Server.getInstance().getPlayer(e.getEntity().getName());
        Position pos=p.getPosition();
        if(pos.getLevel().getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")){
            String c=e.getCause().toString();
            if(variable.disabledmg.indexOf(c)!=-1){e.setCancelled();}
            if(c=="FALL"){
                JSONObject json=variable.configjson.getJSONObject("pra");
                if(json.getBoolean("iffalllagdmg")&&json.getFloat("falllagdmg")<=e.getDamage()){
                    p.teleport(variable.playerresp.get(p.getName()));
                    new ClearBlocks(variable.blockpos.remove(p.getName()),variable.blocklength.remove(p.getName()),variable.configjson.getJSONObject("pra").getBoolean("instabreak"));
                    Map<Integer,Position> m=new HashMap<>();
                    variable.blockpos.put(p.getName(),m);
                    variable.blocklength.put(p.getName(),0);
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
        if(variable.configjson.getJSONObject("pra").getBoolean("pvpprotect")&&en.getPosition().level.getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")){
            e.setCancelled();
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceEvent(BlockPlaceEvent e){
        if(e.isCancelled()){return;}
        Block b=e.getBlock();
        if(b.level.getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")){
            Player p=e.getPlayer();
            Map m=variable.blockpos.get(p.getName());
            m.put(variable.blocklength.get(p.getName()),Position.fromObject(new Vector3(b.x,b.y,b.z),b.level));
            variable.blocklength.put(p.getName(),variable.blocklength.get(p.getName())+1);
            e.setCancelled();
            b.level.setBlockAt((int)b.x,(int)b.y,(int)b.z,b.getId(),b.getDamage());
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent e){
        if(e.isCancelled()){return;}
        Block b=e.getBlock();
        if(b.level.getName()==variable.configjson.getJSONObject("pos").getJSONObject("pra").getString("l")){
            if(b.getId()==variable.configjson.getJSONObject("block").getJSONObject("pra").getInteger("id")){
                Item[] dr={};
                e.setDrops(dr);
            }else{
                e.setCancelled();
            }
        }
    }
}
