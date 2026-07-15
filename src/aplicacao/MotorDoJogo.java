package aplicacao;

import dominio.Carta;
import dominio.Jogador;
import estruturas.Pilha;

import java.util.Scanner;
import java.util.Random;

public class MotorDoJogo {

    private Jogador[] jogadores;
    private Pilha pilhaCompra;
    private Pilha pilhaDescarte;
    private Scanner scan;
    private Random rand;

    public MotorDoJogo() {
        this.pilhaCompra = new Pilha();
        this.pilhaDescarte = new Pilha();
        this.scan = new Scanner(System.in);
        this.rand = new Random();
    }

    public void iniciar() {
        exibirBoasVindas();
        configurarPartida();
        distribuirCartasIniciais();
        executarLoopPrincipal();
    }

    private void exibirBoasVindas() {
        System.out.println("================================================");
        System.out.println("         BEM-VINDO AO JOGO DE CARTAS            ");
        System.out.println("================================================\n");
    }

    private void configurarPartida() {
        int qtdJogadores = obterQtdJogadores();
        this.jogadores = cadJogadores(qtdJogadores);
        inicializarPilhaCompra(qtdJogadores);
    }

    private int obterQtdJogadores() {
        int qtd = 0;

        while (qtd < 2) {
            try {
                System.out.print("Digite a quantidade de jogadores (mínimo de 2): ");
                String entrada = scan.nextLine().trim();
                qtd = Integer.parseInt(entrada);
                if (qtd < 2) {
                    System.out.println("Erro! São necessários no mínimo 2 jogadores.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro! Entrada inválida, digite um número inteiro.");
            }
        }
        return qtd;
    }

    private Jogador[] cadJogadores(int qtd) {
        Jogador[] novosJogadores = new Jogador[qtd];
        for (int i = 0; i < qtd; i++) {
            System.out.print("Nome do Jogador " + (i + 1) + ": ");
            String nome = scan.nextLine().trim();
            if (nome.isEmpty()) {
                nome = "Jogador " + (i + 1);
            }
            novosJogadores[i] = new Jogador(nome);
        }
        return novosJogadores;
    }

    private void inicializarPilhaCompra(int qtdJogadores) {
        int totalCartas = calcularTotalCartas(qtdJogadores);
        Carta[] baralho = criarBaralhoSequencial(totalCartas);

        embaralhar(baralho);
        carregarPilhaCompra(baralho);

        System.out.println("\nBaralho gerado e embaralhado com " + totalCartas + " cartas!");
    }

    private int calcularTotalCartas(int qtdJogadores) {
        return Math.max(60, (qtdJogadores * 5) + 20);
    }

    private Carta[] criarBaralhoSequencial(int tamanho) {
        Carta[] baralho = new Carta[tamanho];
        for (int i = 0; i < tamanho; i++) {
            baralho[i] = new Carta(i + 1);
        }
        return baralho;
    }

    private void embaralhar(Carta[] cartas) {
        for (int i = cartas.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            Carta temp = cartas[index];
            cartas[index] = cartas[i];
            cartas[i] = temp;
        }
    }

    private void carregarPilhaCompra(Carta[] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            this.pilhaCompra.empilhar(cartas[i]);
        }
    }

    private void distribuirCartasIniciais() {
        System.out.println("Distribuindo 5 cartas para cada jogador...\n");
        for (Jogador jogador : jogadores) {
            distribuirCartasParaJogador(jogador, 5);
        }
    }

    private void distribuirCartasParaJogador(Jogador jogador, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            if (!pilhaCompra.estaVazia()) {
                jogador.receberCarta(pilhaCompra.desempilhar());
            }
        }
    }

    private void executarLoopPrincipal() {
        int turno = 0;
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            Jogador jogadorAtual = jogadores[turno];

            jogoAtivo = executarTurno(jogadorAtual);

            if (jogoAtivo) {
                turno = (turno + 1) % jogadores.length;
            }
        }

        System.out.println("\n--- FIM DE JOGO ---");
    }

    private boolean executarTurno(Jogador jogador) {
        exibirCabecalhoTurno(jogador);
        garantirCartasNaMao(jogador);

        jogador.exibirMao();
        System.out.print("Escolha a posição da carta a jogar (ou -1 para encerrar): ");
        int escolha = obterEscolhaValida(jogador);

        if (escolha == -1) {
            System.out.println("\nA partida foi encerrada por decisão de " + jogador.getNome() + ".");
            return false;
        }

        jogarCartaSelecionada(jogador, escolha);
        garantirCartasNaMao(jogador);

        return true;
    }

    private void exibirCabecalhoTurno(Jogador jogador) {
        System.out.println("\n==================================================");
        System.out.println("VEZ DE: " + jogador.getNome().toUpperCase());
        System.out.println("==================================================");
    }

    private void garantirCartasNaMao(Jogador jogador) {
        if (jogador.getQtdCartas() == 0) {
            System.out.println(jogador.getNome() + " não tem cartas na mão! Comprando...");
            comprarCarta(jogador);
        }
    }

    private void jogarCartaSelecionada(Jogador jogador, int indice) {
        Carta cartaJogada = jogador.jogarCarta(indice);
        pilhaDescarte.empilhar(cartaJogada); // [cite: 23, 37]
        System.out.println("\n-> " + jogador.getNome() + " JOGOU: " + cartaJogada);
    }

    private void comprarCarta(Jogador jogador) {
        verificarENecessitarReciclagem();

        if (pilhaCompra.estaVazia()) {
            System.out.println("Alerta: Não há cartas disponíveis nem na compra nem no descarte!");
            return;
        }

        Carta cartaComprada = pilhaCompra.desempilhar();
        jogador.receberCarta(cartaComprada);
        System.out.println(jogador.getNome() + " comprou a " + cartaComprada);
    }

    private void verificarENecessitarReciclagem() {
        if (pilhaCompra.estaVazia()) {
            reciclarDescarte();
        }
    }

    private void reciclarDescarte() {
        System.out.println("\n[Aviso] Pilha de compra vazia! Reciclando cartas da pilha de descarte...");

        if (pilhaDescarte.estaVazia()) {
            System.out.println("[Aviso] Não há cartas na pilha de descarte para reciclar!");
            return;
        }

        Carta[] temporario = esvaziarPilhaParaVetor(pilhaDescarte);

        embaralhar(temporario);
        carregarPilhaCompra(temporario);

        System.out.println("Sucesso: Nova pilha de compra criada com " + pilhaCompra.getTamanho() + " cartas!\n");
    }

    private Carta[] esvaziarPilhaParaVetor(Pilha pilha) {
        int tamanho = pilha.getTamanho();
        Carta[] vetor = new Carta[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = pilha.desempilhar();
        }
        return vetor;
    }

    private int obterEscolhaValida(Jogador jogador) {
        while (true) {
            try {
                String entrada = scan.nextLine().trim();
                int escolha = Integer.parseInt(entrada);

                if (escolha == -1) {
                    return -1;
                }

                if (escolha >= 0 && escolha < jogador.getQtdCartas()) {
                    return escolha;
                }

                System.out.print("Índice inválido! Digite um valor de 0 a " + (jogador.getQtdCartas() - 1) + " (ou -1): ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Por favor, digite um número inteiro correspondente (ou -1): ");
            }
        }
    }
}