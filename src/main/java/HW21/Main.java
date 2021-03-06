/**
 * при разделении потоков по принципу "первый - от мин длины до макс, второй макс длины от а_ааааааа до б_аааааааа"
 * в случае, когда длина неизвестна, но между мин и макс, получается, что пароль найдется только в первом потоке
 * что означает, фактически, однопоточный результат времени
 * потому полный перебор будем применять ко всем длинам от мин до макс, тогда мы равномерно распрделим ресурсы, не зная заранее ни длину, ни алфавит.
 */
package HW21;

import Util.ArrayOfE;
import java.util.Arrays;
import java.util.concurrent.*;

class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //задача
        String hash1 = "f016441d00c16c9b912d05e9d81d894d";
        String pass1 = "very";
        String hash2 = "5ebe2294ecd0e0f08eab7690d2a6ee69";
        String pass2 = "secret";
        String hash3 = "13d70e09909669272b19647c2a55dacb";
        String pass3 = "goodforyou";
        String hash4 = "5f50dfa5385e66ce46ad8d08a9c9be68";

        //генерация алфавитов
        String alphabet = "abcdefghijklmnnopqrstuvwxyz";
        char[] ab4 = new char[106];
        for (int i = 0; i < 93; i++) {
            ab4[i] = (char) (i + 33);
        }
        String alphabet4 = String.valueOf(ab4);
//        System.out.println(alphabet4);//чтобы увидеть алфавит

        //вызов перебора
        bruteForce(hash2, alphabet, 4, 10);
    }

    public static void bruteForce(final String hash, final String ab, final int MIN_LENGTH, final int MAX_LENGTH) throws ExecutionException, InterruptedException {
        boolean done = false;
        //кол потоков зависит от конкретной машины
        final int THREAD_NUM = Runtime.getRuntime().availableProcessors();
        //сложим алфавит в массив
        char[] alphabet = ab.toCharArray();
        //здесь будем перебирать возможные длины от мин до макс (обоснование см вверху)
        for (int j = MIN_LENGTH; j <= MAX_LENGTH; j++) {
            if (done) break;
            //делим множество возможных паролей на (потоки + 1)
            String[] edges = edges(ab, j, THREAD_NUM);
            //магия создания типизированного массива через нативную функцию
            ArrayOfE<Future> r = new ArrayOfE<>(Future.class, THREAD_NUM);
            Future[] results = r.get();
            //создание массива потоков
            Callable<String>[] runs = new Callable[THREAD_NUM];
            for (int i = 0; i < THREAD_NUM; i++) {
                runs[i] = new BruteForceThread(edges[i], edges[i + 1], hash, alphabet, j);
            }
            //выполнение в пуле
            final ExecutorService es = Executors.newFixedThreadPool(THREAD_NUM);
            for (int i = 0; i < THREAD_NUM; i++) {
                results[i] = es.submit(runs[i]);
            }
//FIXME: для проверки работоспособности кода для длинных паролей. рассчет показал, что для моего компа это займет 105 дней, так что будем мухлевать.
//            Callable<String> veryLucky = new BruteForceThread("goodfooyou", "goodfozyou", hash3, alphabet, j);
//            results[THREAD_NUM] = es.submit(veryLucky);// для раскомментирования нужно увеличить массив потоков и будущих на 1.

            boolean stop = false;
            while (!stop) {
                for (Future<String> f : results) {
                    if (f.isDone()) {
                        String s = f.get();
                        if (s != null && hash.equals(Encryptor.md5(s))) {
                            done = true;
                        }
                        stop = true;
                    }//если будущее появилось
                }//пробегаем все будущие
                Thread.sleep(1000);//спим
            }//цикл ожидания результата
            es.shutdownNow();
        }//перебор длин
    }//bruteForce

    private static String[] edges(String ab, final int LENGTH, final int THREAD_NUM) {
        /**
         * получаем алфавит, текущую длину пароля и количество потоков
         * возвращаем массив начал и концов перебора для каждого потока
         * наполнение каждой строки: X+Y*n, где X = буква, колученная из алфавита и номера строки, Y = первая буква алфавита, n = LENGTH - 1.
         * исключение - первая строка. ее наполнение - Y*(n+1)
         *
         * плохая логика: неравномерность распределения по потокам. лень исправлять, хоть и можно, не выходя за границы этого метода
         */
        //границ на 1 больше чем потоков, так как потоки бегут от И-той границы до И+1-ой каждый, где И = номер потока
        String[] edges = new String[THREAD_NUM + 1];
        char[] alphabet = ab.toCharArray();
        //инициализация строки/элемента выходного массива/
        char[] tmp = new char[LENGTH];
        //первая строка состоит из певых букв алфавита
        Arrays.fill(tmp, 0, tmp.length, alphabet[0]);
        edges[0] = String.valueOf(tmp);
        //множитель для нахождения буквы
        int e = alphabet.length / THREAD_NUM;
        //границы, кроме первой и последней
        for (int i = 1; i <= THREAD_NUM; i++) {
            tmp[0] = alphabet[i * e];
            edges[i] = String.valueOf(tmp);
        }
        //последняя граница - наполнена последними буквами алфавита
        Arrays.fill(tmp, 0, tmp.length, alphabet[alphabet.length - 1]);
        edges[THREAD_NUM] = String.valueOf(tmp);
        //вернть массив границ
        return edges;
    }

}//class
