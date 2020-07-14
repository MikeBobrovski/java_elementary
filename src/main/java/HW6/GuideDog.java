package HW6;

public class GuideDog extends Dog {
    private boolean isTrained;

    protected GuideDog(int age, int weight, int[] color, boolean isVaccinated, String name, boolean isTrained) {
        super(age, weight, color, isVaccinated, name);
        this.isTrained = isTrained;
    }

    @Override
    public String voice() {
        String voice = super.voice() + ((this.isTrained) ? ". I can take you home." : "");
        return voice;
    }

    public String takeHome() {
        return "we go home";
    }
}
