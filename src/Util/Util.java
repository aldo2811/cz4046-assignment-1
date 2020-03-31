package Util;

import GridWorld.GridWorld;
import GridWorld.State;

/**
 * Utility class to help the main classes
 */
public class Util {
    /**
     * Displays the Gridworld map
     * @param gridWorld The Gridworld map
     */
    public static void printGrid(GridWorld gridWorld) {
        State[][] grid = gridWorld.getGrid();

        for (int i = 1; i < grid.length-1; i++) {
            for (int j = 1; j < grid[0].length-1; j++) {
                Square square = grid[i][j].getSquare();
                String s = "";

                switch (square) {
                    case GREEN:
                        s += "+1";
                        break;
                    case RED:
                        s += "-1";
                        break;
                    case WHITE:
                        s += "  ";
                        break;
                    case WALL:
                        s += "WALL";
                        break;
                }
                System.out.print("|");
                System.out.printf("%-4s", s);
            }
            System.out.println("|");
        }

        System.out.println();
    }

    /**
     * Print array of policies of the Gridworld
     * @param grid Array of states of the Gridworld
     */
    public static void printPolicies(State[][] grid) {
        System.out.println("Policies:");
        for (int a = 1; a < grid.length-1; a++) {
            for (int b = 1; b < grid[a].length - 1; b++) {
                System.out.printf("%-6s", grid[a][b].getPolicy());
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    /**
     * Print array of utilities of the Gridworld
     * @param grid Array of states of the Gridworld
     */
    public static void printUtilities(State[][] grid) {
        System.out.println("Utilities:");
        for (int a = 1; a < grid.length-1; a++) {
            for (int b = 1; b < grid[a].length - 1; b++) {
                if (grid[a][b].isWall()) System.out.printf("%-7s", "WALL");
                else System.out.printf("%-7.4f", grid[a][b].getUtility());
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    /**
     * Update utilities of each state in the grid given the utility array
     * @param grid Array of states in the Gridworld
     * @param utils Array of utilities
     */
    public static void updateUtility(State[][] grid, double[][] utils) {
        for (int a = 0; a < grid.length; a++) {
            for (int b = 0; b < grid[a].length; b++) {
                grid[a][b].setUtility(utils[a][b]);
            }
        }
    }

    /**
     * Find the maximum expected utility of moving to all 4 directions
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @return Util.Pair of maximum expected utility and policy of the MEU
     */
    public static Pair<Double, Policy> maxExpectedUtility(State[][] grid, int x, int y) {
        double upEU = countUpUtility(grid, x, y);
        double downEU = countDownUtility(grid, x, y);
        double leftEU = countLeftUtility(grid, x, y);
        double rightEU = countRightUtility(grid, x, y);

        double maxEU = upEU;
        Policy maxDir = Policy.UP;

        if (downEU > maxEU) {
            maxEU = downEU;
            maxDir = Policy.DOWN;
        }

        if (leftEU > maxEU) {
            maxEU = leftEU;
            maxDir = Policy.LEFT;
        }

        if (rightEU > maxEU) {
            maxEU = rightEU;
            maxDir = Policy.RIGHT;
        }

        return new Pair<>(maxEU, maxDir);
    }

    /**
     * Counts the expected utility of moving to the specified direction
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @param policy Direction of movement
     * @return Expected utility of moving to the specified direction
     */
    public static double countExpectedUtility(State[][] grid, int x, int y, Policy policy) {
        double EU;

        switch(policy) {
            case UP:
                EU = countUpUtility(grid, x, y);
                break;
            case DOWN:
                EU = countDownUtility(grid, x, y);
                break;
            case LEFT:
                EU = countLeftUtility(grid, x, y);
                break;
            case RIGHT:
                EU = countRightUtility(grid, x, y);
                break;
            default:
                EU = 0;
                break;
        }

        return EU;
    }

    /**
     * Counts the expected utility of moving upwards
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @return Expected utility of moving upwards
     */
    public static double countUpUtility(State[][] grid, int x, int y) {
        State self = grid[x][y];
        State up = grid[x-1][y];
        State left = grid[x][y-1];
        State right = grid[x][y+1];

        // 0.8 chance to go up, 0.1 to go left, 0.1 to go right
        // Stays at the same position if hit the wall
        double selfUtility = self.getUtility();
        double upUtility = up.isWall() ? selfUtility : up.getUtility();
        double leftUtility = left.isWall() ? selfUtility : left.getUtility();
        double rightUtility = right.isWall() ? selfUtility : right.getUtility();

        return 0.8 * upUtility + 0.1 * leftUtility + 0.1 * rightUtility;
    }

    /**
     * Counts the expected utility of moving downwards
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @return Expected utility of moving downwards
     */
    public static double countDownUtility(State[][] grid, int x, int y) {
        State self = grid[x][y];
        State down = grid[x+1][y];
        State right = grid[x][y+1];
        State left = grid[x][y-1];

        // 0.8 chance to go down, 0.1 to go right, 0.1 to go left
        // Stays at the same position if hit the wall
        double selfUtility = self.getUtility();
        double downUtility = down.isWall() ? selfUtility : down.getUtility();
        double rightUtility = right.isWall() ? selfUtility : right.getUtility();
        double leftUtility = left.isWall() ? selfUtility : left.getUtility();

        return 0.8 * downUtility + 0.1 * rightUtility + 0.1 * leftUtility;
    }

    /**
     * Counts the expected utility of moving left
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @return Expected utility of moving left
     */
    public static double countLeftUtility(State[][] grid, int x, int y) {
        State self = grid[x][y];
        State left = grid[x][y-1];
        State down = grid[x+1][y];
        State up = grid[x-1][y];

        // 0.8 chance to go left, 0.1 to go down, 0.1 to go up
        // Stays at the same position if hit the wall
        double selfUtility = self.getUtility();
        double leftUtility = left.isWall() ? selfUtility : left.getUtility();
        double downUtility = down.isWall() ? selfUtility : down.getUtility();
        double upUtility = up.isWall() ? selfUtility : up.getUtility();

        return 0.8 * leftUtility + 0.1 * downUtility + 0.1 * upUtility;
    }

    /**
     * Counts the expected utility of moving right
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @return Expected utility of moving right
     */
    public static double countRightUtility(State[][] grid, int x, int y) {
        State self = grid[x][y];
        State right = grid[x][y+1];
        State up = grid[x-1][y];
        State down = grid[x+1][y];

        // 0.8 chance to go right, 0.1 to go up, 0.1 to go down
        // Stays at the same position if hit the wall
        double selfUtility = self.getUtility();
        double rightUtility = right.isWall() ? selfUtility : right.getUtility();
        double upUtility = up.isWall() ? selfUtility : up.getUtility();
        double downUtility = down.isWall() ? selfUtility : down.getUtility();

        return 0.8 * rightUtility + 0.1 * upUtility + 0.1 * downUtility;
    }
}
