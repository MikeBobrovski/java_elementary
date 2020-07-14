package HW6;

public class Dog extends Pet {
    private int id;

    protected Dog(int age, int weight, int[] color, boolean isVaccinated, String name) {
        super(age, weight, color, isVaccinated, name);
        this.id = super.idCounter;
    }

    @Override
    public String voice() {
        String voice = this.id + super.voice() + ". Woof";
        return voice;
    }
}
