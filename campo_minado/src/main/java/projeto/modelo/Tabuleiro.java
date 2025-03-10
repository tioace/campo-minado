package projeto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import projeto.excecao.ExplosaoException;

public class Tabuleiro {
    private int linhas; 
    private int colunas;  
    private int minas;  

    private final List <Campo> campos = new ArrayList<>(); 

    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas = linhas; 
        this.colunas = colunas; 
        this.minas = minas;

        gerarCampos(); 
        associarOsVizinhos(); 
        sortearMinas(); 

    }

    public void abrir(int linha, int coluna){
        try {
            campos.parallelStream().filter(c -> c.getX() == linha && c.getY() == coluna)
            .findFirst().ifPresent(c -> c.abrir());
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true));
            throw e; 
        }

    }
    
    public void marcar(int linha, int coluna){
        campos.parallelStream().filter(c -> c.getX() == linha && c.getY() == coluna)
        .findFirst().ifPresent(c -> c.alternarMarcado());
    }
    
    

    private void gerarCampos(){
        for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                campos.add(new Campo(i, j)); 
            }
        }
    }
    
    private void associarOsVizinhos(){
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionaVizinho(c2); 
            }
        }
    }

    private void sortearMinas(){
        long minasArmadas = 0; 

        Predicate <Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count(); 

        }   while(minasArmadas < minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivo());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(); 

        int i = 0; 

        
        sb.append("   ");
        for (int c = 0; c < colunas; c++){
            sb.append(" ");
            sb.append(c);
            sb.append(" ");

        }
            sb.append("\n");
        
        for (int l = 0; l < linhas; l++){
            sb.append(" ");
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < colunas; c++){
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString(); 
    }

}
