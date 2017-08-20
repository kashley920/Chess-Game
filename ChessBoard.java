import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;

public class ChessBoard extends JPanel
    implements MouseListener
{
    private static final int CHESS_BOARD_SIZE = 8;
    private ChessPiece[][] board;
    private Location selectedLoc;
    private boolean turn;
    private int won;
    private King kingW, kingB;
    protected ArrayList<ChessPiece> piecesW, piecesB;
    
    public ChessBoard()
    {
        board = new ChessPiece[CHESS_BOARD_SIZE][CHESS_BOARD_SIZE];
        piecesW = new ArrayList<ChessPiece>();
        piecesB = new ArrayList<ChessPiece>();
        selectedLoc = null;
        turn = true;
        won = 0;
        addMouseListener(this);
        
        // White Pieces
        for(int i = 0; i < CHESS_BOARD_SIZE; i++)
            new Pawn(new Location(6, i), true).putOnBoard(this);
        new Rook(new Location(7, 0), true).putOnBoard(this);
        new Knight(new Location(7, 1), true).putOnBoard(this);
        new Bishop(new Location(7, 2), true).putOnBoard(this);
        new Queen(new Location(7, 3), true).putOnBoard(this);
        new Bishop(new Location(7, 5), true).putOnBoard(this);
        new Knight(new Location(7, 6), true).putOnBoard(this);
        new Rook(new Location(7, 7), true).putOnBoard(this);
        kingW = new King(new Location(7, 4), true);
        kingW.putOnBoard(this);
        
        // Black Pieces
        for(int i = 0; i < CHESS_BOARD_SIZE; i++)
            new Pawn(new Location(1, i), false).putOnBoard(this);
        new Rook(new Location(0, 0), false).putOnBoard(this);
        new Knight(new Location(0, 1), false).putOnBoard(this);
        new Bishop(new Location(0, 2), false).putOnBoard(this);
        new Queen(new Location(0, 3), false).putOnBoard(this);
        new Bishop(new Location(0, 5), false).putOnBoard(this);
        new Knight(new Location(0, 6), false).putOnBoard(this);
        new Rook(new Location(0, 7), false).putOnBoard(this);
        kingB = new King(new Location(0, 4), false);
        kingB.putOnBoard(this);
    }
    
    public void paint(Graphics g) 
    {
        super.paint(g);
        g.drawImage(new ImageIcon("ChessBoard.gif").getImage(), 0, 0, null);
        if(selectedLoc != null) {
            g.setColor(Color.YELLOW);
            g.fillRect(selectedLoc.getCol() * 100 + 10, selectedLoc.getRow() * 100 + 10, 80, 80);
            ArrayList<Location> moveLocs = get(selectedLoc).getMoveableLocations();
            for(Location loc : moveLocs)
                g.fillRect(loc.getCol() * 100 + 10, loc.getRow() * 100 + 10, 80, 80);
            g.setColor(Color.RED);
            ArrayList<Location> checkLocs = getCheckLocations(get(selectedLoc));
            for(Location loc : checkLocs)
                g.fillRect(loc.getCol() * 100 + 10, loc.getRow() * 100 + 10, 80, 80);
        }
        for(int r = 0; r < CHESS_BOARD_SIZE; r++) {
            for(int c = 0; c < CHESS_BOARD_SIZE; c++) {
                ChessPiece piece = board[r][c];
                if(piece != null) {
                    g.drawImage(piece.getImage(), c * 100 + 20, r * 100 + 20, null);
                }
            }
        }
        if(won != 0) {
            if(won == 1)
                g.drawImage(new ImageIcon("WhiteWin.gif").getImage(), 100, 150, null);
            else if(won == 2)
                g.drawImage(new ImageIcon("BlackWin.gif").getImage(), 100, 150, null);
            else
                g.drawImage(new ImageIcon("Stalemate.gif").getImage(), 100, 150, null);
        }
        g.dispose();
    }
    public void put(Location loc, ChessPiece piece)
    {
        board[loc.getRow()][loc.getCol()] = piece;
    }
    public void remove(Location loc)
    {
        board[loc.getRow()][loc.getCol()] = null;
    }
    public void add(ChessPiece piece)
    {
        if(piece.getColor())
            piecesW.add(piece);
        else
            piecesB.add(piece);
    }
    public void remove(ChessPiece piece)
    {
        if(piece.getColor()) {
            for(int i = 0; i < piecesW.size(); i++) {
                if(piecesW.get(i).equals(piece)) {
                    piecesW.remove(i);
                    return;
                }
            }
        }
        else {
            for(int i = 0; i < piecesW.size(); i++) {
                if(piecesB.get(i).equals(piece)) {
                    piecesB.remove(i);
                    return;
                }
            }
        }
    }
    public ChessPiece get(Location loc)
    {
        if(loc == null || !isValid(loc))
            return null;
        return board[loc.getRow()][loc.getCol()];
    }
    public ChessPiece get(int row, int col)
    {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    public boolean isValid(Location loc)
    {
        return loc.getRow() >= 0 && loc.getRow() < CHESS_BOARD_SIZE &&
               loc.getCol() >= 0 && loc.getCol() < CHESS_BOARD_SIZE;
    }
    public void mousePressed(MouseEvent e)
    {
        if(won != 0)
            return;
        int col = CHESS_BOARD_SIZE * e.getX() / getWidth();
        int row = CHESS_BOARD_SIZE * e.getY() / getHeight();
        Location loc = new Location(row, col);
        if(isValid(loc)) {
           if(selectedLoc == null && hasTurn(get(loc))) {
               selectedLoc = loc;
           }
           else if(selectedLoc != null) { 
               ChessPiece piece = get(selectedLoc);
               ArrayList<Location> moveLocs = piece.getMoveableLocations();
               ArrayList<Location> checkLocs = getCheckLocations(piece);
               if(moveLocs.contains(loc) && !checkLocs.contains(loc)) {
                   piece.moveTo(loc);
                   if(piece instanceof Pawn && (loc.getRow() == 0 || loc.getRow() == 7)) {
                       piece.removeFromBoard();
                       new Queen(piece.getLocation(), piece.getColor()).putOnBoard(this);
                   }
                   selectedLoc = null;
                   nextTurn();
               }
               else if(piece.getLocation().equals(loc)) {
                   selectedLoc = null;
               } // If a location which does not contain the piece or a moveable location is
           }     // clicked, nothing happens
        }
        repaint();
    }
    public ArrayList<Location> getCheckLocations(ChessPiece piece)
    {
        if(!piece.getBoard().equals(this))
            throw new IllegalArgumentException("This piece is not on this board");
        ArrayList<Location> checkLocs = new ArrayList<Location>(),
                            moveLocs = piece.getMoveableLocations();
        for(Location loc : moveLocs) {
            Location oldLoc = piece.getLocation();
            if(piece instanceof King && piece.getNumMoves() == 0 && 
              (loc.getCol() == 0 || loc.getCol() == 7)) { // Castling
                ArrayList<Location> passingLocs = new ArrayList<Location>();
                int numMoves = 1;
                passingLocs.add(piece.getLocation());
                if(loc.getCol() == 0) {
                    passingLocs.add(new Location(loc.getRow(), 3));
                    passingLocs.add(new Location(loc.getRow(), 2));
                }
                else {
                    passingLocs.add(new Location(loc.getRow(), 5));
                    passingLocs.add(new Location(loc.getRow(), 6));
                }
                for(Location tempLoc : passingLocs) // All should be null in grid
                {
                    piece.moveTo(tempLoc);
                    numMoves++;
                    if(inCheck(piece.getColor())) {
                        checkLocs.add(loc);
                        break;
                    }
                }
                piece.moveTo(oldLoc);
                piece.undo(numMoves);
            }
            else {
                ChessPiece temp = get(loc);
                piece.moveTo(loc);
                if(inCheck(piece.getColor()))
                    checkLocs.add(loc);
                piece.moveTo(oldLoc);
                piece.undo(2);
                if(temp != null)
                    temp.putOnBoard(this);
            }
        }
        return checkLocs;
    }
    private boolean hasTurn(ChessPiece piece)
    {
        if(piece == null)
            return false;
        return piece.getColor() == turn;
    }
    protected void nextTurn()
    {
        checkWin();
        turn = !turn;
    }
    public boolean inCheck(boolean white)
    {
        if(white) {
            for(ChessPiece enemy : piecesB) {
                if(enemy.getMoveableLocations().contains(kingW.getLocation()))
                    return true;
            }
        }
        else { 
            for(ChessPiece enemy : piecesW) {
                if(enemy.getMoveableLocations().contains(kingB.getLocation()))
                    return true;
            }
        }
        return false;
    }
    private void checkWin()
    {
        if(turn) {
            for(ChessPiece piece : piecesB) {
                if(piece.getMoveableLocations().size() > getCheckLocations(piece).size())
                    return; // If a piece can make a legal move
            }
            if(inCheck(false))
                won = 1; // White win
            else
                won = 3; // Stalemate
        }
        else {
            for(ChessPiece piece : piecesW) {
                if(piece.getMoveableLocations().size() > getCheckLocations(piece).size())
                    return; // If a piece can make a legal move
            }
            if(inCheck(true))
                won = 2; // Black win
            else
                won = 3; // Stalemate
        }
    }
    public int getWin() { return won; }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}