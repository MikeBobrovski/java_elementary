/*
пишем через массив. это на случай частого доступа к элементам и нечастого добавления (доступ мгновенно, добавлять дорого)
если изменение размера происходит часто, а извлечение данных редко, то это выгоднее реализовать через всязный список (доступ О(n), добавлять недорого)

 */

package HW7;

import java.util.Arrays;

public class MyCollection {

    private Object[] data;
    private int currentSize;
    private final int MULTIPLIER = 2;
    private final int MAX_SIZE = 1000;

    public MyCollection() {
        this.data = new Object[0];
        this.currentSize = 0;
    }

    public int size() {
        return this.currentSize;
    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    public boolean contains(Object o) {
        if (o == null) return false;
        for (int i = 0; i < this.currentSize; i++) {
            if (this.data[i].equals(o)) return true;
            /*
             @Override
             public boolean equals(Object obj) {} mandatory!
             */
        }
        return false;
    }

    public boolean add(Object o) {
        if (o == null) return false;
        checkSize();
        this.data[currentSize++] = o;
        return true;
    }

    private void checkSize() {
        if (currentSize >= data.length) {
            if (data.length == 0) {
                this.data = new Object[1];
            }
            this.data = grow();
        }
    }

    private Object[] grow() {
        if (this.data.length * 2 > MAX_SIZE) throw new OutOfMemoryError();
        return this.data = Arrays.copyOf(this.data, this.data.length * MULTIPLIER);
    }

    public boolean remove(Object o) {
        if (o == null) return false;
        for (int i = 0; i <= this.currentSize; i++) {
            if (this.data[i].equals(o)) {
                return removeHelp(o);
            }
        }
        return false;
    }

    private boolean removeHelp(Object o) {
        //TODO непонятно, зачем мне этот хелпер. потерял мысль, пока писал. если не вспомню, то сключить его
        if (indexOf(o) == -1) return false;
        for (int i = indexOf(o); i < this.currentSize - 1; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.currentSize--;
        return true;
    }

    private int indexOf(Object o) {
        if (o == null) return -1;//мож, стоит бросать исключение? потом видно будет

        for (int i = 0; i < this.currentSize; i++) {
            if (this.data[i].equals(o)) {
                return i;//вернуть номер элемента равного аргументу
            }
        }
        return -1;//вернуть -1, если нет такого
    }


    public boolean addAll(MyCollection arg) {
        //дописывает коллекцию в конец существующей
        //сложить даты, увеличить текущий объем, вернуть правду, если все ок

        if (arg == null) return false;
        for (int i = 0; i < arg.size(); i++) {
            checkSize();
            this.data[this.currentSize++] = arg.data[i];
        }
        return true;
    }

    public void clear() {
        this.data = new Object[0];
        this.currentSize = 0;
    }

    public boolean retainAll(MyCollection other) {//переписать полностью через вызов удалить для тех из этот, которых нет в другой
        //есть 1234, арг 45, выход - 4 - записать в этот, т е, пересечение множеств
        //сождать результирующую дату, пребрать во вложенных форах каждый элем другого для каждого элем этого, записать все совпашие, присвоить рех жату этому, вернуть правду
        if (other == null) return false;
        int resSize = 0;
        for (int i = 0; i < other.size(); i++) {
            if (this.indexOf(other.data[i]) != -1) {
                resSize++;
            }
        }
        if (resSize == 0) {
            return false;
        } else {
            Object[] result = new Object[resSize];

            resSize = 0;//теперь используем для перебора результирующего массива
            for (int i = 0; i < this.size(); i++) {
                if (other.indexOf(this.data[i]) != -1) {
                    result[resSize++] = this.data[i];
                }
            }
            this.currentSize = resSize;
            this.data = result;
            return true;
        }

    }

    /*public boolean retainAll(MyCollection other) {//переписать полностью через вызов удалить для тех из этот, которых нет в другой
        //есть 1234, арг 45, выход - 4 - записать в этот, т е, пересечение множеств
        //сождать результирующую дату, пребрать во вложенных форах каждый элем другого для каждого элем этого, записать все совпашие, присвоить рех жату этому, вернуть правду
        if (other == null) return false;

        for (int i = 0; i < this.size(); i++) {
            if (other.indexOf(this.data[i]) == -1) {
                this.remove(this.data[i]);
            }
        }
        return true;
    }//работает криво*/

    public boolean removeAll(MyCollection other) {
        if (other == null) return false;
        for (int i = 0; i < other.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (this.data[j].equals(other.data[i])) {// лучше - через indexOf, как в containsAll
                    this.remove(this.data[j]);
                }
            }
        }
        return true;
    }

    /*public boolean removeAll(MyCollection other) {
        if (other == null) return false;
        for (int i = 0; i < this.size(); i++) {
            if (other.indexOf(this.data[i]) != -1) {
                this.remove(this.data[i]);
            }
        }//так, вроде, правильнее, но пропускает 1 элемент, видимо, последний
        return true;
    }*/

    public boolean containsAll(MyCollection other) {
        //вернуть правду если все элементы другой присутствуют в этой
        if (other == null) return false;
        boolean res = true;
        for (int i = 0; i < other.size(); i++) {
            if (this.indexOf(other.data[i]) == -1) {
                res = false;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        if (this.currentSize == 0) {
            return "MyCollection is empty";
        }
        String res = "MyCollection contains following items:\n";
        for (int i = 0; i < currentSize; i++) {
            res += this.data[i].toString() + "\n";
        }
        return res;
    }


}
