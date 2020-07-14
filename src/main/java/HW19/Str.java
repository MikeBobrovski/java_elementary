/*
обкатка StringBuffer и попытка понять, почему он не работал в поштучном ДЖСОНе
 */

package HW19;
public class Str {
    public static void main(String[] args) {

        String test = "{\n" +
                "  \"id\" : 0,\n" +
                "  \"lastName\" : \"Ivanov\",\n" +
                "  \"age\" : 20\n" +
                "}\n" +
                "{\n" +
                "  \"id\" : 1,\n" +
                "  \"lastName\" : \"Petrov\",\n" +
                "  \"age\" : 25\n" +
                "}\n" +
                "{\n" +
                "  \"id\" : 2,\n" +
                "  \"lastName\" : \"Sidorov\",\n" +
                "  \"age\" : 30\n" +
                "}\n";
        System.out.println(test);

        String test2 = "{\n\"id\" : 0,\n\"lastName\" : \"Ivanov\",\n\"age\" : 20\n}\n{\n\"id\" : 1,\n\"lastName\" : \"Petrov\",\n\"age\" : 25\n}\n" +
                "{\n" +
                "  \"id\" : 2,\n" +
                "  \"lastName\" : \"Sidorov\",\n" +
                "  \"age\" : 30\n" +
                "}\n";

        System.out.println(remover(test2));


    }

    public static String remover(String s){
        StringBuffer res = new StringBuffer(s);

        while(res.indexOf("\n") != -1){
            res.deleteCharAt(res.indexOf("\n"));
        }
        while(res.indexOf(" ") != -1){
            res.deleteCharAt(res.indexOf(" "));
        }

        return res.toString();
    }
}
