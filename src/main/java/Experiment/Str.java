package Experiment;


import org.w3c.dom.ls.LSOutput;

public class Str {
    public static void main(String[] args) {
        double base = 2.5;
        int exp = -5;

        System.out.println(Math.pow(base, exp));
        System.out.println(pow(base, exp));

//        System.out.println(Runtime.getRuntime().availableProcessors());
//
//        String test = "{\n" +
//                "  \"id\" : 0,\n" +
//                "  \"lastName\" : \"Ivanov\",\n" +
//                "  \"age\" : 20\n" +
//                "}\n" +
//                "{\n" +
//                "  \"id\" : 1,\n" +
//                "  \"lastName\" : \"Petrov\",\n" +
//                "  \"age\" : 25\n" +
//                "}\n" +
//                "{\n" +
//                "  \"id\" : 2,\n" +
//                "  \"lastName\" : \"Sidorov\",\n" +
//                "  \"age\" : 30\n" +
//                "}\n";
//        System.out.println(test);
//
//        System.out.println(remover(test));


    }

    public static String remover(String s){
        StringBuffer res = new StringBuffer(s);

        while (res.indexOf("\n") != -1) {
            res.deleteCharAt(res.indexOf("\n"));
        }
        while (res.indexOf(" ") != -1) {
            res.deleteCharAt(res.indexOf(" "));
        }

        return res.toString();
    }

    public static  double pow(double x, int y) {
        //во встроенном методе куча проверок на частные случаи. не вижу смысла копировать все их сюда.
        //есть моменты, когда встроенная ф-ия выдает 2 знака после запятой (2.5^-2), а самописная много нулей и что-то в последнем знаке.
        // это погрешность вычислений с плавающей точкой если не критично, фиксить не буду.
        //strictfp - вроде, призван ограничить точность вычислений. пусть будет
        if (y == 0) return 1;
        if (y == 1) return x;
        if (Double.isNaN(x) || Double.isNaN(y)) return x + y;
        if (y < 0) {
            x = 1.0 / x;
            y = -y;
        }

        double halfWay = pow(x, y / 2);// О(n) = log(n) достигается делением на 2 в этой строке
        halfWay = halfWay * halfWay;
        if (y % 2 != 0)
            halfWay = halfWay * x;
        return halfWay;
    }

}
