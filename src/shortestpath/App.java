package shortestpath;

public class App {
    public static void main(String[] args) {
        int[][] example = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,2,0,0,0,1,0,1,0,1},
            {1,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,1,1,0,3},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,0,1,1},
            {3,1,0,0,1,0,0,0,0,1},
            {1,1,0,1,0,0,1,1,1,1},
            {1,0,0,0,0,0,0,3,0,1},
            {1,1,1,1,1,1,1,1,1,1}
        };

        // Maze maze = new Maze(example);
        // maze.printMaze();

        // Cell startPoint = maze.getStartPoint();
        // System.out.println(startPoint);

        MazeSolver solver = new MazeSolver(example);
        
        System.out.println("Labirinto Inicial:");
        solver.displayMaze();
        
        System.out.println("\nConexões entre as células:");
        solver.displayConnections();
    }
}
