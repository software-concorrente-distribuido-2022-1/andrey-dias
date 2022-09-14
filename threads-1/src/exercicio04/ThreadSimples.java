package exercicio04;
/*
* Autores: Andrey Dias e Guilherme Faleiros
*
* Basicamente a classe ThreadSimples executa um método main que instancia uma thread do tipo Loop
* que tem uma lista de palavras e a cada 4000ms imprime uma palavra de acordo com a ordem estabelecida. Por outro lado,
* a classe ThreadSimples, que roda a thread main, aguarda que a outra thread instanciada termine a sua execução e verifica a cada
* 1000ms se a execução da thread Loop finalizou e caso tenha finalizado, imprime "Finalmente", caso contrário, printa Ainda esperando...
* ou caso, o tempo de execução da thread Loop seja maior que 1 minuto, a thread Loop é interrompida.
*
* */
public class ThreadSimples {
    static void mensagem(String messagem) { //declaração da interface do método
        String nomeThread = Thread.currentThread().getName(); //obtém da thread em execução
        System.out.println(nomeThread + " " + messagem); //printa o nome da thread concatenando com o parametro messagem recebido pelo método
    }
    private static class Loop implements Runnable { //declaração da classe implementando a interface Runnable
        public void run() { // declaração do método run que deve ser implementando por conta da interface Runnable
            String info[] = { // Declaração de um array de strings chamado info
                    "Java",
                    "é uma boa linguagem.",
                    "Com threads,",
                    "é melhor ainda."
            };
            try { //Abre um bloco try/catch
                for (int i = 0; i < info.length; i++) { // declaração inicial de um laço de repetição que executará 4 vezes (numero de elementos do array info)
                    Thread.sleep(4000); // Coloca a thread em estado de sleep por 4000ms
                    mensagem(info[i]); // Invoca o método mensagem com o item está posição i do array info
                }
            } catch (InterruptedException e) { // captura uma exceção lançada do tipo InterruptedException
                mensagem("Nada feito!"); // Invoca o método mensagem com a string "Nada feito!"
            }
        }
    }

    public static void main(String args[]) throws InterruptedException { // Declaração da assinatura do método main e indica a classe pode lançar uma InterruptedException
        long paciencia = 1000 * 60 * 60; // inicializa uma variável pacienca com o valor da operação aritmética 1000 * 60 * 60
        if (args.length > 0) { // bloco if verifica se o tamanho do array args recebido é maior que zero
            try { // abre um bloco try/catch
                paciencia = Long.parseLong(args[0]) * 1000; // reatribui o valor da variavel paciencia para o valor da operação de args[0] * 1000
            } catch (NumberFormatException e) { // captura exception do tipo NumberFormatException
                System.err.println("Argumento deve ser um inteiro."); // printa a string no console "Argumento deve ser um inteiro." no console
                System.exit(1); // mata o programa
            }
        }
        mensagem("Iniciando a thread Loop"); // invoca o método mensagem com a string "Iniciando a thread Loop"
        long inicio = System.currentTimeMillis(); // Obtem o valor em milissegundos da data do sistema e atribui na variavel inicio
        Thread t = new Thread(new Loop()); // Instancia uma objeto do tipo Thread passando uma instância da classe loop no construtor
        t.start(); // Inicializa a thread declarada acima
        mensagem("Esperando que a thread Loop termine"); // invoca o método mensagem com a string "Esperando que a thread Loop termine"
        while (t.isAlive()) { // abre um laço while para executar enquanto a thread não estiver como dead
            mensagem("Ainda esperando..."); // invoca o método mensagem com a string "Ainda esperando..."
            t.join(1000); // invoca o método join() da instancia t passando 1000 como argumento
            if (((System.currentTimeMillis() - inicio) > paciencia) && t.isAlive()) { // verifica se o método atende a condição da variavel paciencia ser maior que o tempo passado desde a declaracao da variavel inicio e verifica se a thread está viva
                        mensagem("Cansado de esperar!"); // invoca o método mensagem com a string "Cansado de esperar!"
                        t.interrupt(); // invoca o método interrupt() da instancia t
                        t.join(); // invoca o método join() da instancia t passando 1000 como argumento
                    }
        }
        mensagem("Finalmente!");  // invoca o método mensagem com a string "Finalmente!"
    }
}