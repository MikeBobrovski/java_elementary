package HW6;

public abstract class Wild extends Animal {
    private boolean isPredator;

    protected Wild(int age, int weight, int[] color, boolean isPredator) {
        super(age, weight, color);
        this.isPredator = isPredator;
    }

    @Override
    public String voice() {
        String voice = super.voice() + "I am a wild animal" + ((isPredator) ? " and I am angry" : "");
        return voice;
    }
}
