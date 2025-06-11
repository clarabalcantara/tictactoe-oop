package tictactoe_oop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

public class AplicacaoJM extends JFrame {
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
					AplicacaoJM frame = new AplicacaoJM();
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
	public AplicacaoJM() {
		String nomeJogador = JOptionPane.showInputDialog("Qual o nome do jogador?");
	    if (nomeJogador == null) System.exit(0); // Cancelado

	    // Criar painel com slider
	    JSlider slider = new JSlider(1, 2, 1); // de 1 a 2
	    slider.setMajorTickSpacing(1);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    slider.setSnapToTicks(true);

	    JPanel painel = new JPanel(new BorderLayout());
	    painel.add(new JLabel("Escolha a dificuldade:"), BorderLayout.NORTH);
	    painel.add(slider, BorderLayout.CENTER);

	    int result = JOptionPane.showConfirmDialog(null, painel, "Dificuldade",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    if (result != JOptionPane.OK_OPTION) System.exit(0);

	    int nivel = slider.getValue(); // 1 ou 2

	    jogoDaVelha = new JogoDaVelha(nomeJogador, nivel);
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
	            	jogarMaquina();
	            } catch (Exception ie) {
	            	System.out.println("erro");
	            } finally {
	            	if (!jogoDaVelha.isJogando()) {
	            		toggleBotoes();
	            		replayBtn.setEnabled(true);
	            		System.out.println(jogoDaVelha.getFoto());
	            		
	            		int result = jogoDaVelha.getResultado();
	            		String vencedor = "Jogando...";
	            		try {
	            			if (result == 1) vencedor = jogoDaVelha.getNomeJogador() + " venceu!";
	            			else if (result == 2) vencedor = "Máquina venceu.";
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
	
	private void jogarMaquina() throws Exception {
    	int escolhido = jogoDaVelha.maquinaEscolherPosicao();
    	JButton botao = botoes[escolhido];
    	preencherCelula(botao, escolhido);
	}
	
	private void preencherCelula(JButton cell, int pos) throws Exception {
        boolean vezDaMaquina = ordem % 2 != 0;
        
        if (vezDaMaquina) {
        	System.out.println("MAQUINA");
        	jogoDaVelha.jogarMaquina(pos);
        	cell.setText("O");
        } else {
        	System.out.println("JOGADOR");
        	jogoDaVelha.jogarJogador(pos);
        	cell.setText("X");
        }
	    ordem++;
	}
}
