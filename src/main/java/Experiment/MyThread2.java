package Experiment;

public class MyThread2 implements Runnable{
    @Override
    public void run() {
        Util.Logger.getLogger().log("поток через реализацию: " + Thread.currentThread().getName() + " is started");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Util.Logger.getLogger().log(Thread.currentThread().getName() + " has been interrupted");
        }
        Util.Logger.getLogger().log(Thread.currentThread().getName() + " is finished");
    }
}
