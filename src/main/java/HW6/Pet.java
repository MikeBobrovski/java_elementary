package HW6;

public abstract class Pet extends Animal {
    private String name;
    private boolean isVaccinated;

    protected Pet(int age, int weight, int[] color, boolean isVaccinated, String name) {
        super(age, weight, color);
        if (!nameIsOK(name)) throw new IllegalArgumentException("bad name");
        this.isVaccinated = isVaccinated;
        this.name = name;
    }

    protected static boolean nameIsOK(String arg) {
        final String nameRegEx = "^[A-Z][a-z]{2,16}";
        return arg.matches(nameRegEx);
    }//хелпер для валидации имени

    @Override
    public String voice() {
        String voice = super.voice() + "my name is " + this.name;
        return voice;
    }
}
