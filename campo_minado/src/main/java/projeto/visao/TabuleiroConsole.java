package projeto.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import projeto.excecao.ExplosaoException;
import projeto.excecao.SairException;
import projeto.modelo.Tabuleiro;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner leitor = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro; 

        executarJogo();
    }

    private void executarJogo(){
        try {
            boolean continuar = true; 
            cicloDoJogo(); 
            
            System.out.println("Outra partida? (S/n) ");
            leitor.nextLine();
            String r = leitor.nextLine();
             
            while (continuar) {
                if("n".equalsIgnoreCase(r)){
                    continuar = false;
                } else if ("s".equalsIgnoreCase(r)) {
                    continuar = true; 
                    tabuleiro.reiniciar();
                } 
            }
        } catch (SairException e) {
            System.out.println("Tchau!!!");
        } finally{
            leitor.close();
        }
    }

    private void cicloDoJogo(){
        try {

            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);    

                String digitado = capturarValorDigitado("Digite (x, y): " );

                Iterator <Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();
                

                digitado = capturarValorDigitado("1- Abrir\n2- Marcar\n\n");

                if ("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(),xy.next());
                    System.out.println("");
                } else if ("2".equals(digitado)) {
                    tabuleiro.marcar(xy.next(), xy.next());
                    System.out.println("");
                }
               
            }
            
            System.out.println("Voce ganhou!");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("Voce perdeu!");
        }
    }
    
    private String capturarValorDigitado(String texto){
        System.out.print(texto);
        
        String digitado = leitor.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException(); 
        }

        return digitado; 
    }
}
