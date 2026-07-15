package dominio;

import estruturas.ListaEncadeada;

public class Jogador {
    private String nome;
    private ListaEncadeada maoDeCartas;
    public Jogador(String nome) {
        this.nome = nome;
        this.maoDeCartas = new ListaEncadeada();
    }
    public String getNome() {
        return nome;
    }
    public void adicionarCarta(Carta carta) {
        maoDeCartas.adicionar(carta);
    }
    public void exibirMaoDeCartas() {
        maoDeCartas.exibir();
    }
    public Carta jogarCarta(int posicaoEscolhida) {
       return (Carta) maoDeCartas.removerPorIndice(posicaoEscolhida);

    }
    public boolean maoEstaVazia() {
        return maoDeCartas.estaVazia();
    }


}
