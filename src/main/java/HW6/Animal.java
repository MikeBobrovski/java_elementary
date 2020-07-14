package HW6;

public abstract class Animal {
    protected static int idCounter;
    private int age;
    private int weight;
    private int[] color = new int[3];

    protected String voice() {
        String voice = " Hello, ";
        return voice;
    }

    protected Animal(int age, int weight, int[] color) {
        boolean argumentOK = age >= 0 && weight > 0 && age < 100 && weight < 50_000;
        if (!argumentOK) throw new IllegalArgumentException("very strange animal");
        this.age = age;
        this.weight = weight;
        System.arraycopy(color, 0, this.color, 0, 2);
        idCounter++;
    }

}
