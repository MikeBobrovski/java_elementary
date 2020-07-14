package Experiment;

public class Threads {
    public static void main(String[] args) throws InterruptedException {

        Thread t0 = Thread.currentThread(); // получаем главный поток
        System.out.println(t0); // main
        t0.setName("MainThread");
        Util.Logger.getLogger().log(Thread.currentThread().getName() + " is started");



        Thread t1 = new MyThread1("MyThread");
        t1.setDaemon(true);
        t1.start();

         new MyThread2().run();


        Thread t3 = new Thread(new SmthImpRunnable());	//Создание потока "myThready"
        t3.start();
        Thread.sleep(1000);

        Util.Logger.getLogger().log(Thread.currentThread().getName() + " is finished");
    }
}
