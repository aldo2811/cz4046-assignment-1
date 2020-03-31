package Main;

import GridWorld.*;
import Util.*;

/**
 * Value iteration algorithm implementation
 */
public class ValueIteration {

    /**
     * Using the value iteration algorithm to find the optimal policy
     * @param gridWorld The GridWorld map
     * @return Array of states of the GridWorld, updated with the optimal policies
     */
    public static State[][] valueIteration(GridWorld gridWorld) {
        State[][] grid = gridWorld.getGrid();

        // Array to store utilities after each iteration
        double[][] utils = new double[gridWorld.getLength()+2][gridWorld.getWidth()+2];

        State curState;
        double delta, curUtil, diff;
        int iterations = 0;

        // Used as a limit for plotting utility estimates as a function of the number of iterations
        int targetIterations = 700;

        do {
            // Counting how many iterations
            iterations++;

            // Initialize delta to zero at every iteration
            delta = 0;

            // Update utility of each state simultaneously after each iteration
            Util.updateUtility(grid, utils);

            // Iterate through every state
            for (int i = 1; i <= gridWorld.getLength(); i++) {
                for (int j = 1; j <= gridWorld.getWidth(); j++) {
                    curState = grid[i][j];
                    curUtil = utils[i][j];

                    // Ignore wall
                    if (curState.isWall()) continue;

                    // Calculate maximum expected utility
                    Pair<Double, Policy> MEU = Util.maxExpectedUtility(grid, i, j);

                    // Apply Bellman Equation
                    utils[i][j] = curState.getReward() + Constants.DISCOUNT * MEU.first;

                    // Set new direction of state
                    curState.setPolicy(MEU.second);

                    // Calculate difference of old utility and new utility
                    diff = Math.abs(utils[i][j] - curUtil);

                    // Assign the difference to delta if it is bigger
                    if (diff > delta) {
                        delta = diff;
                    }
                }
            }
        } while (delta >= Constants.EPSILON * (1 - Constants.DISCOUNT) / Constants.DISCOUNT); // Convergence criteria
        // while (iterations < targetIterations); // For plotting utility estimates as a function of the number of iterations

        System.out.println("Value Iteration");
        System.out.println("No of iterations: " + iterations);
        return grid;
    }
}
