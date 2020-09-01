# BridgingPractiseNK
This is a bridging practise plugin helps you practise your bridging ability.

# 介绍

## 简体中文
更完善的功能：智能初始化，自带地图（初始化自动生成配置文件和初始地图），更像Java版的BridgingAnalyzer插件（出生点保存，加速方块，结束方块结束提示，上下传送方块，回到出生点方块），死后方块逐渐消失

### 指令
（默认/bpractise可在config.json中修改）
/bpractise join 加入练习区
/bpractise leave 离开练习区

### 警告

1.不要让MobPlugin在练习区生成生物，否则会空指针
2.不要乱动./plugins/BridgingPractise/cache下的地图备份文件
3.关服请用stop命令关服，不要点X，否则可能会导致一些未知的问题
4.初始化前请保证没有名为“bpractise”的地图，否则将被覆盖

### 配置文件
config.json键值说明：
#### block

block 搭路用方块(id:物品id，d:物品特殊值，c:物品数量)
stop 结束点方块id
res 重生点设置方块id
speedup 加速方块id
backres 回出生点方块id
elevator 电梯方块id（需在同一x,z坐标不同高度有2个这种方块，站在其中一个方块上即可传送到另一个）
pickaxe 搞(id:物品id，d:物品特殊值)

#### pos

lowy 最低y坐标（低于将被拉回重生点）
pra 练习区坐标（x：x坐标，y：y坐标，z：z坐标，l：世界名）
exit 退出后回到的坐标（x：x坐标，y：y坐标，z：z坐标，l：世界名）（初始化时会使用主世界安全重生点）

#### pra

language 语言文件（对应./plugins/BridgingPractise/lang/值.json）（假如没有会在控制台警告并读取英文的语言文件）
instabreak 死亡后方块是否直接清除（true直接清除，false逐渐清除）
breakparticle 清除方块是否掉落粒子
breakdelay 逐渐清除方块时清除单个方块的延时（ms）
falldmgtip 是否在受到掉落伤害时向玩家发出提示
iffalllagdmg 是否受到超过阈值的跌落伤害时回到出生点
pvpprotect pvp保护
candrop 玩家能否扔物品
prompt 是否向玩家发送搭路方块速度，距离，最远距离信息的Popup提示
speedlv 站在加速方块上获得速度效果的等级
speedtick 站在加速方块上获得速度效果的世时间(tick刻)
time 锁定练习区世界的时间
weather 锁定练习区世界的天气（clear,rain,thunder）
command 搭路练习指令名（/值 join/leave）
enablecmd 练习区允许的指令（需包含插件指令否则玩家无法退出）（取指令名例如/kill @e就是kill）