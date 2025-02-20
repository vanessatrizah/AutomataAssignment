import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElevatorMachineGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("JKUAT Towers Elevator System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Header Label
        JLabel headerLabel = new JLabel("Welcome to JKUAT Towers Elevator System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Input and Button Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel instructionLabel = new JLabel("Enter the floor you wish to go to (0 to 10):", JLabel.CENTER);
        JTextField floorInput = new JTextField();
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        inputPanel.add(instructionLabel);
        inputPanel.add(floorInput);
        inputPanel.add(buttonPanel);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Output Panel
        JPanel outputPanel = new JPanel();
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        outputPanel.add(resultLabel);
        frame.add(outputPanel, BorderLayout.SOUTH);

        // Action Listener for the Submit Button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = floorInput.getText();
                try {
                    int floor = Integer.parseInt(inputText);

                    // Validate floor input
                    if (floor < 0 || floor > 10) {
                        resultLabel.setText("Invalid floor! Please enter a number between 0 and 10.");
                        resultLabel.setForeground(Color.RED);
                    } else {
                        // Determine the assigned door
                        String door = "";
                        if (floor >= 0 && floor <= 5) {
                            door = "Door A (Ground Floor to 5th Floor)";
                        } else if (floor >= 0 && floor <= 8) {
                            door = "Door B (Ground Floor to 8th Floor)";
                        } else if (floor >= 0 && floor <= 10) {
                            door = "Door C (Ground Floor to 10th Floor)";
                        }

                        resultLabel.setText("Assigned Door: " + door);
                        resultLabel.setForeground(Color.BLACK);
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input! Please enter a valid number.");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });

        // Action Listener for the Reset Button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floorInput.setText("");
                resultLabel.setText("");
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
