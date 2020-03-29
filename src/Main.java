public class Main {
    public static void main(String[] args) {
        // Value Iteration for the basic maze environment (Part I)
        GridWorld gridWorld = new GridWorld();
        gridWorld.createBasicGrid();
        printGrid(gridWorld);

        State[][] valueIterationResult = valueIteration(gridWorld, 0.99);
        // Print out utilities of each state
        for (int a = 1; a <= gridWorld.getLength(); a++) {
            for (int b = 1; b <= gridWorld.getWidth(); b++) {
                System.out.print(valueIterationResult[a][b].getUtility());
                System.out.print(" | ");
            }
            System.out.print("\n");
        }

        // Policy Iteration for the basic maze environment (Part I)
        GridWorld gridWorld2 = new GridWorld();
        gridWorld2.createBasicGrid();
        State[][] policyIterationResult = policyIteration(gridWorld2);

        // Print out optimal policies of each state
        for (int a = 1; a <= gridWorld2.getLength(); a++) {
            for (int b = 1; b <= gridWorld2.getWidth(); b++) {
                System.out.print(policyIterationResult[a][b].getDirection());
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Displays the GridWorld map
     * @param gridWorld The GridWorld map
     */
    public static void printGrid(GridWorld gridWorld) {
        State[][] grid = gridWorld.getGrid();

        for (int i = 1; i < grid.length-1; i++) {
            for (int j = 1; j < grid[0].length-1; j++) {
                Square square = grid[i][j].getSquare();
                String s = "";

                switch (square) {
                    case GREEN:
                        s += "G";
                        break;
                    case RED:
                        s += "R";
                        break;
                    case WHITE:
                        s += " ";
                        break;
                    case WALL:
                        s += "X";
                        break;
                }
                System.out.print("|");
                System.out.print(s);
            }
            System.out.println("|");
        }
    }

    /**
     * Using the value iteration algorithm to find the optimal policy
     * @param gridWorld The GridWorld map
     * @param discount Discount factor (preference of an agent towards current rewards over future rewards)
     * @return Array of states of the GridWorld, updated with the optimal policies
     */
    public static State[][] valueIteration(GridWorld gridWorld, double discount)  {
        State[][] grid = gridWorld.getGrid();

        // Array to store utilities after each iteration
        double[][] utils = new double[gridWorld.getLength()+2][gridWorld.getWidth()+2];

        State curState;
        int iterations = 0;
        int targetIterations = 200;

        do {
            iterations++;

            // Update utility of each state simultaneously after each iteration
            updateUtility(grid, utils);

            // Iterate through every state
            for (int i = 1; i <= gridWorld.getLength(); i++) {
                for (int j = 1; j <= gridWorld.getWidth(); j++) {
                    curState = grid[i][j];

                    // Ignore wall
                    if (curState.isWall()) continue;

                    // Calculate maximum expected utility
                    Pair<Double, Direction> MEU = maxExpectedUtility(grid, i, j);

                    // Apply Bellman Equation
                    utils[i][j] = curState.getReward() + discount * MEU.first;

                    // Set new direction of state
                    curState.setDirection(MEU.second);
                }
            }

        } while (iterations < targetIterations);

        return grid;
    }

    /**
     * Update utilities of each state in the grid given the utility array
     * @param grid Array of states in the GridWorld
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
     * @return Pair of maximum expected utility and direction of the MEU
     */
    public static Pair<Double, Direction> maxExpectedUtility(State[][] grid, int x, int y) {
        double upEU = countUpUtility(grid, x, y);
        double downEU = countDownUtility(grid, x, y);
        double leftEU = countLeftUtility(grid, x, y);
        double rightEU = countRightUtility(grid, x, y);

        double maxEU = upEU;
        Direction maxDir = Direction.UP;

        if (downEU > maxEU) {
            maxEU = downEU;
            maxDir = Direction.DOWN;
        }

        if (leftEU > maxEU) {
            maxEU = leftEU;
            maxDir = Direction.LEFT;
        }

        if (rightEU > maxEU) {
            maxEU = rightEU;
            maxDir = Direction.RIGHT;
        }

        return new Pair<>(maxEU, maxDir);
    }

    /**
     * Counts the expected utility of moving to the specified direction
     * @param grid Array of states in the grid world
     * @param x Current state's x-coordinate
     * @param y Current state's y-coordinate
     * @param direction Direction of movement
     * @return Expected utility of moving to the specified direction
     */
    public static double countExpectedUtility(State[][] grid, int x, int y, Direction direction) {
        double EU;

        switch(direction) {
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
        double selfUtility = self.getUtility();
        double rightUtility = right.isWall() ? selfUtility : right.getUtility();
        double upUtility = up.isWall() ? selfUtility : up.getUtility();
        double downUtility = down.isWall() ? selfUtility : down.getUtility();

        return 0.8 * rightUtility + 0.1 * upUtility + 0.1 * downUtility;
    }

    /**
     * Using the policy iteration algorithm to find the optimal policy
     * @param gridWorld The GridWorld map
     * @return Array of states of the GridWorld, updated with the optimal policies
     */
    public static State[][] policyIteration(GridWorld gridWorld) {
        State[][] grid = gridWorld.getGrid();
        double[][] utils;
        boolean unchanged;
        State curState;

        do {
            // Update array of utilities by doing policy evaluation
            utils = policyEvaluation(grid);

            // Update utility of each state after each iteration
            updateUtility(grid, utils);

            unchanged = true;

            // Iterate through every state
            for (int i = 1; i <= gridWorld.getLength(); i++) {
                for (int j = 1; j <= gridWorld.getWidth(); j++) {
                    curState = grid[i][j];

                    // Ignore walls
                    if (curState.isWall()) continue;

                    // Calculate the maximum expected utility of the state
                    Pair<Double, Direction> MEU = maxExpectedUtility(grid, i, j);

                    // Calculate the expected utility of current policy
                    double curEV = countExpectedUtility(grid, i, j, curState.getDirection());

                    // Update policy if a better policy is found
                    if (MEU.first > curEV) {
                        curState.setDirection(MEU.second);
                        unchanged = false;
                    }
                }
            }
        } while (!unchanged); // Stop iterating when policy is not changed

        return grid;
    }

    /**
     * The policy evaluation step in the policy iteration algorithm
     * @param grid Array of states of the GridWorld
     * @return Array of updated utilities of each state
     */
    public static double[][] policyEvaluation(State[][] grid) {
        double[][] utils = new double[grid.length][grid[0].length];
        State curState;

        // Iterate through each state
        for (int i = 1; i < grid.length-1; i++) {
            for (int j = 1; j < grid[0].length-1; j++) {
                curState = grid[i][j];

                // Ignore walls
                if (curState.isWall()) continue;

                // Calculate the utility of each state with the current policy
                utils[i][j] = curState.getReward() + 0.99 * countExpectedUtility(grid, i, j, curState.getDirection());
            }
        }

        return utils;
    }
}