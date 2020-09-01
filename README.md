# BridgingPractiseNK  
  
This is a bridging practise plugin helps you practise your bridging ability.  
一个帮助你练习搭路的插件  
  
# 介绍  
  
## 简体中文  
  
### 指令  
  
(默认/bpractise可在config.json中修改)  
/bpractise join 加入练习区  
/bpractise leave 离开练习区  
  
### 警告  
  
1.不要让MobPlugin在练习区生成生物,否则会空指针  
2.不要乱动./plugins/BridgingPractise/cache下的地图备份文件  
3.关服请用stop命令关服,不要点X,否则可能会导致一些未知的问题  
4.初始化前请保证没有名为“bpractise”的地图,否则将被覆盖  
  
### 配置文件  
  
config.json键值说明:  
  
#### block  
  
block 搭路用方块(id:物品id,d:物品特殊值,c:物品数量)  
stop 结束点方块id  
res 重生点设置方块id  
speedup 加速方块id  
backres 回出生点方块id  
elevator 电梯方块id(需在同一x,z坐标不同高度有2个这种方块,站在其中一个方块上即可传送到另一个)  
pickaxe 搞(id:物品id,d:物品特殊值)  
  
#### pos  
  
lowy 最低y坐标(低于将被拉回重生点)  
pra 练习区坐标(x:x坐标,y:y坐标,z:z坐标,l:世界名)  
exit 退出后回到的坐标(x:x坐标,y:y坐标,z:z坐标,l:世界名)(初始化时会使用主世界安全重生点)  
  
#### pra  
  
language 语言文件(对应./plugins/BridgingPractise/lang/值.json)(假如没有会在控制台警告并读取英文的语言文件)  
instabreak 死亡后方块是否直接清除(true直接清除,false逐渐清除)  
breakparticle 清除方块是否掉落粒子  
breakdelay 逐渐清除方块时清除单个方块的延时(ms)  
falldmgtip 是否在受到掉落伤害时向玩家发出提示  
iffalllagdmg 是否受到超过阈值的跌落伤害时回到出生点  
pvpprotect pvp保护  
candrop 玩家能否扔物品  
prompt 是否向玩家发送搭路方块速度,距离,最远距离信息的Popup提示  
speedlv 站在加速方块上获得速度效果的等级  
speedtick 站在加速方块上获得速度效果的世时间(tick刻)  
time 锁定练习区世界的时间  
weather 锁定练习区世界的天气(clear,rain,thunder)  
command 搭路练习指令名(/值 join/leave)  
enablecmd 练习区允许的指令(需包含插件指令否则玩家无法退出)(取指令名例如/kill @e就是kill)  
  
## English  
  
sorry my english is bad.  
  
### Commands  
  
default:/bpractise,can change in config.json  
/bpractise join - Join the practise area  
/bpractise leave - Leave the practise area  
  
### Warning  
  
1.DON'T LET MobPlugin GENERATE MOBS AT PRACTISE AREA  
2.DON'T MODIFY LEVEL BACKUP FILE AT ./plugins/BridgingPractise/cache  
3.PLEASE USE 'stop' COMMAND TO STOP THE SERVER,DON'T CLICK "X" TO STOP  
4.PLEASE MAKE SURE THAT THERE IS NO LEVEL NAMED "BPRACTISE" BEFORE INITIALIZATION, OTHERWISE IT WILL BE OVERWRITTEN  
  
### Config File  
  
config.json keys:  
  
#### block  
  
block - Block used to bridging(id:item id,d:item data id/damage,c:item count)  
stop - Stop point block id  
res - respawn point block id  
speedup - speedup block id  
backres - back-to-respawn block id  
elevator - elevator block id(Need to have 2 such blocks at the same x, z coordinates and different heights, stand on one of them and you can teleport to the other)  
pickaxe - pickaxe(id:item id,d:item data id/damage)  
  
#### pos  
  
lowy - Lowest y position(Below will be pulled back to the respawn point)  
pra - Practise Position(x:x position,y:y position,z:z position,l:Level name)  
exit - The Position when return back(x:x position,y:y position,z:z position,l:Level name)(The main world safe respawn point will be used during initialization)  
  
#### pra  
  
language - Language File(Corresponding to ./plugins/BridgingPractise/lang/value.json) (if the file is not exists will warning in the console and read the English language file)  
instabreak - Will cleared directly when player death?(true is cleared directly, false is cleared gradually)  
breakparticle - Will drop down particles when block break?  
breakdelay - delay time when clear blocks gradually(ms)  
falldmgtip - Will warn player when take damage from falling  
iffalllagdmg - Will to return to the spawn point when receiving fall damage that exceeds the threshold  
pvpprotect - PvP Protection in the Practise area  
candrop - Can players drop items in the Practise area  
prompt - Will send popup prompts to players about the speed, distance, and farthest distance  
speedlv - The level of speed effect obtained by standing on the speedup block  
speedtick - Time to get the speed effect by standing on the speedup block(tick)  
time - Lock the time in the practice area world(int Time)  
weather - Lock the weather in the practice area world(clear,rain,thunder)  
command - Command of bridging practise plugin(/value join/leave)  
enablecmd - Commands allowed in the practice area(plugin commands must be included or the player cannot exit) (use the command name for example /kill @e is kill)