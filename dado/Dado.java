package dado;

import java.util.Random;

/**
 * Classe que representa um dado
 *
 * @author Humberto Chiesi Neto
 *
 * @version 21/11/2021
 *
 */
public class Dado {
    int nLados;         //numero de lados do dado

    public Dado(int nLados){
        if (nLados<0){
            this.nLados = 6;
        } else this.nLados = nLados;
    }

    //função que sorteia um lado do dado (de 1 - nLados) e retorna o valor do lado
    public int sortearLado(){
        Random random = new Random();
        random.setSeed(System.nanoTime());

        int lado = random.nextInt(this.nLados) + 1;

        return lado;
    }
}
