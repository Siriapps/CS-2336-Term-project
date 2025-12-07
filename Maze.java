import java.util.*;

/* This class generates a maze using Prim's algorithm and prints it as ASCII art.
The maze is represented as an adjacency list mapping each Cell to its connected neighbors.
A Cell[][] grid
 
 * Major responsibilities:
 1. Create a grid of Cell objects.
 2. Build graph structure using adjacency lists.
 3. Randomize a spanning tree using Prim's algorithm.
 4. Print the maze with correct horizontal and vertical walls.
 5. Print a solved maze with the DFS path overlay. */
public class Maze {

    private int rows, cols;
    private Cell start, end;

    // Graph representation using adjacency lists
    private Map<Cell, List<Cell>> graph = new HashMap<>();
    private Random rand = new Random();

    private Cell[][] grid;

    //Builds the grid, adjacency list and generates randomised maze.
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

    /* Builds a 2D array of Cell objects.
    Every cell is created once and reused throughout the entire program. */
    private void createGrid() {
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
    }

    /* Initializes the adjacency list for every Cell in the grid.
    Each cell begins with an empty neighbor list. */
    private void createGraph() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                graph.put(grid[r][c], new ArrayList<>());
            }
        }
    }

    // Returns all valid neighboring cells (up, down, left, right) from the grid using their actual object references. 
    private List<Cell> neighbors(Cell c) {
        List<Cell> list = new ArrayList<>();
        int r = c.row, cc = c.col;

        if (r > 0) list.add(grid[r - 1][cc]);
        if (r < rows - 1) list.add(grid[r + 1][cc]);
        if (cc > 0) list.add(grid[r][cc - 1]);
        if (cc < cols - 1) list.add(grid[r][cc + 1]);

        return list;
    }

    // Biderectional edges are added
    private void addEdge(Cell a, Cell b) {
        graph.get(a).add(b); 
        graph.get(b).add(a);
    }

    // Returns true if the adjacency list contains an edge between the two cells.
    private boolean connected(Cell a, Cell b) {
        return graph.get(a).contains(b);
    }

    // Prim's algorithm is used to generae the maze
    /* Algorithm steps:
     1. Pick a random starting cell inside the maze.
     2. Mark it as part of the maze.
     3. Add its neighbors to a frontier set.
     4. Repeatedly choose a random frontier cell, connect it to a cell already
        in the maze, and add its neighbors to the frontier.
     
     The final result is a spanning tree that guarantees a perfect maze with no loops and exactly one path between any two cells. */
    private void generatePrim() {

        Set<Cell> inMaze = new HashSet<>();
        Set<Cell> frontier = new HashSet<>();

        // Start with a random cell
        Cell first = grid[rand.nextInt(rows)][rand.nextInt(cols)];
        inMaze.add(first);
        frontier.addAll(neighbors(first)); // add all neighbors to a frontier set

        // repeated steps until frontier is empty
        while (!frontier.isEmpty()) {

            List<Cell> list = new ArrayList<>(frontier);
            Cell current = list.get(rand.nextInt(list.size())); // Choose a random cell from the frontier.

            List<Cell> inSetNeighbors = new ArrayList<>();
            for (Cell n : neighbors(current)) {
                if (inMaze.contains(n)) inSetNeighbors.add(n);
            }

            Cell chosen = inSetNeighbors.get(rand.nextInt(inSetNeighbors.size()));
            addEdge(current, chosen); // Connect it to one of its neighboring cells already in the maze.

            inMaze.add(current); // Mark it as part of the maze and add its neighbors to the frontier.
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
    // Print maze with solved path
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
