import dado.Dado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

/**
 * Tela onde será exibido o jogo dos dados para o usuário
 *
 * @author Humberto Chiesi Neto
 *
 * @version 21/11/2021
 *
 */
public class PainelJogo extends JPanel implements MouseListener {
    int nDados;
    int tentativas;
    int cndVitoriaX;                                    //condição de vitoria 1
    int cndVitoriaY;                                    //condição de vitoria 2
    int intervalo;                                      //indica intervalo de espera entre animações
    boolean ganhou;

    int jogadas = 0;                                    //contador de jogadas utilizado durante um jogo

    EstadoJogo estado = EstadoJogo.NENHUM;              //estado do jogo

    Dado dado;                                          //objeto da classe dado para ser utilizado no jogo

    Timer timer;                                        //Timer para utilizarmos nas animações

    ArrayList<Integer> listaAux = new ArrayList<>();    //lista que guarda todos lados dos dados jogados em uma tentativa


    public PainelJogo(int qntdDados, int tentativas, int cndVitoriaX, int cndVitoriaY, int intervalo) {
        this.nDados = qntdDados;
        this.tentativas = tentativas;
        this.cndVitoriaX = cndVitoriaX;
        this.cndVitoriaY = cndVitoriaY;
        this.intervalo = intervalo;
        this.ganhou = false;
        this.estado = EstadoJogo.INICIAL;

        this.dado = new Dado(6);

        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        desenharJogo(g);
    }

    //função que desenha todas as telas do jogo
    public void desenharJogo(Graphics g) {
        super.paintComponent(g);
        URL url;

        //se o estado de jogo for INICIAL será mostrado a tela inicial do jogo
        if (this.estado == EstadoJogo.INICIAL) {
            try {
                url = this.getClass().getResource("imagens/telas/titulo_jogo.png");
                Image imagemTitulo = new ImageIcon(url).getImage();
                url = this.getClass().getResource("imagens/telas/dados_inicial.gif");
                Image logoGif = new ImageIcon(url).getImage();
                g.drawImage(imagemTitulo, 150, 150, this);
                g.drawImage(logoGif, 270, 350, this);
            } catch (Exception ignored) {
            }
        }
        else if (this.estado == EstadoJogo.INICIAR_JOGO) {
            this.estado = EstadoJogo.ESPERANDO;
            comecarJogo();
        }

        //se o estado do jogo for JOGANDO_DADOS mostrará a tela de espera entre jogadas
        else if (this.estado == EstadoJogo.JOGANDO_DADOS){
            url = this.getClass().getResource("imagens/telas/r_dice2.gif");
            Image jogandoGif = new ImageIcon(url).getImage();
            url = this.getClass().getResource("imagens/telas/msg_jogando.png");
            Image msgJogando = new ImageIcon(url).getImage();
            g.drawImage(jogandoGif, 160, 50, PainelJogo.this);
            g.drawImage(msgJogando, 150, 650, PainelJogo.this);
        }

        //se o estado do jogo for DADOS_JOGADOS mostrará na tela os lados dos dados
        //e a soma total de todos eles
        else if (this.estado == EstadoJogo.DADOS_JOGADOS){
            int aux = 0;
            int soma = 0;
            for (Integer l : listaAux){
                soma+=l;
                url = this.getClass().getResource("imagens/lados/lado"+l+".png");
                Image ladoDado = new ImageIcon(url).getImage();
                if (aux < 3){
                    g.drawImage(ladoDado, 245*aux+30, 20, PainelJogo.this);
                } else g.drawImage(ladoDado, 245*(aux-3)+30, 280, PainelJogo.this);
                aux++;
            }
            url = this.getClass().getResource("imagens/telas/msg_soma.png");
            Image msgSoma = new ImageIcon(url).getImage();
            g.drawImage(msgSoma, 90, 625, PainelJogo.this);
            Font font = new Font("Verdana", Font.BOLD, 64);
            g.setFont(font);
            g.drawString( String.valueOf(soma),620, 685);
        }

        //se o estado do jogo for FINAL será colocado na tela as imagens
        //imagens essas que variam se o jogador venceu ou perdeu o jogo
        else if (this.estado == EstadoJogo.FINAL){
            if (ganhou){
                url = this.getClass().getResource("imagens/telas/msg_vitoria.png");
                Image msgVitoria = new ImageIcon(url).getImage();
                url = this.getClass().getResource("imagens/telas/dado_vitoria.png");
                Image dadoVitoria = new ImageIcon(url).getImage();
                g.drawImage(msgVitoria, 100, 100, this);
                g.drawImage(dadoVitoria, 220, 320, this);
            } else {
                url = this.getClass().getResource("imagens/telas/msg_derrota.png");
                Image msgDerrota = new ImageIcon(url).getImage();
                url = this.getClass().getResource("imagens/telas/dado_derrota.png");
                Image dadoDerrota = new ImageIcon(url).getImage();
                g.drawImage(msgDerrota, 100, 100, this);
                g.drawImage(dadoDerrota, 220, 320, this);
            }
        }
    }

    //função que realizara a lógica do jogo enquanto muda o EstadoJogo e consequentemente as telas apresentadas
    public void comecarJogo(){
        timer = new Timer(this.intervalo, new ActionListener() {
            boolean paused = true;
            boolean finale = false;

            //Função que se repetira até que o Timer.stop(); seja chamado
            //timer.stop(); será chamado quando alguma das condições de vitoria do jogo for alcancada
            //ou quando o numero de tentativas máxima for atingida
            @Override
            public void actionPerformed(ActionEvent e) {
                if (finale){
                    PainelJogo.this.estado = EstadoJogo.FINAL;
                    timer.stop();
                }else {
                    if (!paused){
                        PainelJogo.this.listaAux.clear();
                        int soma = 0;
                        PainelJogo.this.jogadas++;
                        for(int i = 1; i <= PainelJogo.this.nDados; i++){
                            int lado = dado.sortearLado();
                            soma += lado;
                            PainelJogo.this.listaAux.add(lado);
                        }
                        PainelJogo.this.estado = EstadoJogo.DADOS_JOGADOS;

                        if (PainelJogo.this.jogadas < PainelJogo.this.tentativas){
                            if (soma == PainelJogo.this.cndVitoriaX || soma == PainelJogo.this.cndVitoriaY){
                                PainelJogo.this.ganhou = true;
                                finale = true;
                            }
                        } else {
                            finale = true;
                        }
                    } else {
                        PainelJogo.this.estado = EstadoJogo.JOGANDO_DADOS;
                    }
                    if (paused) paused = false;
                    else paused = true;
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    //se o mouse é pressionado e o estado não é FINAL, o jogo é iniciado
    @Override
    public void mousePressed(MouseEvent e) {
        if (this.estado != EstadoJogo.FINAL){
            this.estado = EstadoJogo.INICIAR_JOGO;
            paint(getGraphics());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
