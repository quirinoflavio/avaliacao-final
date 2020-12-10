package Q2;

import java.util.concurrent.Semaphore;

public class ReusableBarrier {
    private Semaphore mutex = new Semaphore(1);
    private Semaphore turnstile = new Semaphore(0);
    private Semaphore turnstile2 = new Semaphore(1);
    private int count = 0;
    private final int N;

    public ReusableBarrier(int N){
        this.N = N;
    }

    public void rBWait() throws InterruptedException {
        this.mutex.acquire();
        count++;
        if (count == N){
            turnstile2.acquire();
            turnstile.release();
        }
        mutex.release();

        turnstile.acquire();
        turnstile.release();

        mutex.acquire();
        count--;
        if(count == 0){
            turnstile.acquire();
            turnstile2.release();
        }
        mutex.release();

        turnstile2.acquire();
        turnstile2.release();
    }
}
