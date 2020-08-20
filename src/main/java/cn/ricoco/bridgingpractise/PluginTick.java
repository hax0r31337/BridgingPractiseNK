package cn.ricoco.bridgingpractise;

public class PluginTick {
    public static Runner runner;
    public static Thread thread;
    public static void StartTick(){
        runner = new Runner();
        thread = new Thread(runner);
        thread.start();
    }
}
class Runner implements Runnable{
    public void run() {
        try{
            while (true){
                Thread.sleep(3000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
