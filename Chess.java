import javax.swing.JFrame;

public class Chess extends JFrame
{                                 
    public Chess()
    {
        super("Chess");
        setSize(805, 825);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public static void main(String[] args)
    {
        Chess window = new Chess();
        window.add(new ChessBoard());
        window.setVisible(true);
    }
}
