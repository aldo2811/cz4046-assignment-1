package Main;

import GridWorld.*;
import Util.*;

/**
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        // Part I: Basic Maze Environment
        System.out.println("Part I: Basic 6x6 Maze");
        basicMaze();

        // Part II: Bonus Question
        System.out.println("Part II: Bonus Question");
        bonusMaze1();
        bonusMaze2();
    }

    /**
     * Creates GridWorld object and run it on both value iteration and policy iteration algorithms
     * @param length Length of maze
     * @param width Width of maze
     * @param greenSquares String of green square coordinates
     * @param redSquares String of red square coordinates
     * @param wallSquares String of wall square coordinates
     */
    public static void analyzeGridWorld(int length, int width, String greenSquares, String redSquares, String wallSquares) {
        // Value Iteration
        GridWorld gridWorld1 = new GridWorld(length, width, greenSquares, redSquares, wallSquares);
        // Print GridWorld map
        Util.printGrid(gridWorld1);

        State[][] valueIterationResult = ValueIteration.valueIteration(gridWorld1);
        // Print out utilities of each state
        Util.printUtilities(valueIterationResult);
        // Print out optimal policies of each state
        Util.printPolicies(valueIterationResult);

        // Policy Iteration
        GridWorld gridWorld2 = new GridWorld(length, width, greenSquares, redSquares, wallSquares);

        State[][] policyIterationResult = PolicyIteration.policyIteration(gridWorld2);
        // Print out utilities of each state
        Util.printUtilities(policyIterationResult);
        // Print out optimal policies of each state
        Util.printPolicies(policyIterationResult);
    }

    /**
     * Creates the basic maze provided in the assignment
     */
    public static void basicMaze() {
        int length = Constants.BASIC_LENGTH;
        int width = Constants.BASIC_WIDTH;
        String greenSquares = Constants.BASIC_GREEN;
        String redSquares = Constants.BASIC_RED;
        String wallSquares = Constants.BASIC_WALL;

        analyzeGridWorld(length, width, greenSquares, redSquares, wallSquares);
    }

    /**
     * Creates Bonus Maze 1 as indicated in the report
     */
    public static void bonusMaze1() {
        int length = Constants.BONUS1_LENGTH;
        int width = Constants.BONUS1_WIDTH;
        String greenSquares = Constants.BONUS1_GREEN;
        String redSquares = Constants.BONUS1_RED;
        String wallSquares = Constants.BONUS1_WALL;

        analyzeGridWorld(length, width, greenSquares, redSquares, wallSquares);
    }

    /**
     * Creates Bonus Maze 2 as indicated in the report
     */
    public static void bonusMaze2() {
        int length = Constants.BONUS2_LENGTH;
        int width = Constants.BONUS2_WIDTH;
        String greenSquares = Constants.BONUS2_GREEN;
        String redSquares = Constants.BONUS2_RED;
        String wallSquares = Constants.BONUS2_WALL;

        analyzeGridWorld(length, width, greenSquares, redSquares, wallSquares);
    }
}