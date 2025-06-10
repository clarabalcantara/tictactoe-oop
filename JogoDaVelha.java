// separando funcionalidades de arquivos: regras e verificações de vitoria e empate no arquivo
// numero de jogadas = 9 (acaba em empate) -> ate ai jogo segue
// p saber se vai ser jpgadador x jogador ou maq x jogador == bool

/*
o que eu preciso saber no joog (def. atributos) -> encapsulados (private)
- quem é X ou bola
- como ta o tabuleiro (checar(
- se é com maquina
- se ss, o nivel da maquina (1||2)
- qtd jogadas ate empate (9)
- qtd de jogadas ja foram (checar)
*/

// vence quando = 012, 345, 678, 036, 147, 258, 048 e 246
public class JogoDaVelha {
    private String[] celulas = new String[9]; // 3x3
    private String[] simbolos = new String[2]; // X ou bola
    private LinkedHashMap<Integer, String> historico = new LinkedHashMap<>(); // quais jogadas foram
    private int quantidadeJogadas = 0; // se == 9 : empate
    private boolean contraMaquina;
    private int nivelEsperteza; // 1 ou 2

    public JogoDaVelha(String simbolo1, String simbolo2) { // jxj
        this.simbolos[0] = simbolo1; this.simbolos[1] = simbolo2;
        this.contraMaquina = false;
        // iniciar tabuleiro p poder ficar acrecentando os simbolos nas posições
        for (int i=0; i<9; i++){

        }
    }

    public JogoDaVelha(String nomeJogador1, int nivel) {}

    public void jogaJogador(numeroJogador, posicao) {}

    public boolean terminou() {}

    public int getResultado() {}

    public String getSimbolo(int numeroJogador) {}

    public String getFoto() {}

    public ArrayList<Integer> getPosicoesDisponiveis() {}

    public LinkedHashMap<Integer, String> getHistorico() {}

        }