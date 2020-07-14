package Experiment;

import java.util.concurrent.Phaser;

public class Program {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);//создаем фазер на 1 поток - поток майна
        new Thread(new PhaseThread(phaser, "PhaseThread 1")).start();//начинаем 2 потока, передаем в них фазер и имя (см конструктор)
        new Thread(new PhaseThread(phaser, "PhaseThread 2")).start();

        for (int i = 0; i <= 5; i++) {
            System.out.println("Фаза " + phaser.arriveAndAwaitAdvance() + " завершена");//ждем и выводим номер текущей фазы
        }

        System.out.println("номер при дерегистрации: " + phaser.arriveAndDeregister());//снять с регистрации
    }
}//Program

class PhaseThread implements Runnable {

    Phaser phaser;
    String name;

    PhaseThread(Phaser p, String n) {

        this.phaser = p;
        this.name = n;
        phaser.register();//вот тут мы регистрриуем текущий поток участником фазера
    }
    //метод run() гарантирован интерфейсом Runnable и содержит код, который вылонтися в потоке
    public void run() {
        for (int i = 0; i <= 5; i++) {//пусть у нас будет 6 фаз для каждого потока
            phase();
        }
        System.out.println("фазы закончились, поток " + name + " отпустил фазер на " + phaser.arriveAndDeregister() + " фазе");
    }//run

    private void phase(){
        System.out.println(name + " выполняет фазу " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance(); // сообщаем о завершении фаз и удаляем с регистрации объекты
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}//PhaseThread
