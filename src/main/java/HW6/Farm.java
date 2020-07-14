/*
задание выполнено исходя из того, что реализовывать интерфейсы мы еще не умеем
и не смотря на то, что длинные цепочки наследования крайне нежелательны
и с некоторомыми допущениями (хомяк с одинаковым успехом мржет быть диким и домашним, равно, как и рыбка, кот, собака, но рыбка будет дикой, а котики-песики домашними)

вся иерархия выполнена в одном классе, чтоб не множить файлы. задание на логику, а ее наличие/отсутствие, полагаю, можно увидеть и так

облом: нужно создавать экземпляр внешнего класса и от его имени вызывать внутренние или объявлять их статичными, а чем это грозит я не знаю
 */

package HW6;

public class Farm {
/*
    protected static class Animal {
        protected int idCounter;
        private int age;
        private int weight;
        private int[] color = new int[3];

        protected String voice() {
            String voice = "Hello, ";
            return voice;
        }

        protected Animal(int age, int weight, int[] color) {
            boolean argumentOK = age >= 0 && weight > 0 && age < 100 && weight < 50_000;
            if (!argumentOK) throw new IllegalArgumentException("very strange animal");
            this.idCounter++;
            this.age = age;
            this.weight = weight;
            System.arraycopy(color, 0, this.color, 0, 2);
        }
    }


    public class Wild extends Animal {
        private boolean isPredator;
        private int id;

        protected Wild(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color);
            this.isPredator = isPredator;
            this.id = super.idCounter;
        }

        @Override
        public String voice() {
            String voice = super.voice() + "I am a wild animal" + ((isPredator) ? " and I am angry" : "");
            return voice;
        }
    }

    public class Pet extends Animal {
        private String name;
        private boolean isVaccinated;
        private int id;

        protected Pet(int age, int weight, int[] color, boolean isVaccinated) {
            super(age, weight, color);
            this.id = super.idCounter;
            this.isVaccinated = isVaccinated;
        }

        @Override
        public String voice() {
            String voice = super.voice() + "my name is " + this.name;
            return voice;
        }
    }


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


    public class Dog extends Pet {
        //private int id;

        protected Dog(int age, int weight, int[] color, boolean isVaccinated) {
            super(age, weight, color, isVaccinated);
            //this.id = super.idCounter;
        }

        @Override
        public String voice() {
            String voice = super.voice() + ". Woof";
            return voice;
        }
    }

    public class GuideDog extends Dog {
        private boolean isTrained;

        protected GuideDog(int age, int weight, int[] color, boolean isVaccinated, boolean isTrained) {
            super(age, weight, color, isVaccinated);
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

    public class Cat extends Pet {

        public Cat(int age, int weight, int[] color, boolean isVaccinated) {
            super(age, weight, color, isVaccinated);
        }

        @Override
        public String voice() {
            String voice = super.voice() + ". Meow";
            return voice;
        }
    }

    public class Wolf extends Wild {
        protected Wolf(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color, isPredator);
        }
    }

    public class Lion extends Wild {
        protected Lion(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color, isPredator);
        }
    }

    public class Giraffe extends Wild {
        protected Giraffe(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color, isPredator);
        }
    }

    public class Crocodile extends Wild {
        protected Crocodile(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color, isPredator);
        }
    }

    public class Hamster extends Wild {
        protected Hamster(int age, int weight, int[] color, boolean isPredator) {
            super(age, weight, color, isPredator);
        }
    }
*/
}
