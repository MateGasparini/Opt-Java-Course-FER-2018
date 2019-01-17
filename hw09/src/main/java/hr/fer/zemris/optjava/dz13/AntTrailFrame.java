package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMap;

import javax.swing.*;
import java.awt.*;

public class AntTrailFrame extends JFrame {

    private FoodMap foodMap;

    private AntSimulator simulator;

    private TreeSolution solution;

    public AntTrailFrame(TreeSolution solution, FoodMap foodMap) throws HeadlessException {
        this.foodMap = foodMap;
        this.simulator = new AntSimulator(foodMap);
        simulator.setSolution(solution);
        simulator.reset();
        this.solution = solution;

        setTitle("Santa Fe Ant Trail");
        setSize(500, 500);
        setLocation(200, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(foodMap.getRows(), foodMap.getCols()));
        updateCenter(center);
        contentPane.add(center, BorderLayout.CENTER);

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(e -> {
            simulator.step();
            updateCenter(center);
        });
        contentPane.add(stepButton, BorderLayout.SOUTH);
    }

    private void updateCenter(JPanel center) {
        center.removeAll();

        int row = simulator.getRow();
        int col = simulator.getCol();
        boolean[][] food = simulator.getFood();
        for (int i = 0; i < food.length; i ++) {
            for (int j = 0; j < food[i].length; j ++) {
                JPanel cell = new JPanel();
                cell.setEnabled(false);
                if (i == row && j == col) cell.setBackground(Color.RED);
                else cell.setBackground(food[i][j] ? Color.BLUE : Color.WHITE);
                center.add(cell);
            }
        }
        center.revalidate();
    }
}
