//==============================================================
// Cell class: represents one position in the maze grid
//==============================================================
public class Cell {
    public int row;
    public int col;

    public Cell(int r, int c) {
        this.row = r;
        this.col = c;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell)) return false;
        Cell other = (Cell) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;  // simple and consistent hash for HashMap keys
    }
}
