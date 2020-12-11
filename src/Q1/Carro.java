package Q1;

import java.util.concurrent.Semaphore;

public class Carro {
    private final int CAPACIDADE = 4;
    private final int ZERO = 0;
    private int embarcados;
    private int desembarcados;

    private Semaphore mutex;
    private Semaphore mutex2;
    private Semaphore todosABordo;
    private Semaphore todosDesceram;
    private Semaphore filaDeEmbarque;
    private Semaphore filaDeDesembarque;

    public Carro(Semaphore mutex, Semaphore mutex2, Semaphore todosABordo, Semaphore todosDesceram,
                 Semaphore filaDeEmbarque, Semaphore filaDeDesembarque){
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.todosABordo = todosABordo;
        this.todosDesceram = todosDesceram;
        this.filaDeEmbarque = filaDeEmbarque;
        this.filaDeDesembarque = filaDeDesembarque;
    }

    public void carregar() throws InterruptedException {
        filaDeEmbarque.release(CAPACIDADE);
        print("Carro: aguardando embarque");
        todosABordo.acquire();
        print("Carro: todos embarcaram");
    }
    public void descarregar() throws InterruptedException {
        filaDeDesembarque.release(CAPACIDADE);
        print("Carro: aguardando desembarque");
        todosDesceram.acquire();
        print("Carro: todos desembarcaram");
    }

    public void correr() throws InterruptedException {
        print("Viajando...");
        Thread.sleep(1500); //Simulando a viagem
        print("Viagem concluida");
    }

    public void embarcar(int id) throws InterruptedException {
        filaDeEmbarque.acquire();
        mutex.acquire();
        Thread.sleep(500); //Simulando o tempo de embarque
        print("Passageiro #%d: embarcou", id);
        embarcados++;
        if (embarcados == CAPACIDADE){
            todosABordo.release();
            embarcados = ZERO;
        }
        mutex.release();

    }

    public void desembarcar(int id) throws InterruptedException {
        filaDeDesembarque.acquire();
        mutex2.acquire();
        Thread.sleep(500); //Simulando o tempo de desembarque
        print("Passageiro #%d: Desembarcou", id);
        desembarcados++;
        if(desembarcados == CAPACIDADE){
            todosDesceram.release();
            desembarcados = ZERO;
        }
        mutex2.release();

    }

    private void print(String text) {
        System.out.println(text);
    }
    private void print(String text, int id) {
        System.out.printf(text+"\n", id);
    }


}
