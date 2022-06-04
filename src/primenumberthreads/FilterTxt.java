package primenumberthreads;

import java.io.*;
import java.util.*;

public class FilterTxt {

    private static int biggestPrimeNumber = 0;
    private static String largestPrimeNumberFile = "";

    public static Vector<File> splitFileTXT(File directory) {

        Vector<File> filesCheck = new Vector<>();
        Vector<File> filesProcess = new Vector<>();

        //add todos os diretorios da pasta raiz
        Collections.addAll(filesCheck,
                directory.listFiles());

        //abrindo cada uma das sub pastas
        while (!filesCheck.isEmpty()) {
            File currentFile = filesCheck.remove(0);
            //sera que isso é arquivo .txt?
            if (currentFile.isFile()
                    && currentFile.getAbsolutePath().endsWith(".txt")) {
                filesProcess.add(currentFile);
            } else {
                //pode ser pasta ou arquivo NAO txt
                if (currentFile.isDirectory()) {
                    Collections.addAll(filesCheck,
                            currentFile.listFiles());
                }
            }
        }
        return filesProcess;
    }

    public static void selectNumber(File directory, int initialValue, int finalValue) {

        Vector<File> filesTxt = splitFileTXT(directory);

        for (int indexFile = initialValue; indexFile < finalValue; indexFile++) {

            try {
                FileReader reading = new FileReader(filesTxt.get(indexFile));

                BufferedReader buffRead = new BufferedReader(reading);

                String line = buffRead.readLine();

                while (line != null) {

                    //Separa a linha por virgula
                    String separates[] = line.split(",");

                    for (int indexLine = 0; indexLine < separates.length; indexLine++) {
                        //Filtrando o que é numero
                        if (separates[indexLine].matches("[0-9]+")) {
                            //Convertendo para int uma "linha" inteira
                            int number = Integer.parseInt(separates[indexLine].strip());

                            checkPrimeNumber(number, filesTxt.get(indexFile).getPath());

                        }

                    }
                    line = buffRead.readLine();
                }

            } catch (FileNotFoundException ex) {
                System.err.println("File doesn't exist");
            } catch (IOException ex) {
                System.err.println("Corrupted or no access");
            } catch (NumberFormatException e) {
                e.getMessage();
            }
        }   

    }

    public static void checkPrimeNumber(int number, String largestPrimeNumberDirectory) {
        
        if (number > biggestPrimeNumber) {
            
            for (int indexPrimeNumber = 2; indexPrimeNumber < number; indexPrimeNumber++) {
                if (number % indexPrimeNumber == 0) {
                    return;
                }
            }

            biggestPrimeNumber = number;
            largestPrimeNumberFile = largestPrimeNumberDirectory;
        }

    }

    public static void printPrimeNumber() {
        System.out.println(largestPrimeNumberFile + " -- " + biggestPrimeNumber);
    }
}
