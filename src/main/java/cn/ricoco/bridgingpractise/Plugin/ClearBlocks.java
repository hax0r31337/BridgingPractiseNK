package cn.ricoco.bridgingpractise.Plugin;

import java.util.Map;

public class ClearBlocks {
    public Clear runner;
    public Thread thread;
    public ClearBlocks(Map blockmap,int blength,Boolean instabreak){
        runner = new Clear();
        thread = new Thread(runner);
        thread.start();
    }
}
class Clear implements Runnable{
    public static int delay;
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
