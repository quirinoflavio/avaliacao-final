package Q1;

import java.util.concurrent.Semaphore;

public class Main {

    private static final int N_PASSAGEIROS = 24;
    //Semáforos utilizados para entrar nas regiões críticas de embarque e desembarque.
    //Número de permissões é 1 porque apenas uma thread pode acessar a região por vez.
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore mutex2 = new Semaphore(1);

    //Somáforos para identificar se estão todos a bordo ou se todos já desceram.
    //Número de permissões inicialmente é 0 porque ninguem pode acessar a área até que alguém permita.
    private static Semaphore todosABordo = new Semaphore(0);
    private static Semaphore todosDesceram = new Semaphore(0);

    //Passageiros aguardam nas filas antes de embarcar e desembarcar
    private static Semaphore filaDeEmbarque = new Semaphore(0);
    private static Semaphore filaDeDesembarque = new Semaphore(0);

    public static void main(String[] args) {
        Carro carro = new Carro(mutex, mutex2,  todosABordo, todosDesceram, filaDeEmbarque, filaDeDesembarque );

        Thread carroThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        carro.carregar();
                        carro.correr();
                        carro.descarregar();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        carroThread.start();


        for(int i = 1; i <= N_PASSAGEIROS; i++){
            int id = i;
            Thread passageiroThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) { //Os passageiros tentam repetidamente pegar carona
                        try {
                            carro.embarcar(id);
                            carro.desembarcar(id);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            passageiroThread.start();
        }

    }
}
