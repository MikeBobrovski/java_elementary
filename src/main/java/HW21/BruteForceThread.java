/**
 * поскольку самое тяжелое - перебор, то можно увеличить эффективность, проверяя сразу массив хэшей
 * так же можно дописать паузу, при наступлении которой текущий пароль из каждого потока будет писаться в файл, а потом с этого места стартовать
 * так же можно дописать какое-то логироваие по времени/знакоместу. тоже в файл. на случай, если свет погас
 * но все это не в рамках учебной задачи на совсем другую тему (как я это понял)
 */

package HW21;

import Util.TimeHandler;

import java.util.concurrent.Callable;

public class BruteForceThread implements Callable<String> {
    public static final boolean debug = false;//говорит само за себя

    private String start;
    private String stop;
    final String hash;
    char[] alphabet;
    private final char[] chars;

    public BruteForceThread(final String start, final String stop, final String hash, final char[] alphabet, final int currLength) {
        if (start.length() != currLength || stop.length() != currLength)
            throw new IllegalArgumentException("bad argument");
        this.start = start;
        this.stop = stop;
        this.hash = hash;
        this.alphabet = alphabet;
        //одно лишнее место в массиве, изменение которого означает переполнение и завершение процесса
        chars = (" ".concat(start)).toCharArray();
    }//constructor

    public String call() {
        TimeHandler th = new TimeHandler();
        th.startT();
        Util.Logger.getLogger().log("в потоке: " + Thread.currentThread().getName() + ". от: \"" + start + "\"" + " до: \"" + stop + "\"", true);
        while (chars[0] == ' ') {
            String password = String.valueOf(chars).substring(1, chars.length);
            int beginPass = (String.valueOf(chars).lastIndexOf(' '));
            //берем подстроку, чтоб обрезать знаки, которые не были затронуты перебором на этот момент
            password = (beginPass > 0) ? password.substring(beginPass) : password;
            if (debug) System.out.println(password);
            if (hash.equals(Encryptor.md5(password))) {
                Util.Logger.getLogger().log("в потоке: " + Thread.currentThread().getName() + "pass = \"" + (password) + "\"" + ". затраченное время: " + th.stopT(), false);
                return password;
            }
            if (Thread.interrupted()) return password;//останов потоков извне. возвращает текущий пароль

            if (password.equals(stop)) {
                Util.Logger.getLogger().log("в потоке: " + Thread.currentThread().getName() + " NotFound", false);
                return null;
            }

            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] < alphabet[alphabet.length - 1]) {
                    //chars[i]++;//вот тут не инкремент того, что было, а след символ алфавита, если сейчас символ, е если неалфавитный символ, то начальное
                    if (chars[i] == ' ') {
                        chars[i] = alphabet[0];
                    } else {
                        chars[i]++;
                    }
                    break;
                }
                chars[i] = alphabet[0];
            }
        }
        return null;
    }//run


}

