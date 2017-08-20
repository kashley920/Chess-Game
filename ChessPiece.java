import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
public abstract class ChessPiece
{
    private Location location;
    private boolean color; // White = true, Black = false
    private ChessBoard board;
    private int numMoves;
    protected int value;
    protected Image image;
    
    public ChessPiece(Location loc, boolean white)
    {
        location = loc;
        color = white;
        board = null;
        numMoves = 0;
    }
    
    public abstract ArrayList<Location> getMoveableLocations();
    public Location getLocation() { return location; }
    public boolean getColor() { return color; }
    public ChessBoard getBoard() { return board; }
    public int getValue() { return value; }
    public int getNumMoves() { return numMoves; }
    public Image getImage() { return image; }
    public void undo(int n) { numMoves -= n; }
    
    public void moveTo(Location newLocation)
    {
        if (board == null)
            throw new IllegalStateException("This piece is not on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");           
        board.remove(location);
        ChessPiece other = board.get(newLocation);
        if (other != null)
            other.removeFromBoard();
        location = newLocation;
        board.put(location, this);
        numMoves++;
    }
    public void putOnBoard(ChessBoard bd)
    {
        if (board != null)
            throw new IllegalStateException(
                    "This piece is already contained on a board.");
        if (bd.get(location) != null)
            throw new IllegalStateException(
                    "The board already contains a piece at location "
                            + location + ".");
        board = bd;
        board.put(location, this);
        board.add(this);
    }
    public void removeFromBoard()
    {
        if (board == null)
            throw new IllegalStateException(
                    "This piece is not contained on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");
        board.remove(location);
        board.remove(this);
        board = null;
    }
}