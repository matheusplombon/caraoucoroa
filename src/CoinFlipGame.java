import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoinFlipGame extends JFrame {
    private JLabel saldoLabel, resultadoLabel, apostaLabel;
    private JPanel historicoPanel;
    private JButton caraButton, coroaButton, pularButton, aumentarButton, diminuirButton, comoJogarButton;
    private int saldo = 5;
    private int valorAposta = 1; // Valor inicial da aposta
    private List<String> historico = new ArrayList<>();

    public CoinFlipGame() {
        super("Jogo de Cara ou Coroa");

        // Configurações gerais da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420); // Aumentei a largura para acomodar os novos componentes
        getContentPane().setBackground(Color.BLACK); // Define o fundo da janela como preto
        setLayout(new BorderLayout(10, 10));

        // Painel superior para saldo, aposta e histórico
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.BLACK);

        // Saldo e Aposta Panel
        JPanel saldoApostaPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        saldoApostaPanel.setBackground(Color.BLACK);

        saldoLabel = new JLabel("Saldo atual: $" + saldo);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        saldoLabel.setForeground(Color.WHITE);
        saldoApostaPanel.add(saldoLabel);

        apostaLabel = new JLabel("Aposta atual: $" + valorAposta);
        apostaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        apostaLabel.setForeground(Color.WHITE);
        saldoApostaPanel.add(apostaLabel);

        topPanel.add(saldoApostaPanel, BorderLayout.NORTH);

        // Histórico Panel
        historicoPanel = new JPanel();
        historicoPanel.setLayout(new BoxLayout(historicoPanel, BoxLayout.Y_AXIS));
        historicoPanel.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(historicoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 120));
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // Botões de Ação Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        diminuirButton = new JButton("Diminuir Aposta");
        diminuirButton.setForeground(Color.WHITE);
        diminuirButton.setBackground(Color.BLUE);
        diminuirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (valorAposta > 1) {
                    valorAposta--;
                    apostaLabel.setText("Aposta atual: $" + valorAposta);
                }
            }
        });
        buttonPanel.add(diminuirButton);

        aumentarButton = new JButton("Aumentar Aposta");
        aumentarButton.setForeground(Color.WHITE);
        aumentarButton.setBackground(Color.BLUE);
        aumentarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (valorAposta < saldo) {
                    valorAposta++;
                    apostaLabel.setText("Aposta atual: $" + valorAposta);
                } else {
                    JOptionPane.showMessageDialog(CoinFlipGame.this,
                            "Você não pode apostar mais do que possui.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(aumentarButton);

        pularButton = new JButton("Pular");
        pularButton.setForeground(Color.WHITE);
        pularButton.setBackground(Color.BLUE);
        pularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogar("pular");
            }
        });
        buttonPanel.add(pularButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Botão "Como Jogar"
        comoJogarButton = new JButton("Como Jogar");
        comoJogarButton.setForeground(Color.WHITE);
        comoJogarButton.setBackground(Color.BLUE);
        comoJogarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirInstrucoes();
            }
        });
        JPanel comoJogarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comoJogarPanel.add(comoJogarButton);
        comoJogarPanel.setBackground(Color.BLACK);
        topPanel.add(comoJogarPanel, BorderLayout.LINE_END);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para resultado e botões Cara/Coroa
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centerPanel.setBackground(Color.BLACK);

        resultadoLabel = new JLabel("Resultado: ");
        resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
        resultadoLabel.setForeground(Color.WHITE);
        resultadoLabel.setOpaque(true);
        resultadoLabel.setBackground(Color.BLACK);
        centerPanel.add(resultadoLabel);

        JPanel buttonPanel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel2.setBackground(Color.BLACK);

        caraButton = new JButton("Cara");
        caraButton.setForeground(Color.WHITE);
        caraButton.setBackground(Color.BLUE);
        caraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogar("cara");
            }
        });
        buttonPanel2.add(caraButton);

        coroaButton = new JButton("Coroa");
        coroaButton.setForeground(Color.WHITE);
        coroaButton.setBackground(Color.BLUE);
        coroaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogar("coroa");
            }
        });
        buttonPanel2.add(coroaButton);

        centerPanel.add(buttonPanel2);

        add(centerPanel, BorderLayout.CENTER);

        // Exibição da janela
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para realizar a jogada
    private void jogar(String escolha) {
        Random random = new Random();
        String[] opcoes = {"cara", "coroa"};
        String resultado = opcoes[random.nextInt(2)];
        String mensagem = "Resultado, " + resultado.toUpperCase() + ", ";

        if (escolha.equals("pular")) {
            mensagem += "Você pulou!";
            resultadoLabel.setBackground(Color.BLUE);
        } else {
            if (valorAposta <= saldo) {
                if (escolha.equals(resultado)) {
                    saldo += valorAposta;
                    mensagem += "Você ganhou $" + valorAposta + "!";
                    resultadoLabel.setBackground(Color.GREEN);
                } else {
                    saldo -= valorAposta;
                    mensagem += "Você perdeu $" + valorAposta + "!";
                    resultadoLabel.setBackground(Color.RED);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Você não pode apostar mais do que possui.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (saldo <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Seu saldo acabou. Fim de jogo!",
                        "Fim de Jogo",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }

        adicionarHistorico(mensagem, escolha.equals(resultado));
        saldoLabel.setText("Saldo atual: $" + saldo);
        resultadoLabel.setText(mensagem);
        resultadoLabel.setForeground(Color.WHITE);
        if (escolha.equals(resultado)) {
            resultadoLabel.setForeground(Color.GREEN);
        } else {
            resultadoLabel.setForeground(Color.RED);
        }
    }

    // Método para adicionar mensagem ao histórico com cor correspondente
    private void adicionarHistorico(String mensagem, boolean ganhou) {
        JLabel label = new JLabel(mensagem);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        if (ganhou) {
            label.setForeground(Color.GREEN);
        } else {
            label.setForeground(Color.RED);
        }

        label.setForeground(Color.WHITE);
        historicoPanel.add(label);
        historicoPanel.revalidate();
        historicoPanel.repaint();

        historico.add(mensagem);
    }

    // Método para exibir as instruções do jogo
    private void exibirInstrucoes() {
        JOptionPane.showMessageDialog(this,
                "Bem-vindo ao Jogo de Cara ou Coroa!\n\n" +
                        "Para jogar, siga estes passos:\n" +
                        "1. Escolha o valor da sua aposta utilizando os botões 'Aumentar Aposta' e 'Diminuir Aposta'.\n" +
                        "2. Escolha entre 'Cara' ou 'Coroa'.\n" +
                        "3. Se desejar, pode 'Pular' a aposta atual.\n" +
                        "4. Clique em 'Cara' ou 'Coroa' para jogar e verificar o resultado.\n" +
                        "5. Se seu saldo zerar, o jogo acabará automaticamente.\n\n" +
                        "Boa sorte!",
                "Como Jogar",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CoinFlipGame();
            }
        });
    }
}
