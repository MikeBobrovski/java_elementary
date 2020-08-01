package Experiment;



public class Str {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());
        
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

        System.out.println(remover(test));


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
