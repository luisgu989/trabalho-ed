package estruturas;

import dominio.Carta;

public class ListaEncadeada {

    private No primeiro;
    private int tamanho;

    public ListaEncadeada() {
        this.primeiro = null;
        this.tamanho = 0;
    }
    public No getPrimeiro() {
        return primeiro;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public void adicionar(Carta carta) {
        No novoNo = new No(carta);

        if (estaVazia()) {
            primeiro = novoNo;
        } else {
            No atual = primeiro;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(novoNo);
        }
        tamanho++;
    }

    public Carta removerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Erro: Índice inválido! A posição " + indice + " não existe na estrutura.");
        }

        No removido;
        if (indice == 0) {
            removido = primeiro;
            primeiro = primeiro.getProximo();
        } else {
            No anterior = primeiro;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getProximo();
            }
            removido = anterior.getProximo();
            anterior.setProximo(removido.getProximo());
        }

        removido.setProximo(null);
        tamanho--;

        return removido.getCarta();
    }

    public void print() {
        No atual = primeiro;
        int indice = 0;

        while (atual != null) {
            System.out.println("[" + indice + "] " + atual.getCarta());
            atual = atual.getProximo();
            indice++;
        }

    }
}