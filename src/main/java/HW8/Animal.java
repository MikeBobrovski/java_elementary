package HW8;

import java.util.Comparator;
import java.util.Objects;

public abstract class Animal {

    protected int age;
    protected int weight;

    protected Animal(int age, int weight) {
        boolean argumentOK = age >= 0 && weight > 0 && age < 100 && weight < 50_000;
        if (!argumentOK) throw new IllegalArgumentException("very strange animal");
        this.age = age;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "я " + this.getClass().toString().substring(this.getClass().toString().indexOf(".") + 1) + " весом " + weight + " кг, корому " + age + " лет от роду";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                weight == animal.weight;
    }

    public static Comparator<Animal> ByAge = new Comparator<Animal>() {
        @Override
        public int compare(Animal animal1, Animal animal2) {
            if (animal1 == null || animal2 == null) return 0;
            return animal1.age - animal2.age;
            //x < y ? -1 : (x == y ? 0 : 1); эта штука должна вернуть -1/0/1. но работает и так)
        }
    };

    public static Comparator<Animal> ByWeight = new Comparator<Animal>(){

        @Override
        public int compare(Animal animal1, Animal animal2) {
            if (animal1 == null || animal2 == null) return 0;
            return animal1.weight - animal2.weight;
        }
    };
}
