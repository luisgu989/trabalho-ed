package estruturas;

import dominio.Carta;

public class NoEnc<T> {

    private T dado;
    private NoEnc proximo;

    public NoEnc(T dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public T getDado() {
        return dado;
    }

    public NoEnc<T> getProximo() {
        return proximo;
    }

    public void setProximo(NoEnc proximo) {
        this.proximo = proximo;
    }
}