package estruturas;

import dominio.Carta;

public class No {

    private Carta carta;
    private No proximo;

    public No(Carta carta) {
        this.carta = carta;
        this.proximo = null;
    }

    public Carta getCarta() {
        return carta;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}