import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Bishop extends ChessPiece
{
    public Bishop(Location loc, boolean c)
    {
        super(loc, c);
        if(c) // White
            image = new ImageIcon("BishopW.gif").getImage();
        else // Black
            image = new ImageIcon("BishopB.gif").getImage();
        value = 3;
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
        return moveableLocs;
    }
    private boolean canMove(Location loc)
    {
        return getBoard().isValid(loc) && 
              (getBoard().get(loc) == null || getBoard().get(loc).getColor() != getColor()); 
    }
}
