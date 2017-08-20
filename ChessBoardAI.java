import java.util.ArrayList;
public class ChessBoardAI extends ChessBoard
{
    private ChessPiece piece;
    private Location loc;
    
    protected void nextTurn()
    {
        super.nextTurn();
        if(getWin() == 0) {
            calculateMove();
            piece.moveTo(loc);
            super.nextTurn();
        }
        repaint();
    }
    private void calculateMove()
    {
        piece = null;
        loc = null;
        ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
        ArrayList<Location> locations = new ArrayList<Location>();
        for(ChessPiece p : piecesB) { // First find all moves to put white in check
            ArrayList<Location> moveLocs = p.getMoveableLocations(),
                                checkLocs = getCheckLocations(p);
            Location oldLoc = p.getLocation();
            for(Location moveLoc : moveLocs) {
                if(p instanceof King) break;
                ChessPiece temp = get(moveLoc);
                p.moveTo(moveLoc);
                if(inCheck(true) && !checkLocs.contains(moveLoc)) {
                    pieces.add(p);
                    locations.add(moveLoc);
                }
                p.moveTo(oldLoc);
                p.undo(2);
                if(temp != null)
                    temp.putOnBoard(this);
            }
        }
        if(pieces.isEmpty()) { // Add all moves/pieces, to be chosen randomly
            for(ChessPiece p : piecesB) {
                ArrayList<Location> moveLocs = p.getMoveableLocations(),
                                    checkLocs = getCheckLocations(p);
                for(Location moveLoc : moveLocs) {
                    if(!checkLocs.contains(moveLoc)) {
                        pieces.add(p);
                        locations.add(moveLoc);
                    }
                }
            }
        }
        int n = (int)(Math.random() * pieces.size());
        piece = pieces.get(n);
        loc = locations.get(n);
    }
}
