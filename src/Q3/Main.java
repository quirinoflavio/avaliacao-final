package Q3;

import java.util.concurrent.Semaphore;

public class Main {


    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore podeSair = new Semaphore(0);

    private static final int N_ALUNOS = 3;

    public static void main(String[] args) {
        Bar bar = new Bar(mutex, podeSair);

        for (int i = 1; i <= N_ALUNOS; i++) {
            final int id = i;
            Thread alunoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        bar.bebe(id);
                        bar.remediado(id);
                        bar.sai(id);
                    }catch (InterruptedException e){

                    }
                }
            });
            alunoThread.start();
        }
    }
}
