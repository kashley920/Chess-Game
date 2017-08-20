import javax.swing.JFrame;

public class ChessAI extends JFrame
{
    public ChessAI()
    {
        super("Chess");
        setSize(805, 825);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public static void main(String[] args)
    {
        ChessAI window = new ChessAI();
        window.add(new ChessBoardAI());
        window.setVisible(true);
    }
}
