package tictactoe_oop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

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
    private JLabel jogandoLbl = new JLabel("Jogando...");
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AplicacaoJM frame = new AplicacaoJM();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AplicacaoJM() {
        String nomeJogador = solicitarNomeJogador();
        int nivel = configurarDificuldade();

        jogoDaVelha = new JogoDaVelha(nomeJogador, nivel);
        configurarJanela();
        configurarHeader();
        configurarBotoes();
    }

    private String solicitarNomeJogador() {
        String nomeJogador = JOptionPane.showInputDialog("Qual o nome do jogador?");
        if (nomeJogador == null || nomeJogador.isBlank()) System.exit(0);
        return nomeJogador;
    }

    private int configurarDificuldade() {
        JSlider slider = new JSlider(1, 2, 1);
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
        return slider.getValue();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 300);
        setSize(420, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 97, 203));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void configurarHeader() {
        JPanel header = new JPanel();
        header.setBackground(new Color(192, 97, 203));
        header.setBounds(12, 10, 376, 106);
        contentPane.add(header);
        header.setLayout(null);

        replayBtn.setBounds(210, 14, 154, 33);
        replayBtn.setFont(new Font("Arial", Font.BOLD, 16));
        replayBtn.setBackground(new Color(220, 138, 221));
        replayBtn.setForeground(Color.WHITE);
        replayBtn.setEnabled(false);
        replayBtn.addActionListener(e -> reiniciar());
        header.add(replayBtn);

        jogandoLbl.setBackground(new Color(192, 97, 203));
        jogandoLbl.setForeground(Color.WHITE);
        jogandoLbl.setFont(new Font("Arial", Font.BOLD, 16));
        jogandoLbl.setBounds(20, 22, 180, 17);
        header.add(jogandoLbl);

        configurarDificuldadeSlider(header);
    }

    private void configurarDificuldadeSlider(JPanel header) {
        JLabel dificuldadeLbl = new JLabel("Dificuldade: ");
        dificuldadeLbl.setFont(new Font("Arial", Font.BOLD, 16));
        dificuldadeLbl.setForeground(Color.WHITE);
        dificuldadeLbl.setBounds(20, 70, 177, 25);
        header.add(dificuldadeLbl);

        JSlider dificuldadeSlider = new JSlider(1, 2, jogoDaVelha.getDificuldade());
        dificuldadeSlider.setBounds(210, 58, 156, 48);
        dificuldadeSlider.setMajorTickSpacing(1);
        dificuldadeSlider.setPaintTicks(true);
        dificuldadeSlider.setPaintLabels(true);
        dificuldadeSlider.setSnapToTicks(true);
        dificuldadeSlider.setBackground(new Color(192, 97, 203));
        dificuldadeSlider.addChangeListener(e -> jogoDaVelha.setDificuldade(dificuldadeSlider.getValue()));
        header.add(dificuldadeSlider);
    }

    private void configurarBotoes() {
        JPanel buttons = new JPanel();
        buttons.setBounds(12, 127, 382, 323);
        contentPane.add(buttons);
        buttons.setLayout(new GridLayout(3, 3, 0, 0));

        for (int i = 0; i < 9; i++) {
            buttons.add(criarBotao(i));
        }
    }

    private JButton criarBotao(int idx) {
        JButton btn = new JButton("");
        btn.setFont(new Font("Monospaced", Font.BOLD, 48));
        btn.setBackground(new Color(220, 138, 221));
        btn.setForeground(Color.WHITE);
        botoes[idx] = btn;

        btn.addActionListener(e -> {
            try {
                preencherCelula(btn, idx);
                if (verificarFimDeJogo()) return;
                jogarMaquina();
                verificarFimDeJogo();
            } catch (Exception ex) {
                System.out.println("Pode clicar na mesma celular n vei");
            }
        });
        return btn;
    }

    private void preencherCelula(JButton cell, int pos) throws Exception {
        boolean vezDaMaquina = ordem % 2 != 0;
        if (vezDaMaquina) jogoDaVelha.jogarMaquina(pos);
        else jogoDaVelha.jogarJogador(pos);
        cell.setText(jogoDaVelha.getSimbolo(vezDaMaquina ? 2 : 1).toString());
        ordem++;
    }

    private void jogarMaquina() throws Exception {
        if (ordem % 2 != 0) {
            int escolhido = jogoDaVelha.maquinaEscolherPosicao();
            preencherCelula(botoes[escolhido], escolhido);
        }
    }

    private boolean verificarFimDeJogo() {
        if (!jogoDaVelha.isJogando()) {
            toggleBotoes();
            replayBtn.setEnabled(true);
            int result = jogoDaVelha.getResultado();
            String vencedor = switch (result) {
                case 1 -> jogoDaVelha.getNomeJogador() + " venceu!";
                case 2 -> "Máquina venceu.";
                default -> "Empate.";
            };
            jogandoLbl.setText(vencedor);
            return true;
        }
        return false;
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
        jogandoLbl.setText("Jogando...");
    }
}
