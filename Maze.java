import java.util.*;

public class Maze {

    private int rows, cols;
    private Cell start, end;

    private Map<Cell, List<Cell>> graph = new HashMap<>();
    private Random rand = new Random();

    private Cell[][] grid;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        createGrid();
        createGraph();

        this.start = grid[0][0];
        this.end = grid[rows - 1][cols - 1];

        generatePrim();
    }

    public Map<Cell, List<Cell>> getGraph() {
        return graph;
    }
    public Cell getStart() {
        return start;
    }
    public Cell getEnd() {
        return end;
    }

    private void createGrid() {
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
    }

    private void createGraph() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                graph.put(grid[r][c], new ArrayList<>());
            }
        }
    }

    private List<Cell> neighbors(Cell c) {
        List<Cell> list = new ArrayList<>();
        int r = c.row, cc = c.col;

        if (r > 0) list.add(grid[r - 1][cc]);
        if (r < rows - 1) list.add(grid[r + 1][cc]);
        if (cc > 0) list.add(grid[r][cc - 1]);
        if (cc < cols - 1) list.add(grid[r][cc + 1]);

        return list;
    }

    private void addEdge(Cell a, Cell b) {
        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    private boolean connected(Cell a, Cell b) {
        return graph.get(a).contains(b);
    }

    // Prim's algorithm
    private void generatePrim() {

        Set<Cell> inMaze = new HashSet<>();
        Set<Cell> frontier = new HashSet<>();

        Cell first = grid[rand.nextInt(rows)][rand.nextInt(cols)];
        inMaze.add(first);
        frontier.addAll(neighbors(first));

        while (!frontier.isEmpty()) {

            List<Cell> list = new ArrayList<>(frontier);
            Cell current = list.get(rand.nextInt(list.size()));

            List<Cell> inSetNeighbors = new ArrayList<>();
            for (Cell n : neighbors(current)) {
                if (inMaze.contains(n)) inSetNeighbors.add(n);
            }

            Cell chosen = inSetNeighbors.get(rand.nextInt(inSetNeighbors.size()));
            addEdge(current, chosen);

            inMaze.add(current);
            frontier.remove(current);

            for (Cell n : neighbors(current)) {
                if (!inMaze.contains(n)) frontier.add(n);
            }
        }
    }

    //======================================================
    // Print maze with correct horizontal and vertical walls
    //======================================================
    public void printMaze() {

        // top border
        for (int c = 0; c < cols; c++) System.out.print("+---");
        System.out.println("+");

        for (int r = 0; r < rows; r++) {

            // print cell row with correct vertical walls
            for (int c = 0; c < cols; c++) {

                Cell cur = grid[r][c];

                // left wall or open space
                if (c == 0) {
                    System.out.print("| ");
                } else {
                    if (connected(cur, grid[r][c - 1])) System.out.print("  ");
                    else System.out.print("| ");
                }

                // cell contents
                if (cur.equals(start)) System.out.print("S ");
                else if (cur.equals(end)) System.out.print("E ");
                else System.out.print("  ");
            }

            // right border
            System.out.println("|");

            // print bottom walls for each cell
            for (int c = 0; c < cols; c++) {

                Cell cur = grid[r][c];
                System.out.print("+");

                if (r < rows - 1 && connected(cur, grid[r + 1][c])) {
                    System.out.print("   ");
                } else {
                    System.out.print("---");
                }
            }

            System.out.println("+");
        }
    }

    //======================================================
    // Print maze with path overlay
    //======================================================
    public void printSolvedMaze(List<Cell> path) {

        Set<Cell> pathSet = new HashSet<>(path);

        for (int c = 0; c < cols; c++) System.out.print("+---");
        System.out.println("+");

        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {
                Cell cur = grid[r][c];

                if (c == 0) {
                    System.out.print("| ");
                } else {
                    if (connected(cur, grid[r][c - 1])) System.out.print("  ");
                    else System.out.print("| ");
                }

                if (cur.equals(start)) System.out.print("S ");
                else if (cur.equals(end)) System.out.print("E ");
                else if (pathSet.contains(cur)) System.out.print("* ");
                else System.out.print("  ");
            }

            System.out.println("|");

            for (int c = 0; c < cols; c++) {
                Cell cur = grid[r][c];
                System.out.print("+");
                if (r < rows - 1 && connected(cur, grid[r + 1][c])) System.out.print("   ");
                else System.out.print("---");
            }

            System.out.println("+");
        }
    }
}
