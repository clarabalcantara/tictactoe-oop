package tictactoe_oop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
AplicacaoJJ.java
//Alunos: Davi César M. Leite  e Clara Brito P. N. Alcântara 
Data: 13/06/2025
Descrição: Classe que gerencia a interface gráfica de jogador x jogador
*/
public class AplicacaoJJ extends JFrame {
    private final JogoDaVelha jogoDaVelha;
    private static final long serialVersionUID = 1L;
    private int ordem = 0;
    private final JButton[] botoes = new JButton[9];
    private final JButton replayBtn = new JButton("Jogar denovo");
    private final JButton historicoBtn = new JButton("Histórico");
    private JLabel jogandoLbl = new JLabel();
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AplicacaoJJ frame = new AplicacaoJJ();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AplicacaoJJ() {
        jogoDaVelha = new JogoDaVelha("X", "O");
        configurarJanela();
        configurarHeader();
        configurarBotoes();
        atualizarLblJogador();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 300);
        setSize(420, 480);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 97, 203));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void configurarHeader() {
        JPanel header = new JPanel();
        header.setBackground(new Color(192, 97, 203));
        header.setBounds(12, 10, 376, 90); 
        contentPane.add(header);

        replayBtn.setBounds(210, 14, 154, 33);
        replayBtn.setFont(new Font("Arial", Font.BOLD, 16));
        replayBtn.setBackground(new Color(220, 138, 221));
        replayBtn.setForeground(Color.WHITE);
        replayBtn.setEnabled(false);
        replayBtn.addActionListener(e -> reiniciar());
        header.setLayout(null);
        header.add(replayBtn);
        // config botao historico
        historicoBtn.setBounds(210, 50, 154, 33);
        historicoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        historicoBtn.setBackground(new Color(220, 138, 221));
        historicoBtn.setForeground(Color.WHITE);
        historicoBtn.addActionListener(e -> mostrarHistorico());
        header.add(historicoBtn);

        jogandoLbl.setBackground(new Color(192, 97, 203));
        jogandoLbl.setForeground(Color.WHITE);
        jogandoLbl.setFont(new Font("Arial", Font.BOLD, 16));
        jogandoLbl.setBounds(12, 22, 180, 17);
        header.add(jogandoLbl);
    }

    private void configurarBotoes() {
        JPanel buttons = new JPanel();
        buttons.setBounds(12, 120, 389, 300); 
        contentPane.add(buttons);
        buttons.setLayout(new GridLayout(3, 3, 0, 0));

        for (int i = 0; i < 9; i++) {
            buttons.add(criarBotao(i));
        }
    }

    private JButton criarBotao(int idx) {
        JButton btn = new JButton("");
        btn.setFont(new Font("Arial", Font.BOLD, 48));
        btn.setBackground(new Color(220, 138, 221));
        btn.setForeground(Color.WHITE);
        botoes[idx] = btn;

        btn.addActionListener(e -> {
            try {
                preencherCelula(btn, idx);
            } catch (Exception ex) {
                System.out.println("Erro");
            } finally {
                verificarFimDeJogo();
            }
        });
        return btn;
    }

    private void preencherCelula(JButton cell, int pos) throws Exception {
        int jogador = ordem % 2 == 0 ? 1 : 2;
        jogoDaVelha.jogaJogador(jogador, pos);
        cell.setText(jogoDaVelha.getSimbolo(jogador).toString());
        ordem++;
        atualizarLblJogador();
    }

    private void atualizarLblJogador() {
        int jogador = ordem % 2 == 0 ? 1 : 2;
        jogandoLbl.setText("Vez do " + jogoDaVelha.getSimbolo(jogador).toString());
    }

    private void verificarFimDeJogo() {
        if (jogoDaVelha.terminou()) {
            toggleBotoes();
            replayBtn.setEnabled(true);
            int resultado = jogoDaVelha.getResultado();
            String vencedor = resultado > 0 ? jogoDaVelha.getSimbolo(resultado) + " venceu!" : "Empate.";
            jogandoLbl.setText(vencedor);
        }
    }

    private void toggleBotoes() {
        for (JButton btn : botoes) {
            btn.setEnabled(!btn.isEnabled());
        }
    }

    private void reiniciar() {
        for (JButton btn : botoes) {
            btn.setText("");
        }
        jogoDaVelha.reiniciar();
        toggleBotoes();
        replayBtn.setEnabled(false);
        ordem = 0;
        atualizarLblJogador();
    }
    private void mostrarHistorico() {
    	
        String hist = "";
        for (Integer pos : jogoDaVelha.getHistorico().keySet()) {
            hist += "Posição " + pos + ": " + jogoDaVelha.getHistorico().get(pos) + "\n";
        }
        JOptionPane.showMessageDialog(this, hist, "Histórico de Jogadas", JOptionPane.INFORMATION_MESSAGE);
    }

}
