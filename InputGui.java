import javax.swing.*;
import java.awt.*;

/**
 * Classe para pegar os inputs necessaria para o início do jogo
 *
 * @author Humberto Chiesi Neto
 *
 * @version 21/11/2021
 *
 */
public class InputGui {
    int nDados;
    int tentativas;
    int cndVitoriaX;
    int cndVitoriaY;

    public InputGui(){
        JTextField nDadosField = new JTextField(5);
        JTextField tentativasField = new JTextField(5);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        //constroi o JPanel com os text fields necessarios
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(4, 2));
        myPanel.add(new JLabel("Qntd. Dados(1-6):   "));
        myPanel.add(nDadosField);
        myPanel.add(new JLabel("Qntd. Tentativas:   "));
        myPanel.add(tentativasField);
        myPanel.add(new JLabel("Vence com:   "));
        myPanel.add(xField);
        myPanel.add(new JLabel("ou:   "));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Configuracoes do jogo", JOptionPane.OK_CANCEL_OPTION);

        //se o OK for clicado verificamos as entradas estão corretas
        if (result == JOptionPane.OK_OPTION) {
            try {
                this.nDados = Integer.parseInt(nDadosField.getText());
                this.tentativas = Integer.parseInt(tentativasField.getText());
                this.cndVitoriaX = Integer.parseInt(xField.getText());
                this.cndVitoriaY = Integer.parseInt(yField.getText());
                if (this.nDados < 1 || this.nDados > 6){
                    JOptionPane.showMessageDialog(null, "Numero de dados apenas de 1 - 6", "Erro", 0);
                    App.main(null);
                }
                else if (this.tentativas < 0 || this.cndVitoriaX < 0 || this.cndVitoriaY < 0){
                    JOptionPane.showMessageDialog(null, "Apenas numeros positivos!!", "Erro", 0);
                    App.main(null);
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Algum dos valores digitados nao eh um numero", "Erro", 0);
                App.main(null);
            }
        }
        //se o Cancelar for clickado fechamos o programa
        else {
            System.exit(0);
        }
    }
}