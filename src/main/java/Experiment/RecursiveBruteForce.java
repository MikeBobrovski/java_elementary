package HW21;


import java.util.Arrays;

public class RecursiveBruteForce {
    //    public static final boolean debug = true;
    public static final boolean debug = false;

    public static void main(String args[]) {
        //алфавит
        char[] alphabet = "abcdefghijklmnnopqrstuvwxyz".toCharArray();
        //хэш
        String hash1 = "f016441d00c16c9b912d05e9d81d894d";
        //максимальная длина (она же глубина рекрусии)
        final int MAX_LENGTH = 10;
        //собственно вызов
        startSearch(hash1, alphabet, MAX_LENGTH);
    }

    static void startSearch(String hash, char[] alphabet, int maxLength) {
        //"глобойльное" , в рамках рекурсии, хранилище перебираемого пароля
        char[] current = new char[maxLength];
        for (int i = 0; i < current.length; i++) {
            current[i] = '-';//символ вне алфавита, как признак неизмененного разряда
        }
        recursiveSearch(hash, alphabet, maxLength, current, 0, 0);
    }//startSearch

    static void recursiveSearch(String hash, char[] alphabet, int maxLength, char[] current, int currCharPosInCurPass, int currLength) {
        System.out.println("рекурсия 1. текущее знакоместо: " + currCharPosInCurPass + " , текущая длина: " + currLength + " , текущий пароль: " + Arrays.toString(current));
        //----------------------увеличить длину предполагаемого пароля, если все варианты для этой длины пребрали
        //если весь текущий пароль состоит из Z, то добавить разряд, установить все разряды в А
        boolean increaseDepth = true;
        for (int i = 0; i < currLength; i++) {
            if (current[i] != 'z') {
                increaseDepth = false;
                break;
            }
        }
        if (increaseDepth) {
            currLength++;
            for (int i = 0; i < currLength; i++) {
                current[i] = 'a';
            }
        }
        //----------------------условие выхода: максимальная длина пароля достигнута
        if (currLength >= maxLength) {
            System.out.println("can't find pass");
            return;
        }

        System.out.println("рекурсия 2. текущее знакоместо: " + currCharPosInCurPass + " , текущая длина: " + currLength + " , текущий пароль: " + Arrays.toString(current));
        //если текущее знакоместо перебираемого пароля = длине, то мы в крайнем знакоместе. перебираем его по алфавиту, сравниваем
        if (currCharPosInCurPass == currLength) {
            System.out.println("мы на последнем знакоместе? да");
            // то мы на последнем знакоместе. перебор последнего и проверка совпедения перебираемого пароля с текущим
            for (char c : alphabet) {
                //перебор последнего знакоместа для этой глубины
                current[currCharPosInCurPass] = c;
                String password = String.valueOf(current);
                int endPass = (String.valueOf(current).indexOf('-'));
                //берем подстроку, чтоб обрезать знаки, которые не были затронуты перебором на этот момент
                password = (endPass > 0) ? password.substring(0, endPass) : password;
                //if (debug) System.out.println(password);
                System.out.println("---------------итерация. текущее знакоместо: " + currCharPosInCurPass + " , текущая длина: " + currLength + " , текущий пароль: " + Arrays.toString(current));
                //----------------------условие выхода: текущий пароль подошел
                if (hash.equals(Encryptor.md5(password))) {
                    System.out.println("pass =" + (password));
                    return;
                }
            }//for(last char:  alphabet) для последнего знакоместа
            System.out.println("после итерации 1. текущее знакоместо: " + currCharPosInCurPass + " , текущая длина: " + currLength + " , текущий пароль: " + Arrays.toString(current));
            current[currCharPosInCurPass] = 'a';//первый символ алфавита
            currLength++;
            currCharPosInCurPass++;
            System.out.println("после итерации 2. текущее знакоместо: " + currCharPosInCurPass + " , текущая длина: " + currLength + " , текущий пароль: " + Arrays.toString(current));
        } else {
            System.out.println("мы на последнем знакоместе? нет");
            //сюда мы зашли если текущая глубмна != глубини, тогда
            //если текущий символ для этой текущей глубины НЕ равен концу алфавита
            if (current[currCharPosInCurPass] < 'z') {
                System.out.println("текущее знакоместо < z? да");
                //то инкремент текущего символа, и рекурсия с увеличением текущей глубины
                current[currCharPosInCurPass]++;
                currCharPosInCurPass++;
            } else {
                System.out.println("текущее знакоместо < z? нет");
                //если РАВЕН, это на это знакоместо поставим первый символ, и рекурсивно зайдем с уменьшением текущего знакоместа
                current[currCharPosInCurPass] = 'a';//первый символ алфавита
                currCharPosInCurPass--;
            }
        }//if currCharPosInCurPass != currLength
        recursiveSearch(hash, alphabet, maxLength, current, currCharPosInCurPass, currLength);

    }//recursiveSearch

}//class RecursiveBruteForce



/*
static void recursiveSearch(String hash, char[] alphabet, char[] current, int depth, int depthCur, int maxDepth) {

        //----------------------условие выхода: максимальная глубина достигнута
        if (depth >= maxDepth) {
            System.out.println("can't find pass");
            return;
        }


        //----------------------действие: перебор этого знакоместа (=уровню)
        if (depthCur == depth){
            //если текущая глубина = глубине, то мы на последнем знакоместе. перебор последнего и проверка совпедения
        } else {
            //иекремент текоущего и вызов с depthCur++
            //если =z, то вызов с depthCur--
        }
        for (char c : alphabet) {
            current[depth] = c;
            String password = String.valueOf(current);
            int endPass = (String.valueOf(current).indexOf('-'));
            //берем подстроку, чтоб обрезать знаки, которые не были затронуты перебором на этот момент
            password = (endPass > 0) ? password.substring(0, endPass) : password;
            if (debug) System.out.println(password);
            //----------------------условие выхода: текущий пароль подошел
            if (hash.equals(Encryptor.md5(password))) {
                System.out.println("pass =" + (password));
                return;
            }

        }


        if (depthCur <= depth) {
            //если текущее перебираемое знакоместо последнее, то увеличить глубину и передирать с начала
            recursiveSearch(hash, alphabet, current, ++depth, ++depthCur, maxDepth);
        } else {
            //если текущее перебираемое знакоместо НЕ последнее, то перебирать предыдущее знакоместо, глубину не увеличивать
            recursiveSearch(hash, alphabet, current, depth, --depthCur, maxDepth);
        }

    }//recursiveSearch
 */


/*
 static void recursiveSearch(String hash, char[] alphabet, int maxLength, char[] current, int currCharPosInCurPass, int currLength) {

        //----------------------увеличить глубину, если все аврианты на этой глубине пребрали
        boolean increaseDepth = true;
        for (char c : current) {
            if (c != 'z') {
                increaseDepth = false;
                break;
            }
        }
        if (increaseDepth) currLength++;
        //----------------------условие выхода: максимальная глубина достигнута
        if (currLength >= maxLength) {
            System.out.println("can't find pass");
            return;
        }



        //если текущее знакоместо перебираемого пароля = глубине,
        if (currCharPosInCurPass == currLength) {

            // то мы на последнем знакоместе. перебор последнего и проверка совпедения перебираемого пароля с текущим
            for (char c : alphabet) {
                //перебор последнего знакоместа для этой глубины
                current[currCharPosInCurPass] = c;
                String password = String.valueOf(current);
                int endPass = (String.valueOf(current).indexOf('-'));
                //берем подстроку, чтоб обрезать знаки, которые не были затронуты перебором на этот момент
                password = (endPass > 0) ? password.substring(0, endPass) : password;
                if (debug) System.out.println(password);
                //----------------------условие выхода: текущий пароль подошел
                if (hash.equals(Encryptor.md5(password))) {
                    System.out.println("pass =" + (password));
                    return;
                }
            }//for(last char:  alphabet) для последнего знакоместа
            current[currCharPosInCurPass] = 'a';//первый символ алфавита
            currLength++;
            recursiveSearch(hash, alphabet, maxLength, current, currCharPosInCurPass, currLength);

        } else {
            System.out.println("dsadadadasdada");
            //сюда мы зашли если текущая глубмна != глубини, тогда
            //если текущий символ для этой текущей глубины НЕ равен концу алфавита
            if (current[currCharPosInCurPass] < 'z') {
                //то инкремент текущего символа, и рекурсия с увеличением текущей глубины
                current[currCharPosInCurPass]++;
                currCharPosInCurPass++;
                recursiveSearch(hash, alphabet, maxLength, current, currCharPosInCurPass, currLength);
            } else {
                //если РАВЕН, это на это знакоместо поставим первый символ, и рекурсивно зайдем с уменьшением текущего знакоместа
                current[currCharPosInCurPass] = 'a';//первый символ алфавита
                currCharPosInCurPass--;
                recursiveSearch(hash, alphabet, maxLength, current, currCharPosInCurPass, currLength);
            }
        }//if currCharPosInCurPass != currLength

    }//recursiveSearch
 */

