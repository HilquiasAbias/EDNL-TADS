package shortestpath;

import java.util.ArrayList;
import java.util.List;

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
        // 
    }

    public void aEstrela() {
        // ...
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
}
