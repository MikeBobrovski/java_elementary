/*
иерархия:
животное: дикое, домашнее, рыба
дикое: крокодил, жираф, хомяк, лев, волк
домашнее: кот, собака
собака: собака-поводырь

если, вдруг, собака-поводырь не говорит гав, а сразу предлагает пойти домой, то в ее голосе от супер.голос будет браться подстрока, вычетающая гав,
потому что супер-супер-метод вытянуть не получится, а поводырь по моей логике, должен расширять собаку, но не домашнее животное непосредственно

возможно, кое-кто перед приветствием выдаст свой ИД, использовал для отладки, пока не удалил. как организовать режим отладки, чтобы менять поведение методов?

 */

package HW6;


import java.util.ArrayList;

public class AnimalsToTest {
    public static void main(String args[]) {

        ArrayList<Animal> animals = new ArrayList<>();
        int[] color = {190, 16, 35};//цвет предполагается передавать РГБ палитрой (натянем сову на глобус) и для простоты расцветка будет у всех одинаковая. передается по значению

//        Cat cat = new Cat(2, 4, color, true, "Bob");
//        animals.add(cat);
//        System.out.println(animals.get(0).voice());


        animals.add(new Cat(2, 4, color, true, "Bob"));
        animals.add(new Crocodile(5, 120, color, true));
        animals.add(new Dog(6, 12, color, true, "Adolf"));
        animals.add(new Fish(1, 1, color));
        animals.add(new Giraffe(4, 200, color, false));
        animals.add(new GuideDog(6, 12, color, true, "Andrew", true));
        animals.add(new Hamster(4, 200, color, false));
        animals.add(new Lion(1, 400, color, true));
        animals.add(new Wolf(1, 50, color, true));


        animals.forEach(a -> System.out.println(a.voice()));


    }
}
