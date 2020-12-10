package Q2;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int N_ALUNOS = 20;
    private static ReusableBarrier barreira = new ReusableBarrier(4);
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore filaDeComputacao = new Semaphore(0);
    private static Semaphore filaDePsicologia = new Semaphore(0);

    public static void main(String[] args) {
        Barco barco = new Barco(mutex, filaDeComputacao, filaDePsicologia, barreira);
        List<String> cursos = Arrays.asList("computacao", "psicologia");

        for (int i = 1; i <= N_ALUNOS; i++) {
            final int id = i;
            Thread alunoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barco.embarcar(id, cursos.get(id<15 ? 1 : 0));
                        barco.rema(id, cursos.get(id<15 ? 1 : 0));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            alunoThread.start();
        }
    }
}
