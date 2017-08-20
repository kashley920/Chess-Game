import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Pawn extends ChessPiece
{
    public Pawn(Location loc, boolean c)
    {
        super(loc, c);
        if(c) // White
            image = new ImageIcon("PawnW.gif").getImage();
        else // Black
            image = new ImageIcon("PawnB.gif").getImage();
        value = 1;
    }
    public ArrayList<Location> getMoveableLocations() 
    {
        ArrayList<Location> moveableLocs = new ArrayList<Location>(), 
                            finalMoveLocs = new ArrayList<Location>();
        Location captureLoc1, captureLoc2;
        if(getBoard() == null)
            return moveableLocs;
        int row = getLocation().getRow();
        int col = getLocation().getCol();
        if(getColor()) {
            moveableLocs.add(new Location(row - 1, col));
            if(getNumMoves() == 0 && getBoard().get(moveableLocs.get(moveableLocs.size() - 1)) == null)
                moveableLocs.add(new Location(row - 2, col));
            captureLoc1 = new Location(row - 1, col - 1);
            captureLoc2 = new Location(row - 1, col + 1);
        }
        else {
            moveableLocs.add(new Location(row + 1, col));
            if(getNumMoves() == 0 && getBoard().get(moveableLocs.get(moveableLocs.size() - 1)) == null)
                moveableLocs.add(new Location(row + 2, col));
            captureLoc1 = new Location(row + 1, col - 1);
            captureLoc2 = new Location(row + 1, col + 1);
        }
        for(Location loc : moveableLocs) {
            if(canMove(loc))
                finalMoveLocs.add(loc);
        }
        if(getBoard().get(captureLoc1) != null && getBoard().get(captureLoc1).getColor() != getColor())
            finalMoveLocs.add(captureLoc1);
        if(getBoard().get(captureLoc2) != null && getBoard().get(captureLoc2).getColor() != getColor())
            finalMoveLocs.add(captureLoc2);
        return finalMoveLocs;
    } // I know this does not include En passant, I just figured it was too rare of a case to bother
    private boolean canMove(Location loc)
    {
        return getBoard().isValid(loc) && getBoard().get(loc) == null; 
    }
}