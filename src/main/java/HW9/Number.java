package HW9;

import HW3.Group;

import java.util.Comparator;

public class Number implements Comparable<Number>{
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    private int data;

    public Number(int data) {
        this.data = data;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return this.data == number.data;
    }

    @Override
    public String toString() {
        if (this.data > 99) return String.valueOf(this.data);
        if (this.data > 9) return " " + this.data;
        return "  " + this.data;
    }

    /*
    String res = "";
        if (Math.abs(arg) > 99) res = "  " + arg;
        else if (Math.abs(arg) > 9) res = "  " + arg;
        else res = "   " + arg;
        return (arg >= 0) ? " " + res : res;
     */

    public boolean compare(int other){
        return this.data > other;
    }

    @Override
    public int compareTo(Number number) {
        return this.data - number.getData();
    }

    public static Comparator<Number> byValue = new Comparator<Number>() {
        public int compare(Number arg1, Number arg2) {
            int data1 = arg1.data;
            int data2 = arg2.data;
            return data1 - data2; //вперед
            //return data1 - data2;;//назад
        }
    };
}
