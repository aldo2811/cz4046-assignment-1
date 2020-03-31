package GridWorld;

import Util.*;

/**
 * A single state object
 */
public class State {
    private double reward;
    private double utility;
    private boolean isWall;
    private Square square;
    private Policy policy;

    public State(Square square) {
        this.isWall = false;
        this.square = square;
        this.policy = Policy.UP;

        switch(square) {
            // Green squares have +1 reward
            case GREEN:
                this.reward = 1;
                break;
            // Red squares have -1 reward
            case RED:
                this.reward = -1;
                break;
            // White squares have -0.04 reward
            case WHITE:
                this.reward = -0.04;
                break;
            // Wall squares are walls in the maze
            case WALL:
                this.reward = 0;
                this.isWall = true;
                this.policy = Policy.WALL;
                break;
        }
    }

    /**
     * Check if state object is a wall
     * @return True if state is a wall, else false
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * Getter for reward of state
     * @return Reward of state
     */
    public double getReward() {
        return reward;
    }

    /**
     * Getter for utility of state
     * @return Utility of state
     */
    public double getUtility() {
        return utility;
    }

    /**
     * Setter for utility of state
     * @param utility New utility of state
     */
    public void setUtility(double utility) {
        this.utility = utility;
    }

    /**
     * Getter for type of square
     * @return Type of square
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Getter for policy of state
     * @return Policy of state
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * Setter for policy of state
     * @param policy Policy of state
     */
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
