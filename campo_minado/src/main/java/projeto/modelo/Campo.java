package projeto.modelo;

import java.util.ArrayList;
import java.util.List;

import projeto.excecao.ExplosaoException;

public class Campo {

    private boolean minado;
    private boolean marcado; 
    private boolean aberto; 

    private final int x; 
    private final int y; 
    
    Campo(int x, int y){
        this.x = x; 
        this.y = y; 
    }

    private List <Campo> vizinhos = new ArrayList<>();

    boolean adicionaVizinho(Campo vizinho){
        boolean xDiferente = x != vizinho.x;
        boolean yDiferente = y != vizinho.y;
        boolean diagonal = xDiferente && yDiferente; 

        int deltaX = Math.abs(x - vizinho.x);
        int deltaY = Math.abs(y - vizinho.y);
        int deltaGeral = deltaX + deltaY; 

        if(deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false; 
        }
    }

    void alternarMarcado(){
        if(!aberto){
            marcado = !marcado;
        }
    }

    boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;

            if(minado){
                throw new ExplosaoException();
            }

            if(seguro()){
                vizinhos.forEach(v -> v.abrir());
            }
            return aberto;
        } else{
            return false; 
        }
    }

    boolean seguro(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado(){
        return marcado; 
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isAberto(){
        return aberto; 
    }
    
    public boolean isFechado(){
        return !isAberto(); 
    }
    
    public boolean isMinado(){
        return minado;
    }

    void minar(){
        minado = true; 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean objetivo(){
        boolean desvendado =  !minado && aberto; 
        boolean protegido = minado && marcado; 
        return desvendado || protegido; 
    }

    long minasNaVizinhança(){
        return vizinhos.stream().filter(v -> v.minado).count(); 
    }

    void reiniciar(){
        aberto = false;
        marcado = false;
        minado = false;
    }


    public String toString(){
        if(marcado){
            return "x";
        } else if(aberto && minado){
            return "*";
        } else if(aberto && minasNaVizinhança() > 0){
            return Long.toString(minasNaVizinhança());
        } else if(aberto){
            return " ";
        } else{
            return "?";
        }
    }
}