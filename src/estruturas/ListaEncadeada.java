package estruturas;

import dominio.Carta;

public class ListaEncadeada<T> {

    private NoEnc<T> primeiro;
    private int tamanho;

    public ListaEncadeada() {
        this.primeiro = null;
        this.tamanho = 0;
    }
    public NoEnc<T> getPrimeiro() {
        return primeiro;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public void adicionar(T dado) {
        NoEnc novoNo = new NoEnc(dado);

        if (estaVazia()) {
            primeiro = novoNo;
        } else {
            NoEnc atual = primeiro;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(novoNo);
        }
        tamanho++;
    }

    public T removerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Erro: Índice inválido! A posição " + indice + " não existe na estrutura.");
        }

        NoEnc removido;
        if (indice == 0) {
            removido = primeiro;
            primeiro = primeiro.getProximo();
        } else {
            NoEnc anterior = primeiro;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getProximo();
            }
            removido = anterior.getProximo();
            anterior.setProximo(removido.getProximo());
        }

        removido.setProximo(null);
        tamanho--;

        return (T) removido.getDado();
    }

    public void exibir() {
        NoEnc atual = primeiro;
        int indice = 0;

        while (atual != null) {
            System.out.println("[" + indice + "] " + atual.getDado());
            atual = atual.getProximo();
            indice++;
        }

    }
}