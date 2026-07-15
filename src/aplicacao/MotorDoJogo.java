package aplicacao;
import dominio.Carta;
import dominio.Jogador;
import estruturas.ListaEncadeada;
import estruturas.NoEnc;
import estruturas.Pilha;

import java.util.Scanner;
import java.util.Random;

public class MotorDoJogo {
    private Pilha pilhaCompra;
    private Pilha pilhaDescarte;
    private ListaEncadeada<Jogador> listaJogadores;
    private int indexJogadorAtual;
    private Scanner leitor;

    public MotorDoJogo() {
        this.pilhaCompra = new Pilha();
        this.pilhaDescarte = new Pilha();
        this.listaJogadores = new ListaEncadeada();
        this.indexJogadorAtual = 0;
        this.leitor = new Scanner(System.in);
    }

    public void iniciarJogo() {
        criarJogador();
        Carta[] cartas = criarBaralho(52);
        embaralhar(cartas);
        empilharCompra(cartas);
        distribuirCartas();

    }

    public ListaEncadeada<Jogador> criarJogador() {
        System.out.println("Quantos jogadores vão participar dessa partida?");
        int qtdJogadores = this.leitor.nextInt();
        for (int i = 1; i <= qtdJogadores; i++) {
            Jogador novoJogador = new Jogador("jogador " + i);
            listaJogadores.adicionar(novoJogador);
        }
        return listaJogadores;

    }

    public Carta[] criarBaralho(int tamanho) {
        Carta[] cartas = new Carta[tamanho];
        for (int i = 1; i <= cartas.length; i++) {
            cartas[i - 1] = new Carta(i);
        }
        return cartas;
    }

    public Carta[] embaralhar(Carta[] cartas) {
        if (cartas == null || cartas.length <= 1) {
            return null;
        }
        Random gerar = new Random();
        int tamanho = cartas.length;
        // Percorre o vetor de trás para frente, até o índice 1
        for (int i = tamanho - 1; i > 0; i--) {
            // evita que lancemos uma exceção quando i chegar a 0.
            int posicaoAleatoria = gerar.nextInt(i + 1);
            Carta temp = cartas[i];
            cartas[i] = cartas[posicaoAleatoria];
            cartas[posicaoAleatoria] = temp;
        }
        return cartas;
    }

    public void empilharCompra(Carta[] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            pilhaCompra.empilhar(cartas[i]);
        }
    }

    public void distribuirCartas() {
        NoEnc<Jogador> noAtual = listaJogadores.getPrimeiro();
        while (noAtual != null) {
            // Extrai o Jogador de dentro do nó atual
            Jogador jogadorAtual = noAtual.getDado();
            // Distribui as 5 cartas para o jogador atual
            for (int i = 0; i < 5; i++) {
                Carta carta = pilhaCompra.desempilhar();
                jogadorAtual.adicionarCarta(carta);
            }
            noAtual = noAtual.getProximo();
        }
    }
}