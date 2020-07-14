package HW6;

public class Fish extends Animal {
    private int id;

    protected Fish(int age, int weight, int[] color) {
        super(age, weight, color);
        this.id = super.idCounter;
    }

    @Override
    public String voice() {
        return "\"....\"";
    }

}
