import java.util.*;

//==============================================================
// MazeSolver class: DFS traversal to find a path from start to end
//==============================================================
public class MazeSolver {

    private Map<Cell, List<Cell>> graph;
    private Cell start, end;

    public MazeSolver(Map<Cell, List<Cell>> graph, Cell start, Cell end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
    }

    //==============================================================
    // Solve maze using DFS and parent backtracking
    //==============================================================
    public List<Cell> solve() {

        Map<Cell, Cell> parent = new HashMap<>();
        Set<Cell> visited = new HashSet<>();

        Stack<Cell> stack = new Stack<>();
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Cell cur = stack.pop();
            if (cur.equals(end)) break;

            for (Cell nb : graph.get(cur)) {
                if (!visited.contains(nb)) {
                    visited.add(nb);
                    parent.put(nb, cur);
                    stack.push(nb);
                }
            }
        }

        List<Cell> path = new ArrayList<>();

        // no path found case
        if (!parent.containsKey(end) && !end.equals(start)) return path;

        // reconstruct path back using parent map
        for (Cell c = end; c != null; c = parent.get(c)) {
            path.add(c);
            if (c.equals(start)) break;
        }

        Collections.reverse(path);
        return path;
    }
}
