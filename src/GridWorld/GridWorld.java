package GridWorld;

import Util.*;

/**
 * Class representing the GridWorld
 */
public class GridWorld {

    private State[][] grid;
    private int length;
    private int width;

    /**
     * Creates a GridWorld object with the specified parameters
     * @param length Length of maze
     * @param width Width of maze
     * @param greenSquares String containing coordinates of green squares
     * @param redSquares String containing coordinates of red squares
     * @param wallSquares String containing coordinates of wall squares
     */
    public GridWorld(int length, int width, String greenSquares, String redSquares, String wallSquares) {
        // Length and width of maze without outer wall layer
        this.length = length;
        this.width = width;
        this.grid = new State[this.length+2][this.length+2];

        // Create outer wall layer to make code simpler
        createOuterWall(this.length, this.width);

        // Create grid of the GridWorld
        createGrid(greenSquares, redSquares, wallSquares);
    }

    /**
     * Creates the grid of the GridWorld
     * @param greenSquares String containing coordinates of green squares
     * @param redSquares String containing coordinates of red squares
     * @param wallSquares String containing coordinates of wall squares
     */
    public void createGrid(String greenSquares, String redSquares, String wallSquares) {
        String[] greens = greenSquares.split(" ");
        String[] reds = redSquares.split(" ");
        String[] walls = wallSquares.split(" ");

        // Set green, red and wall squares
        setSquare(greens, Square.GREEN);
        setSquare(reds, Square.RED);
        setSquare(walls, Square.WALL);

        // Set other squares as white squares
        for (int i = 1; i <= this.length; i++) {
            for (int j = 1; j <= this.width; j++) {
                if (this.grid[i][j] != null) continue;
                this.grid[i][j] = new State(Square.WHITE);
            }
        }

    }

    /**
     * Sets the type of square (Green, Red or Wall) to squares with the specified position
     * @param posArray Array of position of squares
     * @param square Type of square
     */
    public void setSquare(String[] posArray, Square square) {
        for (var pos : posArray) {
            String[] xy = pos.split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);

            // +1 for x and y as there is an outer wall layer
            this.grid[x+1][y+1] = new State(square);
        }
    }

    /**
     * Creates outer layer of wall, to make the code simpler
     * @param length Length of content of maze
     * @param width Width of content of maze
     */
    public void createOuterWall(int length, int width) {
        for (int i = 0; i < length + 2; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (i == 0 || i == length + 1 || j == 0 || j == width + 1) {
                    this.grid[i][j] = new State(Square.WALL);
                }
            }
        }
    }

    /**
     * Getter for grid
     * @return grid (array of states) of the GridWorld object
     */
    public State[][] getGrid() {
        return grid;
    }

    /**
     * Getter for length of maze
     * @return Length of maze
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter for width of maze
     * @return Width of maze
     */
    public int getWidth() {
        return width;
    }
}
