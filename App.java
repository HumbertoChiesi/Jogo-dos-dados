public class App {
    public static void main(String[] args) {
        InputGui inputs = new InputGui();
        new Gui(inputs.nDados, inputs.tentativas, inputs.cndVitoriaX, inputs.cndVitoriaY, 2000);
    }
}
