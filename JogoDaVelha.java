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

    public JogoDaVelha(String simbolo1, String simbolo2) { // construtor jxj
        this.simbolos[0] = simbolo1; this.simbolos[1] = simbolo2;
        this.contraMaquina = false;
        // iniciar tabuleiro p poder ficar acrecentando os simbolos nas posições
        for (int i=0; i<9; i++){
            this.celulas[i] = "";// ai if ( celulas[posicao].equals("")) == true = livre
        }
    }
    public JogoDaVelha(String nomeJogador1, int nivel) { } // consturo mxj !!!!!!!!!!!!!!!!!!

    public void jogaJogador(int numeroJogador, int posicao) {
     // numerojogador é o indice da lista la do simbolo p acessar x ou bola
         if (numeroJogador < 0||numeroJogador > 1) return; // casos estranhos
         if (posicao <0||posicao >= 9) return; //fora do limtie
         if (!celulas[posicao].equals("")) return; // já está ocupada (basica)

         String simbolo = simbolos[numeroJogador]; // x ou bola
         celulas[posicao] = simbolo; // nova atribuição ao elem.da poscuao
         historico.put(posicao, simbolo);
         quantidadeJogadas++;
        }
    public void jogaMaquina() {} // maquina !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public boolean terminou() {
        return (getResultado() != -1)||(quantidadeJogadas == 9) ; //ganhou ou empate
    }

    public int getResultado() {
        // pra nao usar matriz....
        int[] vitoriaLinha1 = {0, 1, 2};
        int[] vitoriaLinha2 = {3, 4, 5};
        int[] vitoriaLinha3 = {6, 7, 8};
        int[] vitoriaColuna1 = {0, 3, 6};
        int[] vitoriaColuna2 = {1, 4, 7};
        int[] vitoriaColuna3 = {2, 5, 8};
        int[] vitoriaDiagonal1 = {0, 4, 8};
        int[] vitoriaDiagonal2 = {2, 4, 6};
    }

    public String getSimbolo(int numeroJogador) {}

    public String getFoto() {}

    public ArrayList<Integer> getPosicoesDisponiveis() {}

    public LinkedHashMap<Integer, String> getHistorico() {}

        }