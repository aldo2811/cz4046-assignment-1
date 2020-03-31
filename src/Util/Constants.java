package Util;

/**
 * Class of constants used in this project
 */
public class Constants {
    // Discount value
    public static final double DISCOUNT = 0.99;

    // Error value
    public static final double c = 0.1;
    public static final double R_MAX = 1.0;
    public static final double EPSILON = c * R_MAX;

    // Basic Maze
    public static final int BASIC_LENGTH = 6;
    public static final int BASIC_WIDTH = 6;
    public static final String BASIC_GREEN = "0,0 0,2 0,5 1,3 2,4 3,5";
    public static final String BASIC_RED = "1,1 1,5 2,2 3,3 4,4";
    public static final String BASIC_WALL = "0,1 1,4 4,1 4,2 4,3";

    // Bonus Maze 1
    public static final int BONUS1_LENGTH = 8;
    public static final int BONUS1_WIDTH = 8;
    public static final String BONUS1_GREEN = "0,0 0,5 2,0 3,4 3,6 5,1 6,6 7,2 7,7";
    public static final String BONUS1_RED = "0,3 0,6 2,4 3,3 3,5 4,0 4,1 4,4 6,1 7,0 7,5";
    public static final String BONUS1_WALL = "0,1 1,1 1,2 1,3 1,4 1,5 2,2 2,5 3,2 4,2 5,2 5,3 5,4 5,5 6,5";

    // Bonus Maze 2
    public static final int BONUS2_LENGTH = 8;
    public static final int BONUS2_WIDTH = 8;
    public static final String BONUS2_GREEN = "1,2 2,1";
    public static final String BONUS2_RED = "0,1 0,2 1,0 1,3 2,0 2,3 3,1 3,2";
    public static final String BONUS2_WALL = "7,7";

}
