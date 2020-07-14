package Experiment;

import java.io.Reader;
import java.util.*;
public class Experiment{
    public static void main(String []args){
        List list = new ArrayList();
        list.add("Hello");
        String text = list.get(0) + ", world!";
        System.out.print(text);


    }
}

enum Day {
    SUNDAY("SUN"),
    MONDAY("MON"),
    SATURDAY("SAT")
    ;
    public final String abbr;//поле для каждой константы

    Day(String abbr) {//инициализируется через кунструктор для финализированного поля. переменная может быть мутирована напрямую где угодно.
        this.abbr = abbr;
    }
    public boolean isGood (){//методы от имени константы перечисления
        return this == SUNDAY || this == SATURDAY;
    }
}