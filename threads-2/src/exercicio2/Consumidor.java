package exercicio2;

import static java.lang.Thread.sleep;

public class Consumidor implements Runnable {
    Deposito dep;
    int seg;

    public Consumidor(Deposito d, int s) {
        this.dep = d;
        this.seg = s;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            //Deposito.retirar();
            synchronized (this) {
                if (Deposito.items == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else Deposito.retirar() ;
                notify();
                try {
                    sleep(seg * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}