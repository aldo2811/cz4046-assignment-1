public class GridWorld {

    private State[][] grid;
    private int length;
    private int width;

    public void createBasicGrid() {
        // 6x6 maze
        this.length = 6;
        this.width = 6;

        // Add outer wall layer to make code simpler
        this.grid = new State[this.length + 2][this.width + 2];
        createOuterWall(this.length, this.width);

        // Assign maze environment
        this.grid[1][1] = new State(Square.GREEN);
        this.grid[1][2] = new State(Square.WALL);
        this.grid[1][3] = new State(Square.GREEN);
        this.grid[1][4] = new State(Square.WHITE);
        this.grid[1][5] = new State(Square.WHITE);
        this.grid[1][6] = new State(Square.GREEN);
        this.grid[2][1] = new State(Square.WHITE);
        this.grid[2][2] = new State(Square.RED);
        this.grid[2][3] = new State(Square.WHITE);
        this.grid[2][4] = new State(Square.GREEN);
        this.grid[2][5] = new State(Square.WALL);
        this.grid[2][6] = new State(Square.RED);
        this.grid[3][1] = new State(Square.WHITE);
        this.grid[3][2] = new State(Square.WHITE);
        this.grid[3][3] = new State(Square.RED);
        this.grid[3][4] = new State(Square.WHITE);
        this.grid[3][5] = new State(Square.GREEN);
        this.grid[3][6] = new State(Square.WHITE);
        this.grid[4][1] = new State(Square.WHITE);
        this.grid[4][2] = new State(Square.WHITE);
        this.grid[4][3] = new State(Square.WHITE);
        this.grid[4][4] = new State(Square.RED);
        this.grid[4][5] = new State(Square.WHITE);
        this.grid[4][6] = new State(Square.GREEN);
        this.grid[5][1] = new State(Square.WHITE);
        this.grid[5][2] = new State(Square.WALL);
        this.grid[5][3] = new State(Square.WALL);
        this.grid[5][4] = new State(Square.WALL);
        this.grid[5][5] = new State(Square.RED);
        this.grid[5][6] = new State(Square.WHITE);
        this.grid[6][1] = new State(Square.WHITE);
        this.grid[6][2] = new State(Square.WHITE);
        this.grid[6][3] = new State(Square.WHITE);
        this.grid[6][4] = new State(Square.WHITE);
        this.grid[6][5] = new State(Square.WHITE);
        this.grid[6][6] = new State(Square.WHITE);
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

    public State[][] getGrid() {
        return grid;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
