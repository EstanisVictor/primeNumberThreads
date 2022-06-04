
package primenumberthreads;

import java.io.File;

public class PrimeNumberThreads {
    public static void main(String[] args) {
        
        File homeDirectory = new File("C:\\Users\\T-GAMER\\Documents\\arquivosTeste");
        
        //Pegando o total de threads da máquina
        int numbersThreads = Runtime.getRuntime().availableProcessors();
        
        //Pegando quantidade total de arquivos .txt na pasta inteira
        int quantFile = FilterTxt.splitFileTXT(homeDirectory).size();
        
        //Quantidade de arquivos que cada thread irá trabalhar
        int filePerThread = quantFile/numbersThreads;
        
        //Criando minhas threads referente quantidade de threads no PC
        for (int indexThreads = 0; indexThreads < numbersThreads; indexThreads++) {
            //Cria thread
            Thread myThreads = new Thread(new Runnable(){
                //Método que será executado pela thread
                public void run(){
                        
                    //Convertendo o nome da thread (indexThreads) para indice
                    int threadNumber = Integer.parseInt(Thread.currentThread().getName());
                    
                    //Definindo o valor inicial e o final que uma thread irá executar
                    int initialValue = filePerThread*threadNumber;
                    int finalValue = filePerThread*threadNumber+filePerThread;
                    
                    //Como a divisão é inteira, possa ser que sobre arquivos que não serão lidos
                    int restFiles = quantFile%numbersThreads;
                    
                    //Se for igual ao valor final passa somando o resto que sobrou de arquivos
                    if(threadNumber == (numbersThreads-1)){
                        /*
                        Método que lê os arquivos .txt 
                        e salva o maior numero primo
                        */
                        FilterTxt.selectNumber(homeDirectory, initialValue, finalValue+restFiles);
                    }else{
                        FilterTxt.selectNumber(homeDirectory, initialValue, finalValue);
                    }
                    
                }
                //Colocando o indice (indexThreads) como nome da thread
            }, String.valueOf(indexThreads));
            
            myThreads.start();
            
        }
        
        /*
        Enquanto o programa estiver em execução ele ficará checando se todas as 
        outras threads terminaram
        
        Depois que todas as threads acabarem, irá sobrar apenas uma thread main
        */
        while(Thread.activeCount() > 0){
            if(Thread.activeCount() == 1){
                FilterTxt.printPrimeNumber();
                break;
            }
        }
        
    }
    
}
