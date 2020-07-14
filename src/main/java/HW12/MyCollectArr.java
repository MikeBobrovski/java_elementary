package HW12;

import java.util.*;

public class MyCollectArr implements List {

    private Object[] data;
    private int currentSize;
    private final int MULTIPLIER = 2;
    private final int MAX_SIZE = 1000;

    public MyCollectArr() {
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
        checkSize(1);
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
            checkSize(1);
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


    @Override
    public boolean addAll(int index, Collection collection) {
        /*
        добавляет элемент по индексу. не перетерает, а сдвигает
        передача поэлементно
         */
        if (index >= this.currentSize) throw new IndexOutOfBoundsException("no such index");
        checkSize(collection.size());
        Object[] tailSubArr = Arrays.copyOfRange(this.data, index, this.currentSize);
        //System.out.println(Arrays.toString(this.data));
        for (Object next : collection) {
            this.data[index++] = next;
        }
        for (Object next : tailSubArr) {
            this.data[index++] = next;
        }
        this.currentSize += collection.size();
        return false;
    }

    @Override
    public void add(int index, Object o) {
        if (index >= this.currentSize) throw new IndexOutOfBoundsException("no such index");
        if (o == null) return;
        checkSize(1);
        //TODO попробовать с перебором даты, а то дорого по затратам памяти, если добавлять в начальные индексы длинного массива
        Object[] tailSubArr = Arrays.copyOfRange(this.data, index, this.currentSize);//this.data.length - 1
        this.data[index++] = o;
        for (Object next : tailSubArr) {
            this.data[index++] = next;
        }
        this.currentSize++;
    }

    @Override
    public Object get(int index) {
        return this.data[index];
    }

    @Override
    public Object set(int index, Object o) {
        Object tmp = this.data[index];
        this.data[index] = o;
        return tmp;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) return -1;
        for (int i = 0; i < this.currentSize; i++) {
            if (this.data[i].equals(o)) {//если equals определен в классе содержимого коллекции - хорошо, нет - приедет обжектовый метод
                return i;//вернуть номер элемента равного аргументу
            }
        }
        return -1;//вернуть -1, если нет такого
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) return -1;
        for (int i = this.currentSize - 1; i >= 0; i--) {
            if (this.data[i].equals(o)) {//если equals определен в классе содержимого коллекции - хорошо, нет - приедет обжектовый метод
                return i;//вернуть номер элемента равного аргументу
            }
        }
        return -1;//вернуть -1, если нет такого
    }

    @Override
    public Object remove(int index) {
        Object tmp = this.data[index];
        for (int i = index; i < this.currentSize; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.currentSize--;
        return tmp;
    }

    @Override
    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator listIterator(int startIndex) {
        // listIterator() должен вызывать под капотом этот иетератор с параметром 0, чтоб был сухой
        return new ListIterator() {
            private int current = startIndex;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return current < currentSize;
            }

            @Override
            public Object next() {
                lastRet = current;
                return data[current++];
            }

            @Override
            public boolean hasPrevious() {
                //Logger.getLogger().log("текущий индекс: " + current);
                return current > startIndex;// current > 0
            }

            @Override
            public Object previous() {
                Object toReturn = data[--current];
                lastRet = current;
                return toReturn;
            }

            @Override
            public int nextIndex() {
                //The above method is used to return the index of the element which is returned by the next() method
                //если был возвращен, то указатель уже инкрементирован, значит -1
                return current;
            }

            @Override
            public int previousIndex() {
                return current - 1;
            }

            @Override
            public void remove() {
                for (int i = lastRet; i < currentSize - 1; i++) {
                    data[i] = data[i + 1];
                    data[currentSize] = null;
                }
                currentSize--;
                current--;
            }

            @Override
            public void set(Object o) {
                data[lastRet] = o;
            }

            @Override
            public void add(Object o) {
                //вставить до того, который вернет next ()
                //next () это не затронет
                //previous () его вернет
                // nextIndex и previousIndex увеличатся на 1
                MyCollectArr.this.add(lastRet, o);
                current++;
            }
        };
    }

    @Override
    public List subList(int startIndex, int stopIndex) {
        //вот тут совсем непонятно что нужно
        return new SmallerList(this, startIndex, stopIndex);
    }

    @Override
    public void sort(Comparator c) {
/*
        String nameC = c.getClass().getName();
        nameC = nameC.substring(0, nameC.indexOf("$"));
        System.out.println("Comparator: " + c.getClass());


        for (Object o : this) {
            //System.out.println("текущий элем коллекции является потомком: " + o.getClass().getSuperclass()); //работает, но вернет ожект, если не вложен
            System.out.println("текущий элем коллекции " + o + " является объектом: " + o.getClass());

            //System.out.println("Collected item: " + o.getClass() + " is belong to Comparators: "  + o.getClass().isInstance(c.getClass()));

            //String nameThisNext = o.getClass().getSuperclass().getName();
            //System.out.println(nameThisNext +", "+ nameC.equals(nameThisNext));
        }*/

/*из обсуждения в группе понял, что сравнить несравнимое тяжело и незачем, так что сортировка предполагает однородность коллекции
реализовывать свою сортировку смысла не вижу потому что до меня сделано лучше
 */

        Arrays.sort(this.data, c);
    }


    //--------------------------------------------------------------------/контракт
    public class SmallerList implements List {
//FIXME вот прям все методы нужно реализовать???
        List parentList = null;
        int startPosition = 0;
        int endPosition = 0;

        public SmallerList(List parentList, int startPosition, int endPosition){
            this.parentList = parentList;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        // overwrite some directly to appear smaller
        public int size(){
            return endPosition-startPosition;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }


        public void add(int index, Object object){
            parentList.add(index + startPosition, object);
        }

        public boolean contains (Object object){
            for (int i = startPosition; i < endPosition; i++){
                if (parentList.get(i).equals(object)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator listIterator() {
            return null;
        }

        @Override
        public ListIterator listIterator(int i) {
            return null;
        }

        @Override
        public List subList(int i, int i1) {
            return null;
        }



        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(Collection collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Object get(int i) {
            return null;
        }

        @Override
        public Object set(int i, Object o) {
            return null;
        }

        @Override
        public boolean retainAll(Collection collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection collection) {
            return false;
        }

        @Override
        public boolean containsAll(Collection collection) {
            return false;
        }

        @Override
        public Object[] toArray(Object[] objects) {
            return new Object[0];
        }

    }

    private void checkSize(int addLength) {
        if (currentSize + addLength >= data.length) {
            if (data.length == 0) {
                this.data = new Object[1];
            }
            while (currentSize + addLength >= data.length) {
                //FIXME неоптимально. нужно пробрасывать в метод grow() на сколько вырвсти и там просчитывать новый размер перед Arrays.copyOf
                //но и так пока работает
                this.data = grow();
            }
        }
    }

    private Object[] grow() {
        if (this.data.length * 2 > MAX_SIZE) throw new OutOfMemoryError();
        return this.data = Arrays.copyOf(this.data, this.data.length * MULTIPLIER);
    }

    private boolean removeHelp(Object o) {
        //TODO переписать для вызова в итераторах
        if (indexOf(o) == -1) return false;
        for (int i = indexOf(o); i < this.currentSize - 1; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.data[currentSize] = null;//если не перетереть явно, то ничего не страшного не случится, если не заглядывать, куда не просят. но после удаления в масиве данных дублируется последний не-нулл элемент
        this.currentSize--;
        return true;
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
