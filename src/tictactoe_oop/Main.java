package tictactoe_oop;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random rand = new Random();
		JogoDaVelha jogo = new JogoDaVelha('X', 'O');
		for (int i = 9; i > 0; i--) {
			jogo.jogaJogador(1, rand.nextInt(0, 9));
			jogo.jogaJogador(2, rand.nextInt(0, 9));
			System.out.println(jogo.getFoto());
			System.out.println(jogo.getResultado()); 
		}
	}

}
