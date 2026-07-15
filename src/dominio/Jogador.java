package dominio;

import estruturas.ListaEncadeada;

public class Jogador {

    private String nome;
    private ListaEncadeada mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ListaEncadeada();
    }

    public String getNome() {
        return nome;
    }

    public ListaEncadeada getMao() {
        return mao;
    }

    public void receberCarta(Carta carta) {
        mao.adicionar(carta);
    }

    public Carta jogarCarta(int indice) {
       return (Carta) mao.removerPorIndice(indice);

    }

    public void exibirMao() {
        mao.print();
    }

    public int getQtdCartas() {
        return mao.getTamanho();
    }

    @Override
    public String toString() {
        return nome + "(Cartas: " + getQtdCartas() + ")";
    }
}
