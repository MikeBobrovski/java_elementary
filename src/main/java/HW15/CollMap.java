
package HW15;

import HW8.MyCollection;

import java.util.*;

public class CollMap implements Map {

    private static final int NUM_BUCKETS = 32;
    public Entry[] data = new Entry[NUM_BUCKETS];

    private int bucket(Object key) {
        return Math.abs(key.hashCode()) % NUM_BUCKETS;
    }

    private void put(Object key, String value) {
        int index = bucket(key);
        Entry last = data[index];
        data[index] = new Entry(last, key, value);
    }

    private String get(Student key) {
        int index = bucket(key);
        Entry current = data[index];

        while (current != null) {
            Object o = current.key;
            if (o.equals(key)) {
                return current.value;
            } else {
                current = current.next;
            }
        }
        return null;
    }//get

    @Override
    public int size() {
        int counter = 0;
        for (Entry current : data) {
            if (current != null) {
                do {
                    counter++;
                } while (current.next != null);
            }
        }
        return counter;
    }//size

    @Override
    public boolean isEmpty() {
        //return size() == 0;
        Boolean empty = true;
        for (Entry current : data) {
            if (current != null) {
                empty = false;
            }
        }
        return empty;
    }//isEmpty

    @Override
    public boolean containsKey(Object key) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        int index = bucket(key);
        Entry last = data[index];
        if (last == null) return false;
        do {
            if (last.key.equals(key)) return true;
        } while (last.next != null);
        return false;
    }//containsKey

    @Override
    public boolean containsValue(Object value) {
        if (value == null) throw new NullPointerException("not NULL argument required");
        for (Entry current : data) {
            if (current != null) {
                do {
                    if (current.value.equals(value)) return true;
                } while (current.next != null);
            }
        }
        return false;
    }//containsValue

    @Override
    public Object get(Object key) {
        return get((Student) key);
    }//get

    @Override
    public Object put(Object key, Object value) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        put(key, (String) value);
        return value;
    }//put

    @Override
    public Object remove(Object key) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        int index = bucket(key);
        Entry current = data[index];
        if (current != null && current.next == null) {
            //если в списке есть один элемент, то заменяем его на НУЛЛ и возвращаем его
            data[index] = null;
            return current.key;
        } else {
            //если в списке не один элемент, то запоминаем предыдущий. для того, чтобы удалить текущий, предыдущему в "некст" нужно записать "некст" для текущего
            Entry prev = null;
            while (current != null) {
                Object o = current.key;
                if (o.equals(key)) {
                    //если в списке есть один элемент, то заменяем его на НУЛЛ и возвращаем его
                    prev.next = current.next;
                    return current.key;
                } else {
                    prev = current;
                    current = current.next;
                }
            }
        }
        return null;
    }

    @Override
    public void putAll(final Map m) {
        if (m == null) throw new NullPointerException("not NULL argument required");
        Iterator iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();
            put(pairs.getKey(), pairs.getValue());
        }
    }

    @Override
    public void clear() {
        data = new Entry[NUM_BUCKETS];
    }

    @Override
    public Set keySet() {
        //FIXME не понял из описания, вернуть представление или новую сущность?? не должно же быть возможности мутировать ключи не мутируя значения??
        HashSet<Object> res = new HashSet<Object>();
        for (Entry current : data) {
            while (current != null) {
                res.add(current.key);
                current = current.next;
            }
        }
        return res;
    }

    @Override
    public Collection values() {
        //FIXME не понял из описания, вернуть представление или новую сущность?? не должно же быть возможности мутировать ключи не мутируя значения??
        Collection<Object> res = new MyCollection();
        for (Entry current : data) {
            while (current != null) {
                res.add(current.value);
                current = current.next;
            }
        }
        return res;
    }

    @Override
    public Set<Map.Entry> entrySet() {
        return new Set<Map.Entry>() {
            @Override
            public int size() {
                return CollMap.this.size();
            }

            @Override
            public boolean isEmpty() {
                return CollMap.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                //https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#contains-java.lang.Object- - тут сказано, (o==null ? e==null : o.equals(e)), что я понимаю, как "аргумент имеет право быть нулл", но переписывать уже не буду
                if (o == null) throw new NullPointerException("not NULL argument required");
                Object[] tmp = toArray();
                for (Object cur : tmp) {
                    if (cur.equals(o)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Iterator<Map.Entry> iterator() {
                return new Iterator<Map.Entry>() {
                    Entry currentNode = null;
                    int currentBucket = 0;
//исключение если вызвать его от НУЛЛа
                    {
                        for (int i = 0; i < NUM_BUCKETS; i++) {
                            if (data[i] != null) {
                                currentNode = data[i];
                                currentBucket = i;
                                break;
                            }
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        //если в текущем списке (который в бакете) у текущего есть следующий, то вернуть правду
                        if (currentNode.next != null) {
                            return true;
                        } else {
                            //если текущий список (читай бакет) закончился, то идем от текущего бакета до последнего, проверяем, на ненуловость содержимого (ищем след непустой элемент или их список, если коллизии были
                            for (int i = currentBucket; i < NUM_BUCKETS; i++) {
                                if (data[i] != null) {
                                    currentNode = data[i];  //когда нашли такой, то присвоили его текущей ноде,
                                    currentBucket = i + 1; //а номер бакета текущему
                                    return true; //и вернули правду. (можно было обойтись и без этой переменной, но пришлось бы всегда бегать по всему хранилищу и сравнивать с текущим. бред, короче)
                                }
                            }
                        }
                        //если мы дощли сюда, значит не-нуловых бакетов не нашлось и следующего нет, вернем ложь
                        return false;
                    }

                    @Override
                    public Map.Entry next() {
                        Map.Entry tmp = currentNode;
                        if (currentNode.next != null) {
                            currentNode = currentNode.next;
                        } else {
                            //пойти на след неНУЛЛ баккет (тут кусок кода очень похож на такой же в мотоде выше, но выход из цикла разнай, потому написать private static хелпер не рационально
                            for (int i = currentBucket; i < NUM_BUCKETS; i++) {
                                if (data[i] != null) {
                                    currentNode = data[i];  //когда нашли такой, то присвоили его текущей ноде,
                                    currentBucket = i; //а номер бакета текущему
                                    break; //и выйти из цикла
                                }
                            }
                        }
                        return tmp;
                    }
                };
            }

            @Override
            public Object[] toArray() {
                int i = 0;
                Object[] res = new Object[this.size()];
                for (Entry current : data) {
                    if (current != null) {
                        do {
                            res[i] = current;
                            i++;
                        } while (current.next != null);
                    }
                }
                return res;
            }

            @Override
            @Deprecated(since = "NOT IMPLEMENTED!")
            public <T> T[] toArray(T[] ts) {
                if (ts == null) throw new NullPointerException("not NULL argument required");
                //имею право не реализовывать, так как жденерики еще не учили
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean add(Map.Entry entry) {
                if (entry == null) throw new NullPointerException("not NULL argument required");
                put(entry.getKey(), entry.getValue());
                return false;
            }

            @Override
            public boolean remove(Object o) {
                if (o == null) throw new NullPointerException("not NULL argument required");
                if (!this.contains(o)){return false;}
                return CollMap.this.remove(((Map.Entry)o).getKey()) != null;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                if (collection == null) throw new NullPointerException("not NULL argument required");
                Object[] tmp = collection.toArray();
                for (Object cur : tmp) {
                    if (!this.contains(cur)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean addAll(Collection<? extends Map.Entry> collection) {
                if (collection == null) throw new NullPointerException("not NULL argument required");
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Map.Entry currentEntry = (Map.Entry) it.next();
                    put(currentEntry.getKey(), currentEntry.getValue());
                }
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                if (collection == null) throw new NullPointerException("not NULL argument required");
                boolean res = false;
                for (Object o : this){
                    if (!collection.contains(o)){
                        res = true;
                        this.remove(o);
                    }
                }
                return res;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                boolean res = false;
                if (collection == null) throw new NullPointerException("not NULL argument required");
                Object[] tmp = collection.toArray();
                for (Object cur : tmp) {
                    if (!res) res = this.remove(cur); else this.remove(cur);//тут я говорю "если хотябы один элемент был уделен, то вернуть правду иначе ложь"
                }
                return res;
            }

            @Override
            public void clear() {
                CollMap.this.clear();
            }
        };
    }


    public static class Entry implements Map.Entry {
        Entry next;
        Object key;
        String value;

        public Entry(Entry next, Object key, String value) {
            this.next = next;
            this.key = key;
            this.value = value;
        }//Entry constructor

        @Override
        public Object getKey() {
            return this.key;
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        @Override
        public Object setValue(Object o) {
            if (o == null) return null;
            this.value = (String) o;
            return o;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "next=" + next +
                    ", key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return key.equals(entry.key) &&
                    value.equals(entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }//Entry class
}//CollMap
