import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Queen extends ChessPiece
{
    public Queen(Location loc, boolean c)
    {
        super(loc, c);
        if(c) // White
            image = new ImageIcon("QueenW.gif").getImage();
        else // Black
            image = new ImageIcon("QueenB.gif").getImage();
        value = 9;
    }
    public ArrayList<Location> getMoveableLocations()
    {
        ArrayList<Location> moveableLocs = new ArrayList<Location>();
        if(getBoard() == null)
            return moveableLocs;
        int row = getLocation().getRow();
        int col = getLocation().getCol();
        Location loc = new Location(row + 1, col + 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row + i, col + i);
        }
        loc = new Location(row + 1, col - 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row + i, col - i);
        }
        loc = new Location(row - 1, col - 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row - i, col - i);
        }
        loc = new Location(row - 1, col + 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row - i, col + i);
        }
        loc = new Location(row + 1, col);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row + i, col);
        }
        loc = new Location(row, col + 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row, col + i);
        }
        loc = new Location(row - 1, col);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row - i, col);
        }
        loc = new Location(row, col - 1);
        for(int i = 2; getBoard().isValid(loc) && canMove(loc); i++) {
            moveableLocs.add(loc);
            if(getBoard().get(loc) != null)
                break;
            loc = new Location(row, col - i);
        }
        return moveableLocs;
    }
    private boolean canMove(Location loc)
    {
        return getBoard().isValid(loc) && 
              (getBoard().get(loc) == null || getBoard().get(loc).getColor() != getColor()); 
    }
}
