/*

public Container(int[] arg) { this.content = arg; } - исправил, теперь беру значения, а не ссылку

-1 - согласен. по сути, массив размерности 0 и массив НУЛЛ для пользователя  - одно и то же.
тогда пусть будет 0 - public int getSize()  стр 195
скобки добавил





контейнер инт на базе массива, в ячейках значения, индекс - индекс массива

контракт - паблик методы, которые требуются в описании задачи

в каждом методе есть какая-никакая валидация, если она не пройдена,
вызывается метод еррор, который описывает рекцию на проблему. аргумент метода булин.
если правда - то бросается исключение с именем метода, из которого оно брошено, если ложь - выдает сообщение в консоль

максимальная длина массива огранмичена MAX_SIZE. в этом нет необходимости, просто придумал себе условие, чтоб на него проверять только в целях тренеровки

после конца класса есть закомментированый метод. решил, что не нужен, но оставил

по поводу сортировки. реализована пузырьком. понятно, что есть все это https://habr.com/ru/post/335920/, но пока так)
 */

package HW5;

import java.util.Arrays;

public class Container {
    //--------------------------------------------------------поля, контструктор, хелперы
    private int[] content;
    final static int MAX_SIZE = 200;

    public Container(int size) {
        if (size >= 0) content = new int[size];
        else error(true);
    }//конструктор

    public Container() {
        this.content = new int[0]; //или НУЛЛ?
    }//конструктор

    public Container(int[] arg) {
        int[] tmp = new int[arg.length];
        for (int i = 0; i < arg.length; i++) {
            tmp[i] = arg[i];
        }
        this.content = tmp;
    }//конструктор

    private void error(boolean Critical) {
        //сообщение пожно получать аргументом, но пока это не критично
        final String methodName = Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length - 2].getMethodName();
        String message = "problem occurred in \"" + methodName + "\" method.";
        if (!Critical) {
            System.out.println(message);
        } else {
            throw new IllegalArgumentException(message);
        }
    }//помощник для реакции на ошибку

    @Override
    public String toString() {
        return Arrays.toString(content);//как получить имя переменной, в который лежит ссылка на this, от которого вызван toString?
    }//если использование Arrays.toString не предполагалось, то организовал бы через for (el :this.content) res += el + " "

    //--------------------------------------------------------контракт
    public void add(int element) {
        if (this.content == null) {
            this.content = new int[1];
            this.content[0] = element;
            return;
        } else {
            int[] old = this.content;
            int[] res = new int[old.length + 1];
            for (int i = 0; i < old.length; i++) res[i] = old[i];
            res[res.length - 1] = element;
            this.content = res;
        }
    }//add

    public void addIndex(int element, int index) {
        if (this.content == null || index < 0 || index > this.content.length) {//хороший пример применения | и ||, как и & и &&: если после первого условия будет одинарный ИЛИ, возможно исключение НуллПоинтер
            error(false);
            return;
        }
        int[] old = this.content;
        int[] res = new int[old.length + 1];

        for (int i = 0; i < index; i++) {
            res[i] = old[i];
        }
        res[index] = element;
        for (int i = index + 1; i < old.length + 1; i++) {
            res[i] = old[i - 1];
        }// или i < res.length

        this.content = res;
    }//addIndex

    public int get(int index) {
        if (index > 0 && index < this.content.length - 1) {
            return this.content[index];
        } else {
            error(true);
        }
        return 0;
    }//get

    public boolean contains(int element) {
        if (this.content == null) {
            error(false);
            return false;
        }//можно весь код ниже написатиь через елс, но, если иф сработает, то до нгего и так не дойдет
        boolean res = false;
        for (int value : this.content) {
            if (value == element) {
                res = true;
                break;
            }
        }
        return res;
    }//contains

    public Container addAll(Container added) {
        if (this.content == null) {
            error(false);//хотя, здесь не должно быть ошибки, потому что к ничего можно прибавить что-то
            return added;
        }//можно весь код ниже написатиь через елс, но, если иф сработает, то до нгего и так не дойдет
        if (this.content.length + added.content.length > MAX_SIZE) error(true);
        int[] res = new int[this.content.length + added.content.length];
        for (int i = 0; i < this.content.length; i++) {
            res[i] = this.content[i];
        }
        for (int i = this.content.length; i < this.content.length + added.content.length; i++) {
            res[i] = added.content[i - this.content.length];
        }
        return new Container(res);// добавить по индексу реализовывать нет смысла в учебной задаче, но идея такая же, как для одного элемента
    }//addAll

    public boolean equals(int index, int element) {
        if (this.content == null || index < 0 || index > this.content.length - 1) {
            error(true);
        }
        return this.content[index] == element;
    }//equals for element

    public boolean equals(Container arg) {
        if (arg == null) {
            error(false);
            return false;
        }
        if (arg.content == null | this.content == null) {
            error(false);
            return arg.content == null & this.content == null;
        }
        boolean res = this.content.length == arg.content.length;
        for (int i = 0; i < this.content.length; i++) {
            if (!(res && this.content[i] == arg.content[i])) {
                res = false;
                break;
            }
        }
        return res;
    }//equals for another instance of Container

    public void clear() {
        //проверка на this == nul?? не нужно потому, что от нула не вызовется
        this.content = null;
        //this.content = new int[0]; //как альтернатива
    }//clear

    public int indexOf(int element) {
        if (this.content == null) {
            error(false);
            return -1;
        }
        for (int i = 0; i < this.content.length; i++) {
            if (this.content[i] == element) return i;
        }
        return -1;
    }// indexOf

    public int endIndexOf(int element) {
        if (this.content == null) {
            error(false);
            return -1;
        }
        for (int i = this.content.length - 1; i >= 0; i--) {
            if (this.content[i] == element) return i;
        }
        return -1;
    }// indexOf

    public int getSize() {
        if (this.content == null) {
            error(false);
            return 0;//отрицательная длина явный признак ошибки. на случай, если не будем бросать исключение в данном случае
        } else {
            return this.content.length;
        }
    }//getSize

    public void sort() {
        if (this.content == null) {
            error(false);
            return;
        }
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < this.content.length - j; i++) {
                if (this.content[i] > this.content[i + 1]) {
                    tmp = this.content[i];
                    this.content[i] = this.content[i + 1];
                    this.content[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }//sort
//--------------------------------------------------------/контракт


}//Container

/*
     public boolean equals(int[] arg) {
        if (arg == null | this.content == null) {
            error(false);
            return arg == null & this.content == null;
        }
        boolean res = this.content.length == arg.length;
        for (int i = 0; i < this.content.length; i++)
            if (!(res && this.content[i] == arg[i])) {
                res = false;
                break;
            }
        return res;
    }//equals for whole array
*/