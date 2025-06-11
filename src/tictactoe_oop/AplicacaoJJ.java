package tictactoe_oop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AplicacaoJJ extends JFrame {
	private final JogoDaVelha jogoDaVelha;
	private static final long serialVersionUID = 1L;
	private int ordem = 0;
	private final JButton[] botoes = new JButton[9];
	private final JButton replayBtn = new JButton("Jogar denovo");
	JLabel jogandoLbl = new JLabel("Jogando...");
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacaoJJ frame = new AplicacaoJJ();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AplicacaoJJ() {
//		String nomeJogador = JOptionPane.showInputDialog("Qual o nome do jogador?");
//	    if (nomeJogador == null) System.exit(0); // Cancelado
	    jogoDaVelha = new JogoDaVelha('X', 'O');
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setSize(420, 440);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 97, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBackground(new Color(192, 97, 203));
		header.setBounds(12, 10, 376, 55);
		contentPane.add(header);
		
		JPanel buttons = new JPanel();
		buttons.setBounds(12, 65, 376, 323);
		contentPane.add(buttons);
		buttons.setLayout(new GridLayout(3, 3, 0, 0));
		
		
		for (int i = 0; i < 9; i++) {
		    buttons.add(criarBotao(i));
		}
		replayBtn.setBounds(210, 14, 154, 33);
		
		replayBtn.setFont(new Font("Monospaced", Font.BOLD, 16));
		replayBtn.setBackground(new Color(220, 138, 221));
		replayBtn.setForeground(Color.WHITE);
		replayBtn.setEnabled(false);
		replayBtn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	reiniciar();
	        }
	    });
		header.setLayout(null);
		header.add(replayBtn);
		jogandoLbl.setBackground(new Color(192, 97, 203));
		jogandoLbl.setForeground(new Color(255, 255, 255));
		
		
		jogandoLbl.setFont(new Font("Monospaced", Font.BOLD, 16));
		jogandoLbl.setBounds(12, 22, 180, 17);
		header.add(jogandoLbl);
		

		
	}
	
	private JButton criarBotao(int idx) {
	    JButton btn = new JButton("");
	    btn.setFont(new Font("Monospaced", Font.BOLD, 48));
	    btn.setBackground(new Color(220, 138, 221));
	    btn.setForeground(Color.WHITE);
	    botoes[idx] = btn;

	    btn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	            	preencherCelula(btn, idx);
	            } catch (Exception ie) {
	            	System.out.println("erro");
	            } finally {
	            	if (!jogoDaVelha.isJogando()) {
	            		toggleBotoes();
	            		replayBtn.setEnabled(true);
	            		System.out.println(jogoDaVelha.getFoto());
	            		
	            		int result = jogoDaVelha.getResultado();
	            		System.out.println(result);
	            		String vencedor = "Jogando...";
	            		try {
	            			if (result > 0) vencedor = jogoDaVelha.getSimbolo(result - 1) + " venceu!";
	            			else vencedor = "Empate.";
	            		} catch (Exception ee) {}
	            		jogandoLbl.setText(vencedor);
	            	}
	            }
	            
	        }
	    });
	    return btn;
	}
	
	private void toggleBotoes() {
		for (int i = 0; i < 9; i++) {
			JButton btn = botoes[i];
			btn.setEnabled(!btn.isEnabled());
		}
	}
	
	private void reiniciar() {
		for (int i = 0; i < 9; i++) {
			botoes[i].setText("");
		}
    	jogoDaVelha.reiniciar();
    	toggleBotoes();
    	replayBtn.setEnabled(false);
    	ordem = 0;
    	jogandoLbl.setText("Jogando...");
	}
	
	private void preencherCelula(JButton cell, int pos) throws Exception {
        boolean jogador1 = ordem % 2 == 0;
        
        if (jogador1) {
        	System.out.println("Player 1");
        	jogoDaVelha.jogarJogador(1, pos);
        	cell.setText(jogoDaVelha.getSimbolo(0).toString());
        } else {
        	System.out.println("Player 2");
        	jogoDaVelha.jogarJogador(2, pos);
        	cell.setText(jogoDaVelha.getSimbolo(1).toString());
        }
	    ordem++;
	}
}
