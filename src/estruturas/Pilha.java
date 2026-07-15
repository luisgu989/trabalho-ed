package estruturas;

import dominio.Carta;

public class Pilha {

    private No topo;
    private int tamanho;

    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void empilhar(Carta carta) {
        No novoNo = new No(carta);

        novoNo.setProximo(topo);
        topo = novoNo;
        tamanho++;
    }

    public Carta desempilhar() {
        if (estaVazia()) {
            throw new IllegalStateException("Erro: Tentativa de desempilhar uma pilha vazia.");
        }

        No removido = topo;
        Carta cartaRecuperada = removido.getCarta();
        topo = topo.getProximo();
        removido.setProximo(null);
        tamanho--;

        return cartaRecuperada;
    }
}