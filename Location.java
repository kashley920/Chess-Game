public class Location
{
    private int row; 
    private int col; 

    public Location(int r, int c)
    {
        row = r;
        col = c;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    
    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;
        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }

    public String toString()
    {
        return "(" + getRow() + ", " + getCol() + ")";
    }
}
