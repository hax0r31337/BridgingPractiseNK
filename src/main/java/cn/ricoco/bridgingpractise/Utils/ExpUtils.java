package cn.ricoco.bridgingpractise.Utils;

import cn.nukkit.Player;
import cn.ricoco.bridgingpractise.variable;
import com.alibaba.fastjson.JSONObject;

public class ExpUtils {
    public static int calcExp(int lv){
        if(lv<=16){
            //[Level]2 + 6[Level]
            return (int) Math.round(Math.pow(lv, 2) + (6 * lv));
        }else if(lv<=31){
            //2.5[Level]2 - 40.5[Level] + 360
            return (int) Math.round((2.5 * Math.pow(lv, 2)) - (40.5 * lv) + 360);
        }else{
            //4.5[Level]2 - 162.5[Level] + 2220
            return (int) Math.round((4.5 * Math.pow(lv, 2)) - (162.5 * lv) + 2220);
        }
    }
    public static int calcNeedExp(int lv){
        if(lv<=16){
            //2[Current Level] + 7
            return (int) Math.round((2 * lv) + 7);
        }else if(lv<=31){
            //5[Current Level] - 38
            return (int) Math.round((5 * lv) - 38);
        }else{
            //9[Current Level] - 158
            return (int) Math.round((9 * lv) - 158);
        }
    }
    public static JSONObject addExp(JSONObject json, int add, Boolean expTip, Boolean lvUp,String earn, Player p){
        int need=calcNeedExp(json.getInteger("level")+1);
        if(expTip){
            p.sendMessage(variable.langjson.getString(earn).replaceAll("%1",add+""));
        }
        if(need<(json.getInteger("exp")+add)){
            json.put("level",json.getInteger("level")+1);
            json.put("exp",(json.getInteger("exp")+add)-need);
            if(lvUp){
                p.sendMessage(variable.langjson.getString("levelup").replaceAll("%1",json.getInteger("level")+""));
            }
        }else{
            json.put("exp",json.getInteger("exp")+add);
        }
        return json;
    }
}
