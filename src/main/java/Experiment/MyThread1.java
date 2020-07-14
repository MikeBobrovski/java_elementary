package Experiment;

public class MyThread1 extends Thread {
    MyThread1(String name) {
        super(name);
    }

    public void run() {
        Util.Logger.getLogger().log("поток через наследование: " + Thread.currentThread().getName() + " is started");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Util.Logger.getLogger().log(Thread.currentThread().getName() + " has been interrupted");
        }
        Util.Logger.getLogger().log(Thread.currentThread().getName() + " is finished");
    }
}
