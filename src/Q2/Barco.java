package Q2;

import java.util.concurrent.Semaphore;

public class Barco {

    private final String COMPUTACAO = "computacao";

    private ReusableBarrier barreira;
    private Semaphore mutex;
    private Semaphore filaDeComputacao;
    private Semaphore filaDePsicologia;
    private int computacao;
    private int psicologia;
    private int remadorId;

    public Barco(Semaphore mutex, Semaphore filaDeComputacao, Semaphore filaDePsicologia, ReusableBarrier barreira){
        this.mutex = mutex;
        this.filaDeComputacao = filaDeComputacao;
        this.filaDePsicologia = filaDePsicologia;
        this.barreira = barreira;
    }

    public void embarcar(int id, String curso) throws InterruptedException {
        mutex.acquire();
        if(curso.equals(COMPUTACAO)) { //Se o aluno for de computação, verifica-se as condições
            computacao++;
            if (computacao == 4) {
                filaDeComputacao.release(4);
                computacao = 0;
                remadorId = id;
            } else if (computacao == 2 && psicologia >= 2) {
                filaDeComputacao.release(2);
                filaDePsicologia.release(2);
                psicologia -= 2;
                computacao = 0;
                remadorId = id;
            } else {
                mutex.release();
            }
            filaDeComputacao.acquire();

        } else { //Se o aluno for de psicologia, verifica-se as condições
            psicologia++;
            if (psicologia == 4) {
                filaDePsicologia.release(4);
                psicologia = 0;
                remadorId = id;
            } else if (psicologia == 2 && computacao >= 2) {
                filaDePsicologia.release(2);
                filaDeComputacao.release(2);
                computacao -= 2;
                psicologia = 0;
                remadorId = id;
            } else {
                mutex.release();
            }
            filaDePsicologia.acquire();
        }


        print("Aluno #%d de %s subiu no barco", id, curso);

        //Aguarda até que os quatro alunos subam a bordo antes do remador começar a remar
        barreira.rBWait();
    }

    public void rema(int id, String curso) throws InterruptedException {
        if(remadorId == id){
            print("== Aluno #%d de %s está remando ==", id, curso);
            Thread.sleep(1500); //Simula o tempo de viagem
            mutex.release();
        }
    }

    private void print(String text, int id, String curso) {
        System.out.printf(text+"\n", id, curso);
    }

}
