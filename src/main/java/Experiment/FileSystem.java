package Experiment;

import java.io.File;
import java.io.IOException;

public class FileSystem {
    static File fileSingleObj;
    static File fileWholesale;

    public static void main(String[] args) throws InterruptedException {
        setupAll();
        Thread.sleep(6_000);
        tearDownAll();
    }

    public static void setupAll() {
        fileSingleObj = new File("D://hw19", "stSingleObj.txt");
        fileWholesale = new File("D://hw19", "stWholesale.txt");
        try {
            if (fileSingleObj.createNewFile() & fileWholesale.createNewFile())
                System.out.println("Files has been created");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void tearDownAll() {
        if (fileSingleObj.delete() & fileWholesale.delete())
            System.out.println("Files has been deleted");
    }


}


/*
        // определяем объект для каталога
        File dir = new File("D://hw19");
        // если объект представляет каталог
        if(dir.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : dir.listFiles()){

                if(item.isDirectory()){

                    System.out.println(item.getName() + "  \t folder");
                }
                else{

                    System.out.println(item.getName() + "\t file");
                }
            }
        }


        File fileSingleObj = new File("D://hw19/", "stSingleObj.txt");
        System.out.println("File name: " + fileSingleObj.getName());
        System.out.println("Parent folder: " + fileSingleObj.getParent());
        if(fileSingleObj.exists())
            System.out.println("File exists");
        else
            System.out.println("File not found");

        File fileWholesale = new File("D://hw19/stWholesale.txt");
        try
        {
            boolean created = fileWholesale.createNewFile();
            if(created)
                System.out.println("File has been created");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
Thread.sleep(5_000);
        fileWholesale.delete();
    }
 */
