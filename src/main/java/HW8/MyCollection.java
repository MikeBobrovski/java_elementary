/*
на данный момент не реализованы методы
    public Object[] toArray(Object[] objects)
 */

package HW8;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MyCollection implements Collection {

    private Object[] data;
    private int currentSize;
    private final int MULTIPLIER = 2;
    private final int MAX_SIZE = 1000;

    public MyCollection() {
        this.data = new Object[0];
        this.currentSize = 0;
    }

    //--------------------------------------------------------------------контракт

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        for (int i = 0; i < this.currentSize; i++) {//можно через вызов indexOf, и даже правильнее, но переписывать не буду
            if (this.data[i].equals(o)) return true;
            /*
             @Override
             public boolean equals(Object obj) {} mandatory for obj!
             */
        }
        return false;
    }

    @Override
    public boolean add(Object o) {
        if (o == null) return false;
        checkSize();
        this.data[currentSize++] = o;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        for (int i = 0; i <= this.currentSize; i++) {
            if (this.data[i].equals(o)) {
                return removeHelp(o);
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.data = new Object[0];
        this.currentSize = 0;
    }

    @Override
    public boolean addAll(Collection arg) {
        if (arg == null) return false;
        /*Iterator iter = arg.iterator();
        while (iter.hasNext()){ //почему идея предлагает это заменить на форич?
            checkSize();
            this.data[this.currentSize++] = iter.next();
        }*/
        for (Object o : arg) {
            checkSize();
            this.data[this.currentSize++] = o;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection other) {
        if (other == null) return false;
        int resSize = 0;
        //определение размера результирующего массива
        for (Object otherItem : other) {
            if (this.indexOf(otherItem) != -1) {
                resSize++;
            }
        }
        if (resSize == 0) {
            return false;
        } else {
            Object[] result = new Object[resSize];
            resSize = 0;//теперь используем для перебора результирующего массива
            // заполнение результирующего массива
            for (Object otherItem : other) {
                if (this.indexOf(otherItem) != -1) {
                    result[resSize++] = otherItem;
                }
            }
            //переприсвоение данных, текущего ращмера
            this.currentSize = resSize;
            this.data = result;
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection other) {
        boolean res = false;
        if (other == null) return res;
        for (Object otherItem : other) {
            if (this.indexOf(otherItem) != -1) {
                this.remove(this.data[this.indexOf(otherItem)]);
                res = true;
            }
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection other) {
        if (other == null) return false;
        boolean res = true;
        for (Object otherItem : other) {
            if (this.indexOf(otherItem) == -1) {
                res = false;
            }
        }
        return res;
    }


    @Override
    public Object[] toArray() {
        Object[] res = new Object[0];
        if (currentSize >= 0) System.arraycopy(this.data, 0, res, 0, currentSize);
        return res;
    }

    @Override
    public Object[] toArray(Object[] objects) {//здесь, вроде сокрытие не нарушается. вообще, очень непонятный метод: если все не влечет в массив, который есть, то что? ну ладно, обязан реализовать, так что вот
        if (objects.length >= this.toArray().length) objects = this.toArray();
        return objects;
    }

    @Override
    public Iterator iterator() {

        return new Iterator() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < currentSize;
            }

            @Override
            public Object next() {
                return data[current++];
            }
        };
    }



    //--------------------------------------------------------------------/контракт

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
        if (o == null) return -1;
        for (int i = 0; i < this.currentSize; i++) {
            if (this.data[i].equals(o)) {//если equals определен в классе содержимого коллекции - хорошо, нет - приедет обжектовый метод
                return i;//вернуть номер элемента равного аргументу
            }
        }
        return -1;//вернуть -1, если нет такого
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

    //--------------------------------------------------------------------домашка 9

    public int [] find(int arg){
        //if (arg == null) return null;
        int resSize = 0;
        //определение размера результирующего массива
        for (Object Item : this) {
            if (Item.equals(arg)) {
                resSize++;
            }
        }
        System.out.println(arg + " найдено " + resSize + " раз. индексы элементов, равных искомому:");
        if (resSize == 0) {
            return new int [0];
        } else {
            int[] result = new int[resSize];
            resSize = 0;//теперь используем для перебора результирующего массива
            // заполнение результирующего массива
            for (int i = 0; i < this.size(); i++) {
                if (this.data[i].equals(arg)) {
                    result[resSize++] = i;
                }
            }
            return result;
        }
    }//find

}


    /*public boolean addAll(MyCollection arg) {
        //дописывает коллекцию в конец существующей
        //сложить даты, увеличить текущий объем, вернуть правду, если все ок

        if (arg == null) return false;
        for (int i = 0; i < arg.size(); i++) {
            checkSize();
            this.data[this.currentSize++] = arg.data[i];
        }
        return true;
    }


    public boolean retainAll(MyCollection other) {
        //переписать полностью через вызов удалить для тех из этот, которых нет в другой
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

    *//*public boolean retainAll(MyCollection other) {//переписать полностью через вызов удалить для тех из этот, которых нет в другой
        //есть 1234, арг 45, выход - 4 - записать в этот, т е, пересечение множеств
        //сождать результирующую дату, пребрать во вложенных форах каждый элем другого для каждого элем этого, записать все совпашие, присвоить рех жату этому, вернуть правду
        if (other == null) return false;

        for (int i = 0; i < this.size(); i++) {
            if (other.indexOf(this.data[i]) == -1) {
                this.remove(this.data[i]);
            }
        }
        return true;
    }//работает криво*//*

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

    *//*public boolean removeAll(MyCollection other) {
        if (other == null) return false;
        for (int i = 0; i < this.size(); i++) {
            if (other.indexOf(this.data[i]) != -1) {
                this.remove(this.data[i]);
            }
        }//так, вроде, правильнее, но пропускает 1 элемент, видимо, последний
        return true;
    }*//*

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
    }*/

 /*@Override
    public boolean containsAll(Collection args) {
        if (args == null) return false;
        boolean res = true;

        args.forEach(a -> {
                    if (this.indexOf(a) == -1) {
                        res = false;
                    }
                }
        );

        return res;
    }*/

   /* @Override
    public boolean removeAll(Collection arg) {
        if (arg == null) return false;
        if (!(arg instanceof MyCollection)) return false;
        MyCollection other = (MyCollection) arg;

        for (int i = 0; i < other.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (this.data[j].equals(other.data[i])) {// лучше - через indexOf, как в containsAll
                    this.remove(this.data[j]);
                }
            }
        }
        return true;
    }*/