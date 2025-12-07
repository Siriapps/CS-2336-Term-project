import java.util.*;

/*==============================================================
// Main class: handles input and output
  ============================================================== */
public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the maze dimensions: ");
        int r = input.nextInt();
        int c = input.nextInt();

        Maze maze = new Maze(r, c);

        System.out.println("\nGenerated Maze:\n");
        maze.printMaze();

        MazeSolver solver = new MazeSolver(maze.getGraph(), maze.getStart(), maze.getEnd());
        List<Cell> path = solver.solve();

        System.out.println("\nMaze Solver:\n");
        maze.printSolvedMaze(path);
    }
}
