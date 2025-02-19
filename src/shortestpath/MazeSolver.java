package shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

public class MazeSolver {
    private int[][] matrix;
    private Cell[][] grid;
    private Cell startCell;
    private List<Cell> exitCells;

    public MazeSolver(int[][] matrix) {
        this.matrix = matrix;
        this.exitCells = new ArrayList<>();
        validateMatrix();
        buildGrid();
    }

    private void validateMatrix() {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matriz inválida.");
        }
    }

    private void buildGrid() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        grid = new Cell[rows][cols];

        // Primeira passagem: cria as células e define tipo e custo
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cellType = matrix[i][j];
                Cell cell = new Cell();
                cell.setType(cellType);
                cell.setCost(Integer.MAX_VALUE);
                cell.setRow(i); 
                cell.setCol(j);

                grid[i][j] = cell;

                if (cellType == 2) {
                    if (startCell != null) {
                        throw new IllegalArgumentException("Múltiplas células de início encontradas.");
                    }
                    startCell = cell;
                } else if (cellType == 3) {
                    exitCells.add(cell);
                }
            }
        }

        if (startCell == null) {
            throw new IllegalArgumentException("Célula de início não encontrada.");
        }

        if (exitCells.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma saída encontrada.");
        }

        // Segunda passagem: estabelece as ligações entre as células
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = grid[i][j];
                
                if (currentCell.getType() == 1) {
                    continue; // Paredes não têm ligações
                }
                
                // Ligação acima
                if (i > 0 && grid[i - 1][j].getType() != 1) {
                    currentCell.setUp(grid[i - 1][j]);
                }

                // Ligação abaixo
                if (i < rows - 1 && grid[i + 1][j].getType() != 1) {
                    currentCell.setDown(grid[i + 1][j]);
                }
                
                // Ligação à esquerda
                if (j > 0 && grid[i][j - 1].getType() != 1) {
                    currentCell.setLeft(grid[i][j - 1]);
                }
                
                // Ligação à direita
                if (j < cols - 1 && grid[i][j + 1].getType() != 1) {
                    currentCell.setRight(grid[i][j + 1]);
                }
            }
        }
    }

    public void dijkstra() {
        int costBase = 1; // Custo base de movimento

        PriorityQueue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(Cell::getCost));
        queue.add(startCell);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            // Processa vizinhos
            for (Cell neighbor : getEmptyPaths(current)) {
                int newCost = current.getCost() + costBase;
                if (newCost < neighbor.getCost()) {
                    neighbor.setCost(newCost);
                    neighbor.setPrevious(current);
                    queue.add(neighbor);
                }
            }
        }

        // Encontra e exibe o caminho
        Optional<Cell> exit = getLowestCostExit();
        if (exit.isPresent()) {
            List<Cell> path = reconstructPath(exit.get());
            displayPath(path, "Dijkstra");
        } else {
            System.out.println("Nenhum caminho até a saída encontrado!");
        }
    }

    private void resetCellsForAlgorithm() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.getType() == 2) { // Célula inicial
                    cell.setCost(0);
                } else if (cell.getType() != 1) { // Células não parede
                    cell.setCost(Integer.MAX_VALUE);
                }
                cell.setPrevious(null);
            }
        }
    }

    private List<Cell> getEmptyPaths(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        if (cell.getUp() != null && cell.getUp().getType() != 1)
            neighbors.add(cell.getUp());
        if (cell.getRight() != null && cell.getRight().getType() != 1)
            neighbors.add(cell.getRight());
        if (cell.getDown() != null && cell.getDown().getType() != 1)
            neighbors.add(cell.getDown());
        if (cell.getLeft() != null && cell.getLeft().getType() != 1)
            neighbors.add(cell.getLeft());
        return neighbors;
    }

    private Optional<Cell> getLowestCostExit() {
        return exitCells.stream()
            .filter(exit -> exit.getCost() != Integer.MAX_VALUE)
            .min(Comparator.comparingInt(Cell::getCost));
    }

    private List<Cell> reconstructPath(Cell exit) {
        List<Cell> path = new ArrayList<>();
        Cell current = exit;
        while (current != null) {
            path.add(current);
            current = current.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }

    public void aEstrela() {
        PriorityQueue<Cell> openList = new PriorityQueue<>(Comparator.comparingDouble(this::calculateF));
        Set<Cell> closedList = new HashSet<>();
        Cell bestExit = null;
        
        startCell.setCost(0);
        openList.add(startCell);

        while (!openList.isEmpty()) {
            Cell current = openList.poll();
            
            // Verifica se é uma saída e atualiza a melhor saída
            if (exitCells.contains(current)) {
                if (bestExit == null || current.getCost() < bestExit.getCost()) {
                    bestExit = current;
                }
            }
            
            closedList.add(current);
            
            // Processa vizinhos
            for (Cell neighbor : getEmptyPaths(current)) {
                if (closedList.contains(neighbor)) 
                    continue;
                
                int tentativeG = current.getCost() + 1;
                
                if (tentativeG < neighbor.getCost()) {
                    neighbor.setPrevious(current);
                    neighbor.setCost(tentativeG);
                    
                    // Atualiza a fila de prioridade
                    if (openList.contains(neighbor)) {
                        openList.remove(neighbor);
                    }
                    openList.add(neighbor);
                }
            }
        }
        
        if (bestExit != null) {
            List<Cell> path = reconstructPath(bestExit);
            displayPath(path, "A*");
        } else {
            System.out.println("Nenhum caminho encontrado por A*!");
        }
    }

    private double calculateF(Cell cell) {
        return cell.getCost() + calculateHeuristic(cell);
    }

    private double calculateHeuristic(Cell cell) {
        // Heurística: distância euclidiana para a saída MAIS PRÓXIMA
        return exitCells.stream()
            .mapToDouble(exit -> Math.sqrt(
                Math.pow(cell.getRow() - exit.getRow(), 2) + 
                Math.pow(cell.getCol() - exit.getCol(), 2)))
            .min()
            .orElse(Double.MAX_VALUE);
    }

    public void test() {
        resetCellsForAlgorithm();

        long startTime = System.nanoTime();
        dijkstra();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Tempo total do algoritmo dijkstra(): " + duration + " nanosegundos");

        resetCellsForAlgorithm();

        startTime = System.nanoTime();
        aEstrela();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Tempo total do algoritmo aEstrela(): " + duration + " nanosegundos");
    }

    public Cell getStartCell() {
        return startCell;
    }

    public List<Cell> getExitCells() {
        return exitCells;
    }

    public void displayMaze() {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void displayConnections() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.getType() == 1)
                    System.out.print(cell.getType() + " ");
                else if (cell.getType() == 2) 
                    System.out.print("E ");
                else if (cell.getType() == 3)
                    System.out.print("S ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }

    private void displayPath(List<Cell> path, String algorithm) {
        System.out.println("\nCaminho encontrado (" + algorithm + "):");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Cell cell = grid[i][j];
                if (cell.getType() == 1) {
                    System.out.print("1 ");
                } else if (cell == startCell) {
                    System.out.print("E ");
                } else if (exitCells.contains(cell)) {
                    System.out.print(path.contains(cell) ? "* " : "S ");
                } else {
                    System.out.print(path.contains(cell) ? "* " : "  ");
                }
            }
            System.out.println();
        }
    }
}
