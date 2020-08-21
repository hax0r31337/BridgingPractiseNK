package cn.ricoco.bridgingpractise;

import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class variable {
    public static JSONObject configjson;
    public static JSONObject langjson;
    public static JSONArray disabledmg;
    public static Double lowy;
    public static Map<String,Map> blockpos=new HashMap<>();
    public static Map<String,Integer> blocklength=new HashMap<>();
    public static Map<String,Integer> blocksecond=new HashMap<>();
    public static Map<String,Integer> blockmax=new HashMap<>();
    public static Map<String,Position> playerresp=new HashMap<>();
    public static Map<String,Map> playerinv=new HashMap<>();
    public static Map<String,Integer> playerhunger=new HashMap<>();
    public static Map<String,Boolean> playeronresp=new HashMap<>();
    public static Map<String,Integer> playergamemode=new HashMap<>();
}
