package Q3;

import java.util.concurrent.Semaphore;

public class Bar {
    private int bebendo;
    private int querendoSair;
    private Semaphore mutex;
    private Semaphore podeSair;

    public Bar(Semaphore mutex, Semaphore podeSair){
        this.mutex = mutex;
        this.podeSair = podeSair;
    }

    public void bebe(int id) throws InterruptedException {
        mutex.acquire();
        print("Aluno #%d: começou a beber", id);
        bebendo++;
        if(bebendo == 2 && querendoSair == 1){
            podeSair.release();
            querendoSair--;
        }
        mutex.release();
    }

    public void remediado(int id) throws InterruptedException {
        Thread.sleep(100);
        mutex.acquire();
        print("Aluno #%d: tomou uma e está remediado", id);
        bebendo--;
        querendoSair++;
        mutex.release();
    }

    public void sai(int id) throws InterruptedException {
        mutex.acquire();
        print("Aluno #%d: quer sair", id);
        if(bebendo == 1 && querendoSair == 1){
            print("Aluno #%d: não pode sair. Há uma pessoa bebendo", id);
            mutex.release();
            podeSair.acquire();
            print("Aluno #%d: foi embora", id);
        }else if(bebendo == 0 && querendoSair == 2){
            podeSair.release();
            querendoSair -= 2;
            print("Aluno #%d: foi embora", id);
            mutex.release();
        }
        else{
            querendoSair--;
            print("Aluno #%d: foi embora", id);
            mutex.release();
        }
    }

    private void print(String text) {
        System.out.println(text);
    }
    private void print(String text, int id) {
        System.out.printf(text+"\n", id);
    }


}
