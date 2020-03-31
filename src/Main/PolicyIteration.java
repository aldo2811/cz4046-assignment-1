package Main;

import GridWorld.*;
import Util.*;

/**
 * Policy iteration algorithm implementation
 */
public class PolicyIteration {
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
        int iterations = 0;

        // Used as a limit for plotting utility estimates as a function of the number of iterations
        int targetIterations = 700;

        do {
            iterations++;

            // Update array of utilities by doing policy evaluation
            utils = policyEvaluation(grid);

            // Update utility of each state after each iteration
            Util.updateUtility(grid, utils);

            unchanged = true;

            // Iterate through every state
            for (int i = 1; i <= gridWorld.getLength(); i++) {
                for (int j = 1; j <= gridWorld.getWidth(); j++) {
                    curState = grid[i][j];

                    // Ignore walls
                    if (curState.isWall()) continue;

                    // Calculate the expected utility of current policy
                    double curEV = Util.countExpectedUtility(grid, i, j, curState.getPolicy());

                    // Calculate the maximum expected utility of the state
                    Pair<Double, Policy> MEU = Util.maxExpectedUtility(grid, i, j);

                    // Update policy if a better policy is found
                    if (MEU.first > curEV) {
                        curState.setPolicy(MEU.second);
                        unchanged = false;
                    }
                }
            }

        } while (!unchanged); // Stop iterating when policy is not changed
        // while(iterations < targetIterations); // For plotting utility estimates as a function of the number of iterations

        System.out.println("Policy Iteration");
        System.out.println("No of iterations: " + iterations);
        return grid;
    }

    /**
     * The policy evaluation step in the policy iteration algorithm
     * @param grid Array of states of the Gridworld
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
                utils[i][j] = curState.getReward() + Constants.DISCOUNT * Util.countExpectedUtility(grid, i, j, curState.getPolicy());
            }
        }

        return utils;
    }
}
