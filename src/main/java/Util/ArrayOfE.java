package Util;

import java.lang.reflect.Array;

public class ArrayOfE<E> {
    //https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
    private E[] a;

    public ArrayOfE(Class<E> c, int s) {
        @SuppressWarnings("unchecked")
        final E[] a = (E[]) Array.newInstance(c, s);
        this.a = a;
    }

    public E[] get() {
        return a;
    }
}

