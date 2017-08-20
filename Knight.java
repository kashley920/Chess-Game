import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Knight extends ChessPiece
{
    public Knight(Location loc, boolean c)
    {
        super(loc, c);
        if(c) // White
            image = new ImageIcon("KnightW.gif").getImage();
        else // Black
            image = new ImageIcon("KnightB.gif").getImage();
        value = 3;
    }
    public ArrayList<Location> getMoveableLocations()
    {
        ArrayList<Location> moveableLocs = new ArrayList<Location>(), 
                            finalMoveLocs = new ArrayList<Location>();
        if(getBoard() == null)
            return moveableLocs;
        int row = getLocation().getRow();
        int col = getLocation().getCol();
        moveableLocs.add(new Location(row - 2, col + 1));
        moveableLocs.add(new Location(row - 2, col - 1));
        moveableLocs.add(new Location(row + 2, col + 1));
        moveableLocs.add(new Location(row + 2, col - 1));
        moveableLocs.add(new Location(row - 1, col + 2));
        moveableLocs.add(new Location(row - 1, col - 2));
        moveableLocs.add(new Location(row + 1, col + 2));
        moveableLocs.add(new Location(row + 1, col - 2));
        for(Location loc : moveableLocs) {
            if(canMove(loc))
                finalMoveLocs.add(loc);
        }
        return finalMoveLocs;
    }
    private boolean canMove(Location loc)
    {
        return getBoard().isValid(loc) && 
              (getBoard().get(loc) == null || getBoard().get(loc).getColor() != getColor()); 
    }
}
