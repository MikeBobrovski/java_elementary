package HW7;

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

}
