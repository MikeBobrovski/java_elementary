package HW17;

import java.util.*;

public class CollMap<K, V> implements Map<K, V> {

    private static final int NUM_BUCKETS = 32;
    public Entry<K, V>[] data = new Entry[NUM_BUCKETS];

    private int bucket(Object key) {
        return Math.abs(key.hashCode()) % NUM_BUCKETS;
    }


    //-----------------------------------------------------------контракт

    @Override
    public int size() {
        int counter = 0;
        for (Entry<K, V> current : data) {
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
        boolean empty = true;
        for (Entry<K, V> current : data) {
            if (current != null) {
                empty = false;
            }
        }
        return empty;
    }//isEmpty

    @Override
    public boolean containsKey(Object key) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        //if (new K().getClass() != key.getClass()) throw new ClassCastException("inappropriate type for this map");
        int index = bucket(key);
        Entry<K, V> last = data[index];
        if (last == null) return false;
        do {
            if (last.key.equals(key)) return true;
        } while (last.next != null);
        return false;
    }//containsKey

    @Override
    public boolean containsValue(Object value) {
        if (value == null) throw new NullPointerException("not NULL argument required");
        for (Entry<K, V> current : data) {
            if (current != null) {
                do {
                    if (current.value.equals(value)) return true;
                } while (current.next != null);
            }
        }
        return false;
    }//containsValue

    @Override
    public V get(Object key) {
        int index = bucket(key);
        Entry<K, V> current = data[index];

        while (current != null) {
            K k = current.key;
            if (k.equals(key)) {
                return current.getValue();
            } else {
                current = current.next;
            }
        }
        return null;
    }//get

    @Override
    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        int index = bucket(key);
        Entry<K, V> last = data[index];
        data[index] = new Entry(last, key, value);
        return value;
    }//put

    @Override
    public V remove(Object key) {
        if (key == null) throw new NullPointerException("not NULL argument required");
        int index = bucket(key);
        Entry<K, V> current = data[index];
        if (current != null && current.next == null) {
            //если в списке есть один элемент, то заменяем его на НУЛЛ и возвращаем его
            data[index] = null;
            return current.getValue();
        } else {
            //если в списке не один элемент, то запоминаем предыдущий. для того, чтобы удалить текущий, предыдущему в "некст" нужно записать "некст" для текущего
            Entry<K, V> prev = null;
            while (current != null) {
                Object o = current.key;
                if (o.equals(key)) {
                    //если в списке есть один элемент, то заменяем его на НУЛЛ и возвращаем его
                    prev.next = current.next;
                    return current.getValue();
                } else {
                    prev = current;
                    current = current.next;
                }
            }
        }
        return null;
    }//remove

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        if (m == null) throw new NullPointerException("not NULL argument required");
        Iterator iterator = m.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<K, V> pairs = (Entry<K, V>) iterator.next();//FIXME почему возвращает Object?? это же итератор от Map<? extends K, ? extends V>. или не факт, что внутреннее <K, V> фргумента реализует Map.Entry<K, V>??
            put(pairs.getKey(), pairs.getValue());
        }
    }//putAll

    @Override
    public void clear() {
        data = new Entry[NUM_BUCKETS];
    }//clear

    @Override
    public Set<K> keySet() {
        HashSet<K> res = new HashSet<>();
        for (Entry<K, V> current : data) {
            while (current != null) {
                res.add(current.key);
                current = current.next;
            }
        }
        return res;
    }//keySet

    @Override
    public Collection<V> values() {
        Collection<V> res = new HashSet<>();
        for (Entry<K, V> current : this.data) {
            while (current != null) {
                res.add(current.value);
                current = current.next;
            }
        }
        return res;
    }//values

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new Set<>() {
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
            public Iterator<Map.Entry<K, V>> iterator() {
                return new Iterator<>() {
                    Entry<K, V> currentNode = null;
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
                    public Entry<K, V> next() {
                        Entry<K, V> tmp = currentNode;
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
                for (Entry<K, V> current : data) {
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
            public <T> T[] toArray(T[] ts) {
                if (ts == null) throw new NullPointerException("not NULL argument required");
                if (this.size() > ts.length) {
                    ts = Arrays.copyOf(ts, this.size());
                }
                int i = 0;
                for (Entry<K, V> current : data) {
                    if (current != null) {
                        do {
                            ts[i] = (T) current;//из-за этого кастования не могу из одного "в массив" вызвать другой
                            i++;
                        } while (current.next != null);
                    }
                }
                return ts;
            }

            @Override
            public boolean add(Map.Entry entry) {
                if (entry == null) throw new NullPointerException("not NULL argument required");
                K key = ((Entry<K, V>) entry).getKey();//FIXME не понял, чего ругается если убрать кастование. переменная entry типа Map.Entry. этот интерфейс гарантирует наличие методов и их сигнатуры.
                V value = ((Entry<K, V>) entry).getValue();
                put(key, value);
                return false;
            }

            @Override
            public boolean remove(Object o) {
                if (o == null) throw new NullPointerException("not NULL argument required");
                if (!this.contains(o)) {
                    return false;
                }
                return CollMap.this.remove(((Entry<K, V>) o).getKey()) != null;// FIXME путаюсь в "(Entry<K, V>) o" или "(Map.Entry<K, V>) o" или "(Map.Entry) o". объясни, пожалуйста, на занятии
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
            public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
                if (collection == null) throw new NullPointerException("not NULL argument required");
                Iterator<? extends Map.Entry<K, V>> it = collection.iterator();//тут натупил, пытался вызвать итератор своей коллекции, но коллекция, принимаемая аргументом, имеет право реализовать его по другому
                while (it.hasNext()) {

                    Entry<K, V> currentEntry = (Entry<K, V>) it.next();//FIXME вроде, итератор.некст должен вернуть то, под что типизирован, а именно порд то, чего коллекция,а она чего-то унаследованного от Map.Entry<K, V>. так чего же it.next() возвращает Object???
                    put(currentEntry.getKey(), currentEntry.getValue());
                }
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                if (collection == null) throw new NullPointerException("not NULL argument required");
                boolean res = false;
                for (Object o : this) {
                    if (!collection.contains(o)) {
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
                    if (!res) res = this.remove(cur);
                    else
                        this.remove(cur);//тут я говорю "если хотябы один элемент был уделен, то вернуть правду иначе ложь"
                }
                return res;
            }

            @Override
            public void clear() {
                CollMap.this.clear();
            }
        };
    }//entrySet

    //-----------------------------------------------------------/контракт

    public static class Entry<K, V> implements Map.Entry<K, V> {
        Entry<K, V> next;
        private K key;
        private V value;

        public Entry(Entry<K, V> next, K key, V value) {
            this.next = next;
            this.key = key;
            this.value = value;
        }//Entry constructor

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            if (value == null) return null;
            this.value = value;
            return value;
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
            @SuppressWarnings("unchecked")//тут задавил осознанно: не может не катоваться ввиду строки выше.
            Entry<K, V> entry = (Entry<K, V>) o;
            return key.equals(entry.key) &&
                    value.equals(entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }//Entry class
}//CollMap
