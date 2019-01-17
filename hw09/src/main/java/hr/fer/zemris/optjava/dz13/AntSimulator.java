package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMap;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.action.LeftNode;
import hr.fer.zemris.optjava.dz13.nodes.action.MoveNode;
import hr.fer.zemris.optjava.dz13.nodes.action.RightNode;
import hr.fer.zemris.optjava.dz13.nodes.program.IfFoodAheadNode;
import hr.fer.zemris.optjava.dz13.nodes.program.Prog2Node;
import hr.fer.zemris.optjava.dz13.nodes.program.Prog3Node;

public class AntSimulator {

    private FoodMap foodMap;

    private boolean[][] food;

    private int rows;

    private int cols;

    private TreeSolution solution;

    private int row;

    private int col;

    private Direction direction;

    private int moveCount;

    public AntSimulator(FoodMap foodMap) {
        this.foodMap = foodMap;
        this.rows = foodMap.getRows();
        this.cols = foodMap.getCols();
        food = new boolean[rows][cols];
    }

    public void setSolution(TreeSolution solution) {
        this.solution = solution;
    }

    public boolean[][] getFood() {
        return food;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void reset() {
        foodMap.fillFood(food);
        row = 0;
        col = 0;
        direction = Direction.RIGHT;
    }

    public void simulate(int maxMoves) {
        moveCount = 0;
        while (moveCount < maxMoves) {
            runTree(solution.getRoot());
        }
    }

    public void step() {
        runTree(solution.getRoot());
    }

    private void runTree(Node node) {
        if (node instanceof IfFoodAheadNode) {
            runTree(((IfFoodAheadNode) node).getChildren()[foodAhead() ? 0 : 1]);
        } else if (node instanceof Prog2Node) {
            Node[] children = ((Prog2Node) node).getChildren();
            runTree(children[0]);
            runTree(children[1]);
        } else if (node instanceof Prog3Node) {
            Node[] children = ((Prog3Node) node).getChildren();
            runTree(children[0]);
            runTree(children[1]);
            runTree(children[2]);
        } else if (node instanceof MoveNode) {
            if (direction == Direction.UP) row --;
            else if (direction == Direction.DOWN) row ++;
            else if (direction == Direction.LEFT) col --;
            else col ++;

            row = row(row);
            col = col(col);
            if (food[row][col]) {
                food[row][col] = false;
                solution.incrementScore();
            }
            moveCount ++;
        } else if (node instanceof LeftNode) {
            direction = direction.previous();
            moveCount ++;
        } else if (node instanceof RightNode) {
            direction = direction.next();
            moveCount ++;
        }
    }

    private boolean foodAhead() {
        int row = this.row;
        int col = this.col;
        if (direction == Direction.UP) row --;
        else if (direction == Direction.DOWN) row ++;
        else if (direction == Direction.LEFT) col --;
        else col ++;
        return food[row(row)][col(col)];
    }

    private int row(int row) {
        if (row < 0) return rows - 1;
        if (row >= rows) return 0;
        return row;
    }

    private int col(int col) {
        if (col < 0) return cols - 1;
        if (col >= cols) return 0;
        return col;
    }

    private enum Direction {

        UP,
        RIGHT,
        DOWN,
        LEFT;

        static Direction[] values = values();

        Direction next() {
            return values[(this.ordinal() + 1) % values.length];
        }

        Direction previous() {
            return values[(this.ordinal() - 1 + values.length) % values.length];
        }
    }
}
