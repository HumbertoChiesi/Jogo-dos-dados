import javax.swing.*;
import java.awt.*;

/**
 * Cria interface com o usuario (janela, botoes, etc..)
 *
 * @author Humberto Chiesi Neto
 *
 * @version 21/11/2021
 *
 */
public class Gui extends JFrame {
    private PainelJogo painelJogo;                          //tela da jogo

    private JToolBar barraComandos = new JToolBar();        //barra que contera os botões do jogo

    private JButton jbReiniciar = new JButton("Reiniciar");
    private JButton jbSair = new JButton("Sair");

    public Gui(int qntdDados, int tentativas, int cndVitoriaX, int cndVitoriaY, int intervalo) {
        super("Jogo dos dados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        setVisible(true);

        painelJogo = new PainelJogo(qntdDados, tentativas, cndVitoriaX, cndVitoriaY, intervalo);

        barraComandos.add(jbReiniciar);                     // Botao de Reiniciar
        barraComandos.add(jbSair);                          // Botao de Sair

        painelJogo.setBackground(Color.WHITE);

        add(barraComandos, BorderLayout.NORTH);
        add(painelJogo  , BorderLayout.CENTER);

        //quando clicado o botão reiniciar irá fechar a interface atual e chamará a classe Main novamente
        jbReiniciar.addActionListener(e ->{
            setVisible(false);
            dispose();
            App.main(null);
        });
        //quando clicado o botão sair matará o programa
        jbSair.addActionListener(e ->{
            System.exit(0);
        });
    }
}
