/**
 * A single state object
 */
public class State {
    private double reward;
    private double utility;
    private boolean isWall;
    private Square square;
    private Direction direction;

    public State(Square square) {
        this.isWall = false;
        this.square = square;
        this.direction = Direction.DOWN;

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
                this.direction = Direction.WALL;
                break;
        }
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public double getUtility() {
        return utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
