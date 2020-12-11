package Q2;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int N_ALUNOS = 20;

    //Barreira reusável para rastrear quantas threads sobem a bordo do barco.
    private static ReusableBarrier barreira = new ReusableBarrier(4);

    //Semáforo para limitar acesso a região crítica.
    //Número de permissões é 1 porque apenas uma thread pode acessar pode vez.
    private static Semaphore mutex = new Semaphore(1);

    //Semáforos para controlar o número de threads que podem passar.
    //Número de permissões é 0 porque precisa esperar liberar acesos.
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
                        String curso = cursos.get(id<15 ? 1 : 0); //Uma forma de distribuir os cursos entre os alunos.
                        barco.embarcar(id, curso);
                        barco.rema(id, curso);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            alunoThread.start();
        }
    }
}
