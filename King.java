import java.util.ArrayList;
import javax.swing.ImageIcon;
public class King extends ChessPiece
{
    public King(Location loc, boolean c)
    {
        super(loc, c);
        if(c) // White
            image = new ImageIcon("KingW.gif").getImage();
        else // Black
            image = new ImageIcon("KingB.gif").getImage();
        value = 0;
    }
    public ArrayList<Location> getMoveableLocations()
    {
        ArrayList<Location> moveableLocs = new ArrayList<Location>(), 
                            finalMoveLocs = new ArrayList<Location>();
        if(getBoard() == null)
            return moveableLocs;
        int row = getLocation().getRow();
        int col = getLocation().getCol();
        moveableLocs.add(new Location(row, col + 1));
        moveableLocs.add(new Location(row + 1, col));
        moveableLocs.add(new Location(row + 1, col + 1));
        moveableLocs.add(new Location(row, col - 1));
        moveableLocs.add(new Location(row - 1, col));
        moveableLocs.add(new Location(row - 1, col - 1));
        moveableLocs.add(new Location(row + 1, col - 1));
        moveableLocs.add(new Location(row - 1, col + 1));
        for(Location loc : moveableLocs) {
            if(canMove(loc))
                finalMoveLocs.add(loc);
        }
        if(canCastle(true)) // Left
              finalMoveLocs.add(new Location(getLocation().getRow(), 0));
        if(canCastle(false)) // Right
              finalMoveLocs.add(new Location(getLocation().getRow(), 7));
        return finalMoveLocs;
    }
    private boolean canMove(Location loc)
    {
        return getBoard().isValid(loc) && 
               (getBoard().get(loc) == null || getBoard().get(loc).getColor() != getColor()); 
    }
    private boolean canCastle(boolean side)
    {
        if(side) { // Left
            return getNumMoves() == 0 && 
                getBoard().get(getLocation().getRow(), 0) instanceof Rook && // Also checks null
                getBoard().get(getLocation().getRow(), 0).getNumMoves() == 0 &&
                getBoard().get(getLocation().getRow(), 1) == null &&
                getBoard().get(getLocation().getRow(), 2) == null &&
                getBoard().get(getLocation().getRow(), 3) == null;
        }
        else { // Right
            return getNumMoves() == 0 &&
                getBoard().get(getLocation().getRow(), 7) instanceof Rook && 
                getBoard().get(getLocation().getRow(), 7).getNumMoves() == 0 &&
                getBoard().get(getLocation().getRow(), 6) == null &&
                getBoard().get(getLocation().getRow(), 5) == null;
        }
    }
    public void moveTo(Location newLoc)
    {
        if(getNumMoves() == 0 && newLoc.getCol() == 0) { // Castling
            super.moveTo(new Location(getLocation().getRow(), 2));
            getBoard().get(newLoc).moveTo(new Location(getLocation().getRow(), 3));
        }
        else if(getNumMoves() == 0 && newLoc.getCol() == 7) {
            super.moveTo(new Location(getLocation().getRow(), 6));
            getBoard().get(newLoc).moveTo(new Location(getLocation().getRow(), 5));
        }
        else
            super.moveTo(newLoc);
    }
}
