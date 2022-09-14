package exercicio2;

import static java.lang.Thread.sleep;

public class Produtor implements Runnable {
    Deposito dep;
    int x;

    public Produtor(Deposito d, int s) {
        this.dep = d;
        this.x = s;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            Deposito.colocar();
            //notifyAll();
            try {
                sleep(x * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}