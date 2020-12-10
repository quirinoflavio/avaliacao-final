package Q1;

import java.util.concurrent.Semaphore;

public class Main {

    private static final int N_PASSAGEIROS = 24;
    private static Semaphore mutex;
    private static Semaphore mutex2;
    private static Semaphore todosABordo;
    private static Semaphore todosDesceram;
    private static Semaphore filaDeEmbarque;
    private static Semaphore filaDeDesembarque;

    public static void main(String[] args) {
        mutex = new Semaphore(1);
        mutex2 = new Semaphore(1);
        todosABordo = new Semaphore(0);
        todosDesceram = new Semaphore(0);
        filaDeEmbarque = new Semaphore(0);
        filaDeDesembarque = new Semaphore(0);

        Carro carro = new Carro(mutex, todosABordo, todosDesceram, filaDeEmbarque, filaDeDesembarque );
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
            final int id = i;
            Thread passageiroThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        carro.embarcar(id);
                        carro.desembarcar(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            passageiroThread.start();
        }

    }
}
