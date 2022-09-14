package exercicio2;

public class Deposito {
    private static Object d;
    static int items=0;
    private static final int capacidade=10;
    public static synchronized int retirar() {
        if (items>0) {
            items--;
            System.out.println("Caixa retirada: Sobram " + items + " caixas");
            return 1; }
        return 0;
    }
    public static synchronized int colocar() {
        if (items<capacidade) {
            items++;
            System.out.println("Caixa armazenada: Passaram a ser " + items + " caixas");
            return 1; }
        return 0;
    }
    public static void main(String[] args) {
        Deposito dep = new Deposito();
        Produtor p = new Produtor((Deposito) d, 1);
        Consumidor c = new Consumidor((Deposito) d, 2);
        //p.run();
       // c.run();
        Thread h = new Thread(p);
        h.start();
        Thread h1 = new Thread(c);
        h1.start();
/*
inicia o produtor
...
inicia o consumidor
...
*/
        try {
          h.join();
          h1.join();
        } catch (InterruptedException e){
            }
        System.out.println("Execução do main da classe Deposito terminada!");

    }
}
