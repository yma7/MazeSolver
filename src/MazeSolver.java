// MazeSolver by Yuanye Ma

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution()
    {
        // Initializes an ArrayList and Stack
        ArrayList<MazeCell> sols = new ArrayList<>();
        Stack <MazeCell> solution = new Stack<>();
        MazeCell cell = maze.getEndCell();
        // Iterates through the Maze by traversing through each cell's parent cell
        while (cell != null)
        {
            solution.push(cell);
            cell = cell.getParent();
        }
        // Copies the elements in the stack into a ArrayList
        while (!solution.isEmpty())
        {
            sols.add(solution.pop());
        }
        return sols;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS()
    {
        Stack <MazeCell> cells = new Stack<>();
        // Gets the starting cell
        cells.push(maze.getStartCell());
        // Performs a DFS search until cells is empty or the Maze has been solved
        while (!cells.isEmpty())
        {
            // Pops the starting element in the stack and sets it's explored to true
            MazeCell currentCell = cells.pop();
            currentCell.setExplored(true);
            if (currentCell == maze.getEndCell())
            {
                break;
            }
            // Finds the VALID neighbor cells and pushes each one of them onto the stack
            for (MazeCell neighbor: exploreCell(currentCell))
            {
                neighbor.setParent(currentCell);
                cells.push(neighbor);
            }

        }
        // Returns the solution
        return getSolution();
    }

    public ArrayList<MazeCell> exploreCell(MazeCell cell)
    {
        ArrayList <MazeCell> neighbors = new ArrayList<>();
        // Gets rows and cols
        int row = cell.getRow();
        int col = cell.getCol();
        // Checks to see if it is a valid cell in the order of N E S W and adds it to neighbors
        if (maze.isValidCell(row - 1, col))
        {
            neighbors.add(maze.getCell(row - 1, col));
        }
        if (maze.isValidCell(row, col + 1))
        {
            neighbors.add(maze.getCell(row, col + 1));
        }
        if (maze.isValidCell(row + 1, col))
        {
            neighbors.add(maze.getCell(row + 1, col));
        }
        if (maze.isValidCell(row, col - 1))
        {
            neighbors.add(maze.getCell(row, col - 1) );
        }

        return neighbors;
    }


    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */

    public ArrayList<MazeCell> solveMazeBFS() {
        // Same logic and code as DFS but with a Queue instead of a Stack
        Queue <MazeCell> cells = new LinkedList<>();
        cells.add(maze.getStartCell());
        while (!cells.isEmpty())
        {
            // Gets the first element of the Queue and sets it to explored
            MazeCell currentCell = cells.remove();
            currentCell.setExplored(true);
            // Checks to see if Maze has been solved
            if (currentCell == maze.getEndCell())
            {
                break;
            }
            // Iterates through the neighbors of the current cell
            for (MazeCell neighbor: exploreCell(currentCell))
            {
                // Sets parent to current cell and pushes it onto the Queue
                neighbor.setParent(currentCell);
                cells.add(neighbor);
            }
        }
        return getSolution();
    }

    public static void main(String[] args) {

        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);


        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);

    }
}
