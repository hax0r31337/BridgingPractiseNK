package cn.ricoco.bridgingpractise.Plugin;

import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.ricoco.bridgingpractise.variable;

import java.util.Map;

public class ClearBlocks {
    public Clear runner;
    public Thread thread;
    public ClearBlocks(Map blockmap,int blength,Boolean instabreak){
        if(instabreak){
            for(int i=0;i<blength;i++){
                try{
                    Position pos= (Position) blockmap.get(i);
                    if(variable.configjson.getJSONObject("pra").getBoolean("breakparticle")){
                        pos.getLevel().addParticle(new DestroyBlockParticle(new Vector3(pos.x+0.5,pos.y+0.5,pos.z+0.5),pos.getLevelBlock()));
                    }
                    pos.level.setBlockAt((int)pos.x,(int)pos.y,(int)pos.z,0,0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            runner = new Clear();
            Clear.blockmap=blockmap;
            Clear.blength=blength;
            Clear.delay=variable.configjson.getJSONObject("pra").getInteger("breakdelay");
            thread = new Thread(runner);
            thread.start();
        }
    }
}
class Clear implements Runnable{
    public static int delay;
    public static Map blockmap;
    public static int blength;
    @Override
    public void run() {
        for(int i=0;i<blength;i++){
            try{
                Thread.sleep(delay);
                Position pos= (Position) blockmap.get(i);
                if(variable.configjson.getJSONObject("pra").getBoolean("breakparticle")){
                    pos.getLevel().addParticle(new DestroyBlockParticle(new Vector3(pos.x+0.5,pos.y+0.5,pos.z+0.5),pos.getLevelBlock()));
                }
                pos.level.setBlockAt((int)pos.x,(int)pos.y,(int)pos.z,0,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
