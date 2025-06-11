package tictactoe_oop;

//separando funcionalidades de arquivos: regras e verificações de vitoria e empate no arquivo
//numero de jogadas = 9 (acaba em empate) -> ate ai jogo segue
//p saber se vai ser jpgadador x jogador ou maq x jogador == bool

/*
o que eu preciso saber no joog (def. atributos) -> encapsulados (private)
- quem é X ou bola
- como ta o tabuleiro (checar(
- se é com maquina
- se ss, o nivel da maquina (1||2)
- qtd jogadas ate empate (9)
- qtd de jogadas ja foram (checar)
 */

//vence quando = 012, 345, 678, 036, 147, 258, 048 e 246
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JogoDaVelha {
	private Character[] celulas = new Character[9]; // 3x3
	private Character[] simbolos = new Character[2]; // X ou bola
	private LinkedHashMap<Integer, Character> historico = new LinkedHashMap<>(); // quais jogadas foram
	private byte quantidadeJogadas = 0; // se == 9 : empate
	private boolean contraMaquina;
	private byte nivelEsperteza; // 1 ou 2

	public JogoDaVelha(Character simbolo1, Character simbolo2) { // construtor jxj
		if (simbolo1 == ' ' || simbolo2 == ' ') {
			throw new IllegalArgumentException("Símbolos não podem ser espaços em branco.");
		}
		this.simbolos[0] = simbolo1; this.simbolos[1] = simbolo2;
		this.contraMaquina = false;
		// iniciar tabuleiro p poder ficar acrecentando os simbolos nas posições
		iniciarTabuleiro();
	}
	
	public JogoDaVelha(String nomeJogador1, byte nivel) {
		this.simbolos[0] = 'X'; this.simbolos[1] = 'O';
		this.contraMaquina = true;
		this.nivelEsperteza = nivel;
		iniciarTabuleiro();
	} // consturo mxj !!!!!!!!!!!!!!!!!!
		

	private void iniciarTabuleiro() {
		for (int i = 0; i < 9; i++) this.celulas[i] = ' ';
	}
	
	public void jogaJogador(int numeroJogador, int posicao) {
		// numerojogador é o indice da lista la do simbolo p acessar x ou bola
		if (contraMaquina) numeroJogador = 0;
		numeroJogador--;
		if (numeroJogador < 0||numeroJogador > 1) return; // casos estranhos
		if (posicao <0||posicao >= 9) return; //fora do limtie
		if (!(celulas[posicao] == ' ')) return; // já está ocupada (basica) OBS: retornar um erro

		Character simbolo = getSimbolo(numeroJogador); // x ou bola
		celulas[posicao] = simbolo; // nova atribuição ao elem.da poscuao
		historico.put(posicao, simbolo);
		quantidadeJogadas++;
	}
	
	public void jogaMaquina() {
		
	} // maquina !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public boolean terminou() {
		return (getResultado() != -1)||(quantidadeJogadas == 9) ; //se nao houve ganho ou empate
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
			System.out.println("entrou aqui");
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
	
	private Character getSimbolo(int numeroJogador) {
		return simbolos[numeroJogador];
	}
	
	// classe stringbuilder eu nao conhecia
	public String getFoto() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<9; i++) {
			sb.append(celulas[i] == ' ' ? ' ' : celulas[i]);
			if ((i+1) %3 == 0) { // 1 - 3 - 9
				sb.append("\n");
			} else {
				sb.append(" | ");
			}
		}
		return sb.toString(); // tinha esquecido de converter p strig pq sao dois tipos difernetss
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

	//acho q poderia usar stringbuilder aqui tbm mas vou no simples dessa vez
	public String exibirHistorico() {
		String resultado = "";
		// pcada input do mapa historico, get a key e o value
		for (var entrada : historico.entrySet()) { // p cada entrada um par posicao e valor
			resultado += "Posição " + entrada.getKey() + ": " + entrada.getValue() + "\n";
		}
		return resultado;
	}
}
