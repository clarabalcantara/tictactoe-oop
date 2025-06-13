package tictactoe_oop;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JogoDaVelha {
    private Character[] celulas = new Character[9]; // Tabuleiro 3x3
    private Character[] simbolos = new Character[2]; // 'X' ou 'O'
    private LinkedHashMap<Integer, Character> historico = new LinkedHashMap<>(); // Histórico de jogadas
    private int quantidadeJogadas = 0; // Se == 9, é empate
    private boolean contraMaquina = false;
    private boolean jogando = false;
    private String nomeJogador = "";
    private int nivelEsperteza; // 1 ou 2

    public JogoDaVelha(Character simbolo1, Character simbolo2) {
        if (simbolo1 == ' ' || simbolo2 == ' ') {
            throw new IllegalArgumentException("Símbolos não podem ser espaços em branco.");
        }
        this.simbolos[0] = simbolo1;
        this.simbolos[1] = simbolo2;
        iniciarTabuleiro();
    }

    public JogoDaVelha(String nomeJogador1, int nivel) {
        this.simbolos[0] = 'X';
        this.simbolos[1] = 'M';
        this.contraMaquina = true;
        this.nivelEsperteza = nivel;
        this.nomeJogador = nomeJogador1;
        iniciarTabuleiro();
    }

    public void setDificuldade(int dificuldade) {
        if (dificuldade == 1 || dificuldade == 2)
            this.nivelEsperteza = dificuldade;
    }
    
    public int getDificuldade() {
    	return this.nivelEsperteza;
    }

    public String getNomeJogador() {
        return this.nomeJogador;
    }
    // metodo p retornar historioc
    public LinkedHashMap<Integer, Character> getHistorico() {
        return historico;
    }


    public Character getSimbolo(int numeroJogador) {
        return simbolos[--numeroJogador];
    }

    public void jogarJogador(int posicao) throws Exception {
        if (!contraMaquina) {
            throw new Exception("Este método é para jogo contra a máquina.");
        }
        jogarJogador(1, posicao); // Jogador 1 (X)
    }

    public void jogarMaquina(int posicao) throws Exception {
        if (!contraMaquina) {
            throw new Exception("Este método é para jogo contra a máquina.");
        }
        jogarJogador(2, posicao); // Jogador 2 (O)
    }

    public void jogarJogador(int numeroJogador, int posicao) throws Exception {
        if (!isJogando()) jogando = true;

        if (numeroJogador < 1 || numeroJogador > 2 || posicao < 0 || posicao >= 9 || celulas[posicao] != ' ') {
            throw new Exception("Jogada inválida.");
        }

        // Executa a jogada
        celulas[posicao] = getSimbolo(numeroJogador);
        historico.put(posicao, getSimbolo(numeroJogador)); // Armazena o histórico
        quantidadeJogadas++;

        if (terminou()) {
            jogando = false;
        }
    }

    public ArrayList<Integer> getPosicoesDisponiveis() {
        ArrayList<Integer> livres = new ArrayList<>();
        for (int i = 0; i < celulas.length; i++) {
            if (celulas[i] == ' ') {
                livres.add(i);
            }
        }
        return livres;
    }

    public String exibirTabuleiro() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(celulas[i] == ' ' ? ' ' : celulas[i]);
            if ((i + 1) % 3 == 0) {
                sb.append("\n");
            } else {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public int maquinaEscolherPosicao() {
        if (this.nivelEsperteza >= 2) {
            return posicaoDificil();
        } else {
            return posicaoFacil();
        }
    }

    private int posicaoDificil() {
        ArrayList<Integer> livres = getPosicoesDisponiveis();
        Character simboloMaquina = simbolos[1];
        Character simboloJogador = simbolos[0];

        // Verifica se a máquina pode ganhar
        for (int pos : livres) {
            celulas[pos] = simboloMaquina;
            if (getResultado() == 2) {
                celulas[pos] = ' ';
                return pos;
            }
            celulas[pos] = ' ';
        }

        // Bloqueia o jogador caso ele possa vencer
        for (int pos : livres) {
            celulas[pos] = simboloJogador;
            if (getResultado() == 1) {
                celulas[pos] = ' ';
                return pos;
            }
            celulas[pos] = ' ';
        }

        // Joga no centro se disponível
        if (celulas[4] == ' ') return 4;

        // Joga nos cantos se disponíveis
        int[] cantos = {0, 2, 6, 8};
        for (int canto : cantos) {
            if (celulas[canto] == ' ') return canto;
        }

        // Qualquer outra posição aleatória
        return livres.get((int) (Math.random() * livres.size()));
    }

    private int posicaoFacil() {
        ArrayList<Integer> livres = getPosicoesDisponiveis();
        return livres.get((int) (Math.random() * livres.size()));
    }

    public void reiniciar() {
        iniciarTabuleiro();
        jogando = false;
        quantidadeJogadas = 0;
    }

    public boolean terminou() {
        return getResultado() != -1; // -1 = jogo não terminou
    }

    public boolean isJogando() {
        return jogando && getPosicoesDisponiveis().size() > 0;
    }

    public int getResultado() {
		// pra nao usar matriz...
		// ia fazer um 8 listas mas com esse tanto de if acho q seria mais eficiente do que ter que criar arrau
			/*
	      0 1 2
	      3 4 5
	      6 7 8
			 */
		// verificações por linha/coluna/diagonal - n sei se esse tanto de if seria a melhor forma alvez uma funcao seria melhrp
		// supor q simbolo1 = X so p eu testar mentalmente
		if (celulas[0] != ' ' && celulas[0] == celulas[1] && celulas[0] == celulas[2]) {
			return celulas[0] == simbolos[0] ? 1:2; // se X = X == Jogador 1 vence se nao Joagdor 2
		}
		if (celulas[3] != ' ' && celulas[3] == celulas[4] && celulas[3] == celulas[5])
			return celulas[3] == simbolos[0] ? 1:2;
		if (celulas[6] != ' ' && celulas[6] == celulas[7] && celulas[6] == celulas[8])
			return celulas[6] == simbolos[0] ? 1:2;

		if (celulas[0] != ' ' && celulas[0] == celulas[3] && celulas[0] == celulas[6])
			return celulas[0] == simbolos[0] ? 1:2;
		if (celulas[1] != ' ' && celulas[1] == celulas[4] && celulas[1] == celulas[7])
			return celulas[1] == simbolos[0] ? 1:2;
		if (celulas[2] != ' ' && celulas[2] == celulas[5] && celulas[2] == celulas[8])
			return celulas[2] == simbolos[0] ? 1:2;

		if (celulas[0] != ' ' && celulas[0] == celulas[4] && celulas[0] == celulas[8])
			return celulas[0] == simbolos[0] ? 1:2;
		if (celulas[2] != ' ' && celulas[2] == celulas[4] && celulas[2] == celulas[6])
			return celulas[2] == simbolos[0] ? 1:2;
		// empate
		if (quantidadeJogadas == 9)
			return 0;
		// ainda n t erminou
		return -1;
	}

    private void iniciarTabuleiro() {
        for (int i = 0; i < 9; i++) celulas[i] = ' ';
    }
}
