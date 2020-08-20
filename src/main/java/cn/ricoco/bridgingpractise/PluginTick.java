package cn.ricoco.bridgingpractise;

public class PluginTick {
    public static Runner runner;
    public static Thread thread;
    public static void StartTick(){
        runner = new Runner();
        thread = new Thread(runner);
        Runner.a="awa";
        thread.start();
    }
}
class Runner implements Runnable{
    public static String a;
    @Override
    public void run() {
        System.out.println(a);
    }
}