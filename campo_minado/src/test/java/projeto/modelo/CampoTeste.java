package projeto.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import projeto.excecao.ExplosaoException;

public class CampoTeste {
    
    private Campo campo = new Campo(3,3);

    @Test
    public void testeVizinho(){
        Campo vizinho = new Campo(3,2);

        boolean resultado = campo.adicionaVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeAlternarMarcacaoPadrao(){
        assertFalse(campo.isMarcado());
    }
  
    @Test
    void testeAlternarMarcacao(){
        campo.alternarMarcado();
        assertTrue(campo.isMarcado());
    }
    
    @Test
    void testeAlternarMarcacao2(){
        campo.alternarMarcado();
        campo.alternarMarcado();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir());
    }
    
    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcado();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();

        assertThrows(ExplosaoException.class,() ->{
            campo.abrir();
        });                              
    }
    

    @Test
    void testeAbrirComVizinhos(){
        Campo v1 = new Campo(1 , 1);
        Campo v2 = new Campo(2, 2);
        //Campo v3 = new Campo(2 , 4);

        v2.adicionaVizinho(v1);
 
        campo.adicionaVizinho(v2);
        campo.abrir(); 
        assertTrue(v2.isAberto() && v1.isAberto());
    }

    @Test
    void testeAbrirComVizinhos2(){
        Campo v11 = new Campo(1 , 1);
        Campo v12 = new Campo(1 , 2);
        Campo v22 = new Campo(2, 2);
        v12.minar(); 

        v22.adicionaVizinho(v11);
        v22.adicionaVizinho(v12);
        
        campo.adicionaVizinho(v22);
        campo.abrir(); 

        assertTrue(v22.isAberto() && v11.isFechado());

    }
}