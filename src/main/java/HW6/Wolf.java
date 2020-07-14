package HW6;

public class Wolf extends Wild {
    private int id;

    protected Wolf(int age, int weight, int[] color, boolean isPredator) {
        super(age, weight, color, isPredator);
        this.id = super.idCounter;
    }
    @Override
    public String voice() {
        String voice = this.id + super.voice();
        return voice;
    }

}
