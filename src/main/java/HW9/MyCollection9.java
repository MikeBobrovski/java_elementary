/*
на данный момент не реализованы методы
    public Object[] toArray(Object[] objects)
 */

package HW9;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MyCollection9 implements Collection {

    private Object[] data;
    private int currentSize;
    private final int MULTIPLIER = 2;
    private final int MAX_SIZE = 1000;

    public MyCollection9() {
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
        Object[] res = new Object[this.currentSize];
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


    public int[] find(Object arg) {
        if (arg == null) return null;
        int resSize = 0;
        //определение размера результирующего массива
        for (Object Item : this) {
            if (Item.equals(arg)) {
                resSize++;
            }
        }
        System.out.println(arg + " найдено " + resSize + " раз. индексы элементов, равных искомому:");
        if (resSize == 0) {
            return new int[0];
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

   /* public boolean serch(int arg){
        Object[] arr = this.toArray();
        Integer[] ar = (Integer[]) arr;
        int [] a = new int [ar.length];
        for(int i : ar){
            a[]
        }
        //int[] a = (int[]) ar;
        int res = binarySearch(ar, 0, arr.length - 1, arg);
        return res != -1;
    }*/

    private boolean swap(){
        return false;
    }

    public int binarySearch (Number[] arr, int l, int r, int x) {

            if (r >= l) {
                int mid = l + (r - l) / 2;

                if (arr[mid].equals(x))
                    return mid;

                // If element is smaller than mid, then
                // it can only be present in left subarray
                if (arr[mid].compare(x))
                    return binarySearch(arr, l, mid - 1, x);

                // Else the element can only be present
                // in right subarray
                return binarySearch(arr, mid + 1, r, x);
            }

            // We reach here when element is not present
            // in array
            return -1;


    }//find

    public void sort(){//метод - костыль, непонятно как сортировать объекты не зная их внутреннее устройство
        Object[] arr = this.toArray();
        Arrays.sort(arr);
        this.data = arr;
    }

    /*
        public void sort(Object[] source, int leftBorder, int rightBorder){
            int leftMarker = leftBorder;
            int rightMarker = rightBorder;
    source[0] source[0].getClass()
            int pivot = source.//source[(leftMarker + rightMarker) / 2];


        }
    */
    public static void qsort(int[] source, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;

        int pivot = source[(leftMarker + rightMarker) / 2];
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (source[leftMarker] < pivot) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (source[rightMarker] > pivot) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                    int tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);
        if (leftMarker < rightBorder) {
            qsort(source, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            qsort(source, leftBorder, rightMarker);
        }
    }


}

 /*public int [] find(int arg){
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
    }//find*/